package app.revanced.patches.youtube.extended.layoutswitch.resource.patch

import app.revanced.patcher.annotation.Description
import app.revanced.patcher.annotation.Name
import app.revanced.patcher.data.ResourceContext
import app.revanced.patcher.patch.ResourcePatch
import app.revanced.patcher.patch.annotations.DependsOn
import app.revanced.patcher.patch.annotations.Patch
import app.revanced.patches.youtube.extended.layoutswitch.bytecode.patch.LayoutSwitchBytecodePatch
import app.revanced.patches.youtube.misc.settings.resource.patch.SettingsPatch
import app.revanced.shared.annotation.YouTubeCompatibility
import app.revanced.shared.util.resources.ResourceHelper

@Patch
@Name("layout-switch")
@Description("Tricks the dpi to use some tablet/phone layouts.")
@DependsOn(
    [
        LayoutSwitchBytecodePatch::class,
        SettingsPatch::class
    ]
)
@YouTubeCompatibility
class LayoutSwitchPatch : ResourcePatch {
    override fun execute(context: ResourceContext) {

        /*
         add settings
         */
        ResourceHelper.addSettings(
            context,
            "PREFERENCE_CATEGORY: REVANCED_EXTENDED_SETTINGS",
            "PREFERENCE: MISC_SETTINGS",
            "SETTINGS: LAYOUT_SWITCH"
        )

        ResourceHelper.patchSuccess(
            context,
            "layout-switch"
        )
    }
}