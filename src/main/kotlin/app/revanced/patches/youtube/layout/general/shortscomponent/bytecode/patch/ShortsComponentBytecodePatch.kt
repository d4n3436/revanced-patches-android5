package app.revanced.patches.youtube.layout.general.shortscomponent.bytecode.patch

import app.revanced.patcher.annotation.Name
import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.patch.annotations.DependsOn
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.PatchResult
import app.revanced.patcher.patch.PatchResultError
import app.revanced.patcher.patch.PatchResultSuccess
import app.revanced.shared.extensions.findMutableMethodOf
import app.revanced.shared.extensions.injectHideCall
import app.revanced.shared.patches.mapping.ResourceMappingPatch
import app.revanced.shared.annotation.YouTubeCompatibility
import app.revanced.shared.util.bytecode.BytecodeHelper
import org.jf.dexlib2.iface.instruction.formats.Instruction21c
import org.jf.dexlib2.iface.instruction.formats.Instruction31i
import org.jf.dexlib2.iface.instruction.OneRegisterInstruction
import org.jf.dexlib2.Opcode

@Name("hide-shorts-component-bytecode-patch")
@DependsOn([ResourceMappingPatch::class])
@YouTubeCompatibility
class ShortsComponentBytecodePatch : BytecodePatch() {

    // list of resource names to get the id of
    private val resourceIds = arrayOf(
        "ic_right_comment_32c",
        "reel_dyn_remix",
        "reel_player_paused_state_buttons"
    ).map { name ->
        ResourceMappingPatch.resourceMappings.single { it.name == name }.id
    }
    private var patchSuccessArray = Array(resourceIds.size) {false}

    override fun execute(context: BytecodeContext): PatchResult {
        context.classes.forEach { classDef ->
            classDef.methods.forEach { method ->
                with(method.implementation) {
                    this?.instructions?.forEachIndexed { index, instruction ->
                        when (instruction.opcode) {
                            Opcode.CONST -> {
                                when ((instruction as Instruction31i).wideLiteral) {
                                    resourceIds[0] -> { // shorts player comment
                                        val insertIndex = index - 2
                                        val invokeInstruction = instructions.elementAt(insertIndex)
                                        if (invokeInstruction.opcode != Opcode.CONST_HIGH16) return@forEachIndexed

                                        val mutableMethod = context.proxy(classDef).mutableClass.findMutableMethodOf(method)

                                        val viewRegister = (instructions.elementAt(index + 3) as OneRegisterInstruction).registerA
                                        mutableMethod.implementation!!.injectHideCall(index + 4, viewRegister, "layout/GeneralLayoutPatch", "hideShortsPlayerCommentsButton")

                                        patchSuccessArray[0] = true;
                                    }

                                    resourceIds[1] -> { // shorts player remix
                                        val insertIndex = index - 2
                                        val invokeInstruction = instructions.elementAt(insertIndex)
                                        if (invokeInstruction.opcode != Opcode.CHECK_CAST) return@forEachIndexed

                                        val mutableMethod = context.proxy(classDef).mutableClass.findMutableMethodOf(method)

                                        val viewRegister = (invokeInstruction as Instruction21c).registerA
                                        mutableMethod.implementation!!.injectHideCall(index - 1, viewRegister, "layout/GeneralLayoutPatch", "hideShortsPlayerRemixButton")

                                        patchSuccessArray[1] = true;
                                    }

                                    resourceIds[2] -> { // shorts player subscriptions banner
                                        val insertIndex = index + 3
                                        val invokeInstruction = instructions.elementAt(insertIndex)
                                        if (invokeInstruction.opcode != Opcode.CHECK_CAST) return@forEachIndexed

                                        val mutableMethod = context.proxy(classDef).mutableClass.findMutableMethodOf(method)

                                        val viewRegister = (invokeInstruction as Instruction21c).registerA
                                        mutableMethod.implementation!!.injectHideCall(insertIndex, viewRegister, "layout/GeneralLayoutPatch", "hideShortsPlayerSubscriptionsButton")

                                        patchSuccessArray[2] = true;
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
        return if (errorIndex == -1) {
            BytecodeHelper.patchStatus(context, "ShortsComponent")
            PatchResultSuccess()
        } else
            PatchResultError("Instruction not found: $errorIndex")
    }
}
