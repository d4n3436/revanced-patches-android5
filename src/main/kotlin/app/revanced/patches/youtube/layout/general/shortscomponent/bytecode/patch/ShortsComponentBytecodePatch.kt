package app.revanced.patches.youtube.layout.general.shortscomponent.bytecode.patch

import app.revanced.patcher.annotation.Name
import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.PatchException
import app.revanced.patcher.patch.annotations.DependsOn
import app.revanced.shared.annotation.YouTubeCompatibility
import app.revanced.shared.extensions.findMutableMethodOf
import app.revanced.shared.extensions.injectHideCall
import app.revanced.shared.patches.mapping.ResourceMappingPatch
import app.revanced.shared.util.bytecode.BytecodeHelper
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction
import com.android.tools.smali.dexlib2.iface.instruction.formats.Instruction31i

@Name("hide-shorts-component-bytecode-patch")
@DependsOn([ResourceMappingPatch::class])
@YouTubeCompatibility
class ShortsComponentBytecodePatch : BytecodePatch() {

    // list of resource names to get the id of
    private val resourceIds = arrayOf(
        "ic_right_comment_32c"
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
                                    resourceIds[0] -> { // shorts player comment
                                        val insertIndex = index - 3
                                        val invokeInstruction = instructions.elementAt(insertIndex)
                                        if (invokeInstruction.opcode != Opcode.CONST_HIGH16) return@forEachIndexed

                                        val mutableMethod = context.proxy(classDef).mutableClass.findMutableMethodOf(method)

                                        val viewRegister = (instructions.elementAt(index + 3) as OneRegisterInstruction).registerA
                                        mutableMethod.implementation!!.injectHideCall(index + 4, viewRegister, "layout/GeneralLayoutPatch", "hideShortsPlayerCommentsButton")

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
        val errorIndex = patchSuccessArray.indexOf(false)
        if (errorIndex != -1) throw PatchException("Instruction not found: $errorIndex")

        BytecodeHelper.patchStatus(context, "ShortsComponent")
    }
}
