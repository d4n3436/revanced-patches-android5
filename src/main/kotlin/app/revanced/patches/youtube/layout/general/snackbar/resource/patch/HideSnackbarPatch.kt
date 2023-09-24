package app.revanced.patches.youtube.layout.general.snackbar.resource.patch

import app.revanced.patcher.annotation.Description
import app.revanced.patcher.annotation.Name
import app.revanced.patcher.data.ResourceContext
import app.revanced.patcher.patch.ResourcePatch
import app.revanced.patcher.patch.annotations.DependsOn
import app.revanced.patcher.patch.annotations.Patch
import app.revanced.patches.youtube.layout.general.snackbar.bytecode.patch.HideSnackbarBytecodePatch
import app.revanced.patches.youtube.misc.settings.resource.patch.SettingsPatch
import app.revanced.shared.annotation.YouTubeCompatibility
import app.revanced.shared.util.resources.ResourceHelper

@Patch
@Name("hide-snackbar")
@Description("Hides the snackbar action popup.")
@DependsOn(
    [
        HideSnackbarBytecodePatch::class,
        SettingsPatch::class
    ]
)
@YouTubeCompatibility
class HideSnackbarPatch : ResourcePatch {
    override fun execute(context: ResourceContext) {

        /*
         add settings
         */
        ResourceHelper.addSettings2(
            context,
            "PREFERENCE_CATEGORY: REVANCED_SETTINGS",
            "PREFERENCE: LAYOUT_SETTINGS",
            "PREFERENCE_HEADER: GENERAL",
            "SETTINGS: HIDE_SNACKBAR"
        )

        ResourceHelper.patchSuccess(
            context,
            "hide-snackbar"
        )
    }
}