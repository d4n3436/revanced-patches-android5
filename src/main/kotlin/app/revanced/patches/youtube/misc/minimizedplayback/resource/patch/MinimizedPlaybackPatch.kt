package app.revanced.patches.youtube.misc.minimizedplayback.resource.patch

import app.revanced.patcher.annotation.Description
import app.revanced.patcher.annotation.Name
import app.revanced.patcher.data.ResourceContext
import app.revanced.patcher.patch.annotations.DependsOn
import app.revanced.patcher.patch.annotations.Patch
import app.revanced.patcher.patch.PatchResult
import app.revanced.patcher.patch.PatchResultSuccess
import app.revanced.patcher.patch.ResourcePatch
import app.revanced.patches.youtube.misc.minimizedplayback.bytecode.patch.MinimizedPlaybackBytecodePatch
import app.revanced.patches.youtube.misc.settings.resource.patch.SettingsPatch
import app.revanced.shared.annotation.YouTubeCompatibility
import app.revanced.shared.util.resources.ResourceHelper

@Patch
@Name("enable-minimized-playback")
@Description("Enables minimized and background playback.")
@DependsOn(
    [
        MinimizedPlaybackBytecodePatch::class,
        SettingsPatch::class
    ]
)
@YouTubeCompatibility
class MinimizedPlaybackPatch : ResourcePatch {
    override fun execute(context: ResourceContext): PatchResult {

        ResourceHelper.patchSuccess(
            context,
            "enable-minimized-playback"
        )

        return PatchResultSuccess()
    }
}