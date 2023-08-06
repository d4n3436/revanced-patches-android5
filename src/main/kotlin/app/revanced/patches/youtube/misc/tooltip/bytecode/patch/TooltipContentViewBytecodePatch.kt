package app.revanced.patches.youtube.misc.tooltip.bytecode.patch

import app.revanced.patcher.annotation.Name
import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.addInstruction
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.PatchResult
import app.revanced.patcher.patch.PatchResultSuccess
import app.revanced.patcher.patch.annotations.DependsOn
import app.revanced.patches.youtube.misc.resourceid.patch.SharedResourcdIdPatch
import app.revanced.patches.youtube.misc.tooltip.bytecode.fingerprints.TooltipContentViewFingerprint
import app.revanced.shared.annotation.YouTubeCompatibility
import app.revanced.shared.extensions.toErrorResult

@Name("hide-tooltip-content-bytecode-patch")
@DependsOn([SharedResourcdIdPatch::class])
@YouTubeCompatibility
class TooltipContentViewBytecodePatch : BytecodePatch(
    listOf(
        TooltipContentViewFingerprint
    )
) {
    override fun execute(context: BytecodeContext): PatchResult {

        TooltipContentViewFingerprint.result?.mutableMethod?.addInstruction(
            0,
            "return-void"
        ) ?: return TooltipContentViewFingerprint.toErrorResult()

        return PatchResultSuccess()
    }
}
