package app.revanced.patches.youtube.layout.general.mixplaylists.bytecode.patch

import app.revanced.patcher.annotation.Name
import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.getInstruction
import app.revanced.patcher.fingerprint.method.impl.MethodFingerprintResult
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.PatchResult
import app.revanced.patcher.patch.PatchResultSuccess
import app.revanced.patches.youtube.layout.general.mixplaylists.bytecode.fingerprints.*
import app.revanced.shared.annotation.YouTubeCompatibility
import app.revanced.shared.extensions.injectHideCall
import app.revanced.shared.extensions.toErrorResult
import org.jf.dexlib2.iface.instruction.Instruction
import org.jf.dexlib2.iface.instruction.OneRegisterInstruction
import org.jf.dexlib2.iface.instruction.TwoRegisterInstruction
import org.jf.dexlib2.iface.instruction.formats.Instruction21c

@Name("hide-mix-playlists-bytecode-patch")
@YouTubeCompatibility
class MixPlaylistsBytecodePatch : BytecodePatch(
    listOf(
        CreateMixPlaylistFingerprint,
        SecondCreateMixPlaylistFingerprint,
        ThirdCreateMixPlaylistFingerprint,
        FourthCreateMixPlaylistFingerprint
    )
) {
    override fun execute(context: BytecodeContext): PatchResult {

        arrayOf(
            CreateMixPlaylistFingerprint,
            SecondCreateMixPlaylistFingerprint
        ).map {
            it.result ?: return it.toErrorResult()
        }.forEach {
            it.addHook()
        }

        arrayOf(
            ThirdCreateMixPlaylistFingerprint to true,
            FourthCreateMixPlaylistFingerprint to false
        ).map { (fingerprint, boolean) ->
            fingerprint.result?.hookMixPlaylists(boolean) ?: return fingerprint.toErrorResult()
        }

        return PatchResultSuccess()
    }

    private fun MethodFingerprintResult.addHook() {
        val insertIndex = scanResult.patternScanResult!!.endIndex - 3
        val register = (mutableMethod.getInstruction(insertIndex - 2) as OneRegisterInstruction).registerA

        mutableMethod.implementation!!.injectHideCall(insertIndex, register, "layout/GeneralLayoutPatch", "hideMixPlaylists")
    }

    private fun MethodFingerprintResult.hookMixPlaylists(isThirdFingerprint: Boolean) {
        fun getRegister(instruction: Instruction): Int {
            if (isThirdFingerprint) return (instruction as TwoRegisterInstruction).registerA
            return (instruction as Instruction21c).registerA
        }
        val endIndex = scanResult.patternScanResult!!.endIndex
        val instruction = method.implementation!!.instructions.elementAt(endIndex)
        val register = getRegister(instruction)

        mutableMethod.implementation!!.injectHideCall(endIndex, register, "layout/GeneralLayoutPatch", "hideMixPlaylists")
    }
}
