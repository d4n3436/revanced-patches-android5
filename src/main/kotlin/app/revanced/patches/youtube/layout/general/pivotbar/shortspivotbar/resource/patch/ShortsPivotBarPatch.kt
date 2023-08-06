package app.revanced.patches.youtube.layout.general.pivotbar.shortspivotbar.resource.patch

import app.revanced.patcher.annotation.Description
import app.revanced.patcher.annotation.Name
import app.revanced.patcher.data.ResourceContext
import app.revanced.patcher.patch.PatchResult
import app.revanced.patcher.patch.PatchResultSuccess
import app.revanced.patcher.patch.ResourcePatch
import app.revanced.patcher.patch.annotations.DependsOn
import app.revanced.patcher.patch.annotations.Patch
import app.revanced.patches.youtube.layout.general.pivotbar.shortspivotbar.bytecode.patch.ShortsPivotBarBytecodePatch
import app.revanced.patches.youtube.misc.settings.resource.patch.SettingsPatch
import app.revanced.shared.annotation.YouTubeCompatibility
import app.revanced.shared.util.resources.ResourceHelper

@Patch
@Name("hide-shorts-navbar")
@Description("Hide navigation bar when playing shorts.")
@DependsOn(
    [
        SettingsPatch::class,
        ShortsPivotBarBytecodePatch::class
    ]
)
@YouTubeCompatibility
class ShortsPivotBarPatch : ResourcePatch {
    override fun execute(context: ResourceContext): PatchResult {

        /*
         add settings
         */
        ResourceHelper.addSettings4(
            context,
            "PREFERENCE_CATEGORY: REVANCED_SETTINGS",
            "PREFERENCE: LAYOUT_SETTINGS",
            "PREFERENCE_HEADER: GENERAL",
            "SETTINGS: SHORTS_COMPONENT.PARENT",
            "SETTINGS: SHORTS_COMPONENT_PARENT.B",
            "SETTINGS: HIDE_SHORTS_PLAYER_PIVOT_BAR"
        )

        ResourceHelper.patchSuccess(
            context,
            "hide-shorts-navbar"
        )

        return PatchResultSuccess()
    }

}