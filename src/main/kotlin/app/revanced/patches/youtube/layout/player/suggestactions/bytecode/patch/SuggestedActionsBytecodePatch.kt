package app.revanced.patches.youtube.layout.player.suggestactions.bytecode.patch

import app.revanced.patcher.annotation.Name
import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.annotations.DependsOn
import app.revanced.shared.annotation.YouTubeCompatibility
import app.revanced.shared.extensions.findMutableMethodOf
import app.revanced.shared.extensions.injectHideCall
import app.revanced.shared.extensions.toResult
import app.revanced.shared.patches.mapping.ResourceMappingPatch
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.formats.Instruction22c
import com.android.tools.smali.dexlib2.iface.instruction.formats.Instruction31i

@DependsOn([ResourceMappingPatch::class])
@Name("hide-suggested-actions-bytecode-patch")
@YouTubeCompatibility
class SuggestedActionsBytecodePatch : BytecodePatch() {

    // list of resource names to get the id of
    private val resourceIds = arrayOf(
        "suggested_action"
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
                                    resourceIds[0] -> { // suggested_action
                                        val insertIndex = index + 4
                                        val iPutInstruction = instructions.elementAt(insertIndex)
                                        if (iPutInstruction.opcode != Opcode.IPUT_OBJECT) return@forEachIndexed

                                        val mutableMethod = context.proxy(classDef).mutableClass.findMutableMethodOf(method)

                                        val viewRegister = (iPutInstruction as Instruction22c).registerA
                                        mutableMethod.implementation!!.injectHideCall(insertIndex, viewRegister, "layout/PlayerLayoutPatch", "hideSuggestedActions")

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
