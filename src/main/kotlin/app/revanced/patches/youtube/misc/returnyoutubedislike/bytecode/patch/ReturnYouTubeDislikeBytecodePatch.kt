package app.revanced.patches.youtube.misc.returnyoutubedislike.bytecode.patch

import app.revanced.patcher.annotation.Name
import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.addInstruction
import app.revanced.patcher.extensions.InstructionExtensions.getInstruction
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.annotations.DependsOn
import app.revanced.patches.youtube.misc.protobufpoof.fingerprints.PlayerParameterBuilderFingerprint
import app.revanced.patches.youtube.misc.resourceid.patch.SharedResourcdIdPatch
import app.revanced.patches.youtube.misc.resourceid.patch.SharedResourcdIdPatch.Companion.dislikeButtonId
import app.revanced.patches.youtube.misc.returnyoutubedislike.bytecode.fingerprints.ButtonTagFingerprint
import app.revanced.patches.youtube.misc.videoid.mainstream.patch.MainstreamVideoIdPatch
import app.revanced.shared.annotation.YouTubeCompatibility
import app.revanced.shared.extensions.exception
import app.revanced.shared.util.bytecode.getWideLiteralIndex
import app.revanced.shared.util.integrations.Constants.UTILS_PATH
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction

@Name("return-youtube-dislike-bytecode-patch")
@DependsOn(
    [
        SharedResourcdIdPatch::class
    ]
)
@YouTubeCompatibility
class ReturnYouTubeDislikeBytecodePatch : BytecodePatch(
    listOf(
        ButtonTagFingerprint,
        PlayerParameterBuilderFingerprint
    )
) {
    override fun execute(context: BytecodeContext) {

        ButtonTagFingerprint.result?.let {
            it.mutableMethod.apply {
                val dislikeButtonIndex = getWideLiteralIndex(dislikeButtonId)

                val resourceIdentifierRegister =
                    getInstruction<OneRegisterInstruction>(dislikeButtonIndex).registerA
                val textViewRegister =
                    getInstruction<OneRegisterInstruction>(dislikeButtonIndex + 4).registerA

                addInstruction(
                    dislikeButtonIndex + 4,
                    "invoke-static {v$resourceIdentifierRegister, v$textViewRegister}, $INTEGRATIONS_RYD_CLASS_DESCRIPTOR->setOldUILayoutDislikes(ILandroid/widget/TextView;)V"
                )
            }
        } ?: throw ButtonTagFingerprint.exception

        MainstreamVideoIdPatch.injectCall("$INTEGRATIONS_RYD_CLASS_DESCRIPTOR->newVideoLoaded(Ljava/lang/String;)V")
    }

    private companion object {
        const val INTEGRATIONS_RYD_CLASS_DESCRIPTOR =
            "$UTILS_PATH/ReturnYouTubeDislikePatch;"
    }
}
