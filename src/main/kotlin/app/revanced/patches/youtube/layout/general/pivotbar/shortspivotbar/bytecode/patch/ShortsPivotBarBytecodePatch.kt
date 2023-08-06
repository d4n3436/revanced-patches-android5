package app.revanced.patches.youtube.layout.general.pivotbar.shortspivotbar.bytecode.patch

import app.revanced.shared.extensions.toErrorResult
import app.revanced.patcher.annotation.Name
import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.data.toMethodWalker
import app.revanced.patcher.extensions.InstructionExtensions.addInstruction
import app.revanced.patcher.fingerprint.method.impl.MethodFingerprint.Companion.resolve
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.PatchResult
import app.revanced.patcher.patch.PatchResultSuccess
import app.revanced.patcher.util.proxy.mutableTypes.MutableMethod
import app.revanced.shared.fingerprints.PivotBarFingerprint
import app.revanced.patches.youtube.layout.general.pivotbar.shortspivotbar.bytecode.fingerprints.*
import app.revanced.shared.annotation.YouTubeCompatibility
import app.revanced.shared.util.integrations.Constants.GENERAL_LAYOUT
import org.jf.dexlib2.dexbacked.reference.DexBackedTypeReference
import org.jf.dexlib2.iface.instruction.OneRegisterInstruction
import org.jf.dexlib2.iface.instruction.ReferenceInstruction

@Name("hide-shorts-pivot-bar-bytecode-patch")
@YouTubeCompatibility
class ShortsPivotBarBytecodePatch : BytecodePatch(
    listOf(
        PivotBarFingerprint,
        ReelWatchBundleFingerprint,
        ReelWatchEndpointParentFingerprint
    )
) {
    override fun execute(context: BytecodeContext): PatchResult {

        PivotBarFingerprint.result?.let { parentResult ->
            SetPivotBarFingerprint.also { it.resolve(context, parentResult.classDef) }.result?.let {
                with (it.mutableMethod) {
                    val startIndex = it.scanResult.patternScanResult!!.startIndex
                    val instructions = implementation!!.instructions

                    val indexReference = ((instructions[startIndex] as ReferenceInstruction).reference as DexBackedTypeReference).toString()
                    if (indexReference != targetReference) return SetPivotBarFingerprint.toErrorResult()
                    val register = (instructions[startIndex] as OneRegisterInstruction).registerA

                    addInstruction(
                        startIndex + 1,
                        "sput-object v$register, $GENERAL_LAYOUT->pivotbar:$targetReference"
                    )
                }
            } ?: return SetPivotBarFingerprint.toErrorResult()
        } ?: return PivotBarFingerprint.toErrorResult()

        ReelWatchBundleFingerprint.result?.let {
            with (context
                .toMethodWalker(it.method)
                .nextMethod(it.scanResult.patternScanResult!!.endIndex, true)
                .getMethod() as MutableMethod
            ) {
                addInstruction(
                    0,
                    "invoke-static {}, $GENERAL_LAYOUT->hideShortsPlayerPivotBar()V"
                )
            }
        } ?: return ReelWatchBundleFingerprint.toErrorResult()

        ReelWatchEndpointParentFingerprint.result?.let { parentResult ->
            ReelWatchEndpointFingerprint.also { it.resolve(context, parentResult.classDef) }.result?.mutableMethod?.addInstruction(
                0,
                "sput-object p1, $GENERAL_LAYOUT->shortsContext:Landroid/content/Context;"
            ) ?: return ReelWatchEndpointFingerprint.toErrorResult()
        } ?: return ReelWatchEndpointParentFingerprint.toErrorResult()

        return PatchResultSuccess()
    }

    private companion object {
        const val targetReference =
            "Lcom/google/android/apps/youtube/app/ui/pivotbar/PivotBar;"
    }
}