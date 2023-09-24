package app.revanced.patches.youtube.layout.seekbar.customseekbarcolor.bytecode.patch

import app.revanced.patcher.annotation.Name
import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.annotations.DependsOn
import app.revanced.shared.annotation.YouTubeCompatibility
import app.revanced.shared.extensions.findMutableMethodOf
import app.revanced.shared.extensions.toResult
import app.revanced.shared.patches.mapping.ResourceMappingPatch
import app.revanced.shared.util.integrations.Constants.SEEKBAR_LAYOUT
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction
import com.android.tools.smali.dexlib2.iface.instruction.formats.Instruction31i

@DependsOn([ResourceMappingPatch::class])
@Name("custom-seekbar-color-bytecode-patch")
@YouTubeCompatibility
class CustomSeekbarColorBytecodePatch : BytecodePatch() {

    // list of resource names to get the id of
    private val resourceIds = arrayOf(
        "inline_time_bar_colorized_bar_played_color_dark"
    ).map { name ->
        ResourceMappingPatch.resourceMappings.single { it.name == name }.id
    }
    private var patchSuccessArray = Array(resourceIds.size) {false}

    override fun execute(context: BytecodeContext) {
        context.classes.forEach { classDef ->
            classDef.methods.forEach { method ->
                with(method.implementation) {
                    this?.instructions?.forEachIndexed { index, instruction ->
                        when (instruction.opcode) {
                            Opcode.CONST -> {
                                when ((instruction as Instruction31i).wideLiteral) {
                                    resourceIds[0] -> { // seekbar color
                                        val registerIndex = index + 2

                                        val mutableMethod = context.proxy(classDef).mutableClass.findMutableMethodOf(method)

                                        val viewRegister = (instructions.elementAt(registerIndex) as OneRegisterInstruction).registerA

                                        mutableMethod.addInstructions(
                                            registerIndex + 1, """
                                                invoke-static {v$viewRegister}, $SEEKBAR_LAYOUT->enableCustomSeekbarColor(I)I
                                                move-result v$viewRegister
                                            """
                                        )

                                        patchSuccessArray[0] = true;
                                    }
                                }
                            }
                            else -> return@forEachIndexed
                        }
                    }
                }
            }
        }
        return toResult(patchSuccessArray.indexOf(false))
    }
}
