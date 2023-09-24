package app.revanced.patches.youtube.button.overlaybuttons.resource.patch

import app.revanced.patcher.annotation.Description
import app.revanced.patcher.annotation.Name
import app.revanced.patcher.data.ResourceContext
import app.revanced.patcher.patch.ResourcePatch
import app.revanced.patcher.patch.annotations.DependsOn
import app.revanced.patcher.patch.annotations.Patch
import app.revanced.patches.youtube.button.autorepeat.patch.AutoRepeatPatch
import app.revanced.patches.youtube.button.overlaybuttons.bytecode.patch.OverlayButtonsBytecodePatch
import app.revanced.patches.youtube.misc.settings.resource.patch.SettingsPatch
import app.revanced.shared.annotation.YouTubeCompatibility
import app.revanced.shared.patches.options.PatchOptions
import app.revanced.shared.util.resources.ResourceHelper
import app.revanced.shared.util.resources.ResourceUtils
import app.revanced.shared.util.resources.ResourceUtils.copyResources
import app.revanced.shared.util.resources.ResourceUtils.copyXmlNode

@Patch
@Name("overlay-buttons")
@Description("Add overlay buttons for ReVanced Extended.")
@DependsOn(
    [
        AutoRepeatPatch::class,
        OverlayButtonsBytecodePatch::class,
        PatchOptions::class,
        SettingsPatch::class,
    ]
)
@YouTubeCompatibility
class OverlayButtonsResourcePatch : ResourcePatch {
    override fun execute(context: ResourceContext) {

        val icon = PatchOptions.Overlay_Buttons_Icon

        /*
         * Copy resources
         */
        arrayOf(
            ResourceUtils.ResourceGroup(
                "drawable",
                "playlist_repeat_button.xml",
                "playlist_shuffle_button.xml",
                "revanced_repeat_icon.xml"
            )
        ).forEach { resourceGroup ->
            context.copyResources("youtube/overlaybuttons", resourceGroup)
        }

        arrayOf(
            ResourceUtils.ResourceGroup(
                "drawable-xxhdpi",
                "ic_fullscreen_vertical_button.png",
                "ic_vr.png",
                "quantum_ic_fullscreen_exit_grey600_24.png",
                "quantum_ic_fullscreen_exit_white_24.png",
                "quantum_ic_fullscreen_grey600_24.png",
                "quantum_ic_fullscreen_white_24.png",
                "revanced_copy_icon.png",
                "revanced_copy_icon_with_time.png",
                "revanced_download_icon.png",
                "yt_outline_arrow_repeat_1_white_24.png",
                "yt_outline_arrow_shuffle_1_white_24.png",
                "yt_outline_screen_full_exit_white_24.png",
                "yt_outline_screen_full_white_24.png"
            )
        ).forEach { resourceGroup ->
            context.copyResources("youtube/overlaybuttons/$icon", resourceGroup)
        }

        /*
         * Copy preference fragments
         */

        context.copyXmlNode("youtube/overlaybuttons/host", "layout/youtube_controls_bottom_ui_container.xml", "android.support.constraint.ConstraintLayout")

        val container = context["res/layout/youtube_controls_bottom_ui_container.xml"]
        container.writeText(
            container.readText()
            .replace(
                "yt:layout_constraintRight_toLeftOf=\"@id/fullscreen_button",
                "yt:layout_constraintRight_toLeftOf=\"@+id/copy_button"
            ).replace(
                "60",
                "48"
            ).replace(
                "paddingBottom=\"16",
                "paddingBottom=\"28"
            ).replace(
                "paddingLeft=\"12",
                "paddingLeft=\"0"
            ).replace(
                "paddingRight=\"12",
                "paddingRight=\"0"
            )
        )

        /*
         add settings
         */
        ResourceHelper.addSettings(
            context,
            "PREFERENCE_CATEGORY: REVANCED_EXTENDED_SETTINGS",
            "PREFERENCE: OVERLAY_BUTTONS",
            "SETTINGS: OVERLAY_BUTTONS"
        )

        ResourceHelper.patchSuccess(
            context,
            "overlay-buttons"
        )
    }
}