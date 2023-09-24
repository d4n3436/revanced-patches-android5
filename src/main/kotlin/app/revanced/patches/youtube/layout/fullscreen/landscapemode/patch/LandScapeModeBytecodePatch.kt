package app.revanced.patches.youtube.layout.fullscreen.landscapemode.patch

import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
import app.revanced.patcher.extensions.InstructionExtensions.getInstruction
import app.revanced.patcher.fingerprint.method.impl.MethodFingerprint.Companion.resolve
import app.revanced.patcher.fingerprint.method.impl.MethodFingerprintResult
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patches.youtube.layout.fullscreen.landscapemode.fingerprints.OrientationParentFingerprint
import app.revanced.patches.youtube.layout.fullscreen.landscapemode.fingerprints.OrientationPrimaryFingerprint
import app.revanced.patches.youtube.layout.fullscreen.landscapemode.fingerprints.OrientationSecondaryFingerprint
import app.revanced.shared.extensions.exception
import app.revanced.shared.util.integrations.Constants.FULLSCREEN_LAYOUT
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction

class LandScapeModeBytecodePatch : BytecodePatch(
    listOf(
        OrientationParentFingerprint
    )
) {
    override fun execute(context: BytecodeContext) {
        OrientationParentFingerprint.result?.classDef?.let { classDef ->
            arrayOf(
                OrientationPrimaryFingerprint,
                OrientationSecondaryFingerprint
            ).forEach { fingerprint ->
                fingerprint.also { it.resolve(context, classDef) }.result?.injectOverride() ?: throw fingerprint.exception
            }
        } ?: throw OrientationParentFingerprint.exception
    }

    private companion object {
        const val INTEGRATIONS_CLASS_DESCRIPTOR =
            "$FULLSCREEN_LAYOUT->disableLandScapeMode(Z)Z"

        fun MethodFingerprintResult.injectOverride() {
            with (mutableMethod) {
                val index = scanResult.patternScanResult!!.endIndex
                val register = getInstruction<OneRegisterInstruction>(index).registerA

                addInstructions(
                    index +1, """
                        invoke-static {v$register}, $INTEGRATIONS_CLASS_DESCRIPTOR
                        move-result v$register
                    """
                )
            }
        }
    }
}