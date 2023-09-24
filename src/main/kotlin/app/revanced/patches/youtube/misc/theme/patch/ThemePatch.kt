package app.revanced.patches.youtube.misc.theme.patch

import app.revanced.patcher.annotation.Description
import app.revanced.patcher.annotation.Name
import app.revanced.patcher.data.ResourceContext
import app.revanced.patcher.patch.ResourcePatch
import app.revanced.patcher.patch.annotations.DependsOn
import app.revanced.patcher.patch.annotations.Patch
import app.revanced.patches.youtube.misc.settings.resource.patch.SettingsPatch
import app.revanced.shared.annotation.YouTubeCompatibility
import app.revanced.shared.patches.options.PatchOptions
import app.revanced.shared.patches.theme.bytecode.GeneralThemePatch
import app.revanced.shared.util.resources.ResourceHelper
import org.w3c.dom.Element

@Patch
@Name("theme")
@Description("Applies a custom theme (default: amoled).")
@DependsOn(
    [
        GeneralThemePatch::class,
        PatchOptions::class,
        SettingsPatch::class
    ]
)
@YouTubeCompatibility
class ThemePatch : ResourcePatch {
    override fun execute(context: ResourceContext) {

        arrayOf("values", "values-v31").forEach { valuesPath ->
           setTheme(context, valuesPath)
        }

        ResourceHelper.themePatchSuccess(
            context,
            "default",
            "amoled"
        )

        ResourceHelper.themePatchSuccess(
            context,
            "materialyou",
            "mix"
        )
    }
    companion object {

        fun setTheme(
            context: ResourceContext,
            valuesPath: String
        ) {
            context.xmlEditor["res/$valuesPath/colors.xml"].use { editor ->
                val resourcesNode = editor.file.getElementsByTagName("resources").item(0) as Element

                for (i in 0 until resourcesNode.childNodes.length) {
                    val node = resourcesNode.childNodes.item(i) as? Element ?: continue

                    node.textContent = when (node.getAttribute("name")) {
                        "yt_black0", "yt_black1", "yt_black1_opacity95", "yt_black1_opacity98", "yt_black2", "yt_black3",
                        "yt_black4", "yt_status_bar_background_dark", "material_grey_850" -> PatchOptions.darkThemeBackgroundColor

                        else -> continue
                    }
                }
            }
        }
    }
}
