package app.revanced.shared.patches.playerbutton

import app.revanced.patcher.annotation.Name
import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
import app.revanced.patcher.extensions.InstructionExtensions.getInstruction
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.PatchResult
import app.revanced.patcher.patch.PatchResultSuccess
import app.revanced.patcher.patch.annotations.DependsOn
import app.revanced.patches.youtube.misc.resourceid.patch.SharedResourcdIdPatch
import app.revanced.shared.annotation.YouTubeCompatibility
import app.revanced.shared.extensions.findMutableMethodOf
import app.revanced.shared.extensions.toErrorResult
import app.revanced.shared.fingerprints.LiveChatFingerprint
import app.revanced.shared.util.integrations.Constants.PLAYER_LAYOUT
import org.jf.dexlib2.Opcode
import org.jf.dexlib2.builder.instruction.BuilderInstruction21c
import org.jf.dexlib2.builder.instruction.BuilderInstruction35c

@Name("hook-player-button-patch")
@DependsOn([SharedResourcdIdPatch::class])
@YouTubeCompatibility
class PlayerButtonPatch : BytecodePatch(
    listOf(
        LiveChatFingerprint
    )
) {
    override fun execute(context: BytecodeContext): PatchResult {

        LiveChatFingerprint.result?.let {
            val endIndex = it.scanResult.patternScanResult!!.endIndex
            val instuctions = it.mutableMethod.getInstruction(endIndex)
            val imageButtonClass =
                context
                .findClass((instuctions as BuilderInstruction21c)
                .reference.toString())!!
                .mutableClass

            for (method in imageButtonClass.methods) {
                with (imageButtonClass.findMutableMethodOf(method)) {
                    var jumpInstruction = true

                    implementation!!.instructions.forEachIndexed { index, instuctions ->
                        if (instuctions.opcode.ordinal == Opcode.INVOKE_VIRTUAL.ordinal) {
                            val definedInstruction = (instuctions as? BuilderInstruction35c)

                            if (definedInstruction?.reference.toString() ==
                                "Landroid/view/View;->setVisibility(I)V") {

                                jumpInstruction = !jumpInstruction
                                if (jumpInstruction) return@forEachIndexed

                                val firstRegister = definedInstruction?.registerC
                                val secondRegister = definedInstruction?.registerD

                                addInstructions(
                                    index, """
                                        invoke-static {v$firstRegister, v$secondRegister}, $PLAYER_LAYOUT->hidePlayerButton(Landroid/view/View;I)I
                                        move-result v$secondRegister
                                        """
                                )
                            }
                        }
                    }
                }
            }
        } ?: return LiveChatFingerprint.toErrorResult()

        return PatchResultSuccess()
    }
}