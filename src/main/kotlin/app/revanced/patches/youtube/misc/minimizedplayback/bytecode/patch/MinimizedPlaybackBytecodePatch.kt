package app.revanced.patches.youtube.misc.minimizedplayback.bytecode.patch

import app.revanced.patcher.annotation.Name
import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.data.toMethodWalker
import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
import app.revanced.patcher.extensions.InstructionExtensions.addInstructionsWithLabels
import app.revanced.patcher.extensions.InstructionExtensions.getInstruction
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.PatchResult
import app.revanced.patcher.patch.PatchResultSuccess
import app.revanced.patcher.patch.annotations.DependsOn
import app.revanced.patcher.util.proxy.mutableTypes.MutableMethod
import app.revanced.patcher.util.smali.ExternalLabel
import app.revanced.patches.youtube.misc.minimizedplayback.bytecode.fingerprints.*
import app.revanced.patches.youtube.misc.resourceid.patch.SharedResourcdIdPatch
import app.revanced.shared.annotation.YouTubeCompatibility
import app.revanced.shared.extensions.toErrorResult
import app.revanced.shared.util.integrations.Constants.MISC_PATH
import org.jf.dexlib2.iface.instruction.ReferenceInstruction
import org.jf.dexlib2.iface.reference.MethodReference

@Name("enable-minimized-playback-bytecode-patch")
@DependsOn([SharedResourcdIdPatch::class])
@YouTubeCompatibility
class MinimizedPlaybackBytecodePatch : BytecodePatch(
    listOf(
        KidsMinimizedPlaybackPolicyControllerFingerprint,
        MinimizedPlaybackManagerFingerprint,
        MinimizedPlaybackSettingsFingerprint
    )
) {
    override fun execute(context: BytecodeContext): PatchResult {
        val methods = arrayOf(
            KidsMinimizedPlaybackPolicyControllerFingerprint,
            MinimizedPlaybackManagerFingerprint
        ).map {
            it.result?.mutableMethod?: return it.toErrorResult()
        }

        methods[0].hookPlaybackController()
        methods[1].hookPlayback()

        return PatchResultSuccess()
    }

    private companion object {
        const val INTEGRATIONS_PLAYBACK_METHOD_REFERENCE =
            "$MISC_PATH/MinimizedPlaybackPatch;->enableMinimizedPlayback()Z"

        fun MutableMethod.walkMutable(
            context: BytecodeContext) {
            val booleanCalls = implementation!!.instructions.withIndex()
                .filter { ((it.value as? ReferenceInstruction)?.reference as? MethodReference)?.returnType == "Z" }

            val booleanIndex = booleanCalls.elementAt(1).index
            val booleanMethod =
                context.toMethodWalker(this)
                .nextMethod(booleanIndex, true)
                .getMethod() as MutableMethod

            booleanMethod.hookPlayback()
        }

        fun MutableMethod.hookPlayback() {
            addInstructions(
                0, """
                    invoke-static {}, $INTEGRATIONS_PLAYBACK_METHOD_REFERENCE
                    move-result v0
                    return v0
                """
            )
        }

        fun MutableMethod.hookPlaybackController() {
            addInstructionsWithLabels(
                0, """
                    invoke-static {}, $INTEGRATIONS_PLAYBACK_METHOD_REFERENCE
                    move-result v0
                    if-eqz v0, :default
                    return-void
                """, ExternalLabel("default", getInstruction(0))
            )
        }
    }
}
