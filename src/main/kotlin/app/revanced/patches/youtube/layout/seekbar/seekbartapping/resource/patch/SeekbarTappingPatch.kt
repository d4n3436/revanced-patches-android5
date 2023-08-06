package app.revanced.patches.youtube.layout.seekbar.seekbartapping.resource.patch

import app.revanced.patcher.annotation.Description
import app.revanced.patcher.annotation.Name
import app.revanced.patcher.data.ResourceContext
import app.revanced.patcher.patch.annotations.DependsOn
import app.revanced.patcher.patch.annotations.Patch
import app.revanced.patcher.patch.PatchResult
import app.revanced.patcher.patch.PatchResultSuccess
import app.revanced.patcher.patch.ResourcePatch
import app.revanced.patches.youtube.layout.seekbar.seekbartapping.bytecode.patch.SeekbarTappingBytecodePatch
import app.revanced.patches.youtube.misc.settings.resource.patch.SettingsPatch
import app.revanced.shared.annotation.YouTubeCompatibility
import app.revanced.shared.util.resources.ResourceHelper

@Patch
@Name("enable-seekbar-tapping")
@Description("Enables tap-to-seek on the seekbar of the video player.")
@DependsOn(
    [
        SeekbarTappingBytecodePatch::class,
        SettingsPatch::class
    ]
)
@YouTubeCompatibility
class SeekbarTappingPatch : ResourcePatch {
    override fun execute(context: ResourceContext): PatchResult {

        /*
         add settings
         */
        ResourceHelper.addSettings2(
            context,
            "PREFERENCE_CATEGORY: REVANCED_SETTINGS",
            "PREFERENCE: LAYOUT_SETTINGS",
            "PREFERENCE_HEADER: SEEKBAR",
            "SETTINGS: ENABLE_SEEKBAR_TAPPING"
        )

        ResourceHelper.patchSuccess(
            context,
            "enable-seekbar-tapping"
        )

        return PatchResultSuccess()
    }
}