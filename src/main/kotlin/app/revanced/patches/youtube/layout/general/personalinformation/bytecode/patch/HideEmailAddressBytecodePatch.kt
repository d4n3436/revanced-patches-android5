package app.revanced.patches.youtube.layout.general.personalinformation.bytecode.patch

import app.revanced.patcher.annotation.Name
import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
import app.revanced.patcher.extensions.InstructionExtensions.getInstruction
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.PatchResult
import app.revanced.patcher.patch.PatchResultSuccess
import app.revanced.patches.youtube.layout.general.personalinformation.bytecode.fingerprints.AccountSwitcherAccessibilityLabelFingerprint
import app.revanced.shared.annotation.YouTubeCompatibility
import app.revanced.shared.extensions.toErrorResult
import app.revanced.shared.util.integrations.Constants.GENERAL_LAYOUT
import org.jf.dexlib2.iface.instruction.OneRegisterInstruction

@Name("hide-email-address-bytecode-patch")
@YouTubeCompatibility
class HideEmailAddressBytecodePatch : BytecodePatch(
    listOf(
        AccountSwitcherAccessibilityLabelFingerprint
    )
) {
    override fun execute(context: BytecodeContext): PatchResult {

        AccountSwitcherAccessibilityLabelFingerprint.result?.let {
            with (it.mutableMethod) {
                val insertIndex = it.scanResult.patternScanResult!!.endIndex
                val register = (getInstruction(insertIndex - 2) as OneRegisterInstruction).registerA

                addInstructions(
                    insertIndex, """
                        invoke-static {v$register}, $GENERAL_LAYOUT->hideEmailAddress(I)I
                        move-result v$register
                    """
                )
            }
        } ?: return AccountSwitcherAccessibilityLabelFingerprint.toErrorResult()

        return PatchResultSuccess()
    }
}
