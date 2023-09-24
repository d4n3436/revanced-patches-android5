package app.revanced.patches.youtube.layout.flyoutpanel.flyoutpanel.patch

import app.revanced.patcher.annotation.Description
import app.revanced.patcher.annotation.Name
import app.revanced.patcher.data.ResourceContext
import app.revanced.patcher.patch.ResourcePatch
import app.revanced.patcher.patch.annotations.DependsOn
import app.revanced.patcher.patch.annotations.Patch
import app.revanced.patches.youtube.misc.litho.filter.patch.LithoFilterPatch
import app.revanced.patches.youtube.misc.settings.resource.patch.SettingsPatch
import app.revanced.shared.annotation.YouTubeCompatibility
import app.revanced.shared.util.resources.ResourceHelper

@Patch
@Name("hide-flyout-panel")
@Description("Adds options to hide player settings flyout panel.")
@DependsOn(
    [
        LithoFilterPatch::class,
        SettingsPatch::class
    ]
)
@YouTubeCompatibility
class FlyoutPanelPatch : ResourcePatch {
    override fun execute(context: ResourceContext) {

        /*
         add settings
         */
        ResourceHelper.addSettings2(
            context,
            "PREFERENCE_CATEGORY: REVANCED_SETTINGS",
            "PREFERENCE: LAYOUT_SETTINGS",
            "PREFERENCE_HEADER: FLYOUT_PANEL",
            "SETTINGS: FLYOUT_PANEL_COMPONENT"
        )

        ResourceHelper.patchSuccess(
            context,
            "hide-flyout-panel"
        )
    }
}
