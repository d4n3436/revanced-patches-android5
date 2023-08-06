package app.revanced.patches.youtube.ads.video.bytecode.patch

import app.revanced.patcher.annotation.Name
import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.patch.annotations.DependsOn
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.PatchResult
import app.revanced.patcher.patch.PatchResultSuccess
import app.revanced.shared.annotation.YouTubeCompatibility
import app.revanced.shared.patches.videoads.GeneralVideoAdsPatch
import app.revanced.shared.util.bytecode.BytecodeHelper
import app.revanced.shared.util.integrations.Constants.ADS_PATH

@DependsOn(
    [
        GeneralVideoAdsPatch::class
    ]
)
@Name("hide-video-ads-bytecode-patch")
@YouTubeCompatibility
class VideoAdsBytecodePatch : BytecodePatch() {
    override fun execute(context: BytecodeContext): PatchResult {
        val INTEGRATIONS_CLASS_DESCRIPTOR = "$ADS_PATH/HideVideoAdsPatch;->hideVideoAds()Z"

        GeneralVideoAdsPatch.injectLegacyAds(INTEGRATIONS_CLASS_DESCRIPTOR)

        GeneralVideoAdsPatch.injectMainstreamAds(INTEGRATIONS_CLASS_DESCRIPTOR)

        BytecodeHelper.patchStatus(context, "VideoAds")

        return PatchResultSuccess()
    }
}
