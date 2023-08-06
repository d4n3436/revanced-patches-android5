package app.revanced.patches.youtube.misc.branding.name.patch

import app.revanced.patcher.annotation.Description
import app.revanced.patcher.annotation.Name
import app.revanced.patcher.data.ResourceContext
import app.revanced.patcher.patch.annotations.DependsOn
import app.revanced.patcher.patch.annotations.Patch
import app.revanced.patcher.patch.PatchResult
import app.revanced.patcher.patch.PatchResultSuccess
import app.revanced.patcher.patch.ResourcePatch
import app.revanced.patches.youtube.misc.settings.resource.patch.SettingsPatch
import app.revanced.shared.annotation.YouTubeCompatibility
import app.revanced.shared.extensions.startsWithAny
import app.revanced.shared.patches.options.PatchOptions
import app.revanced.shared.util.resources.ResourceHelper
import org.w3c.dom.Element

@Patch
@Name("custom-branding-name")
@DependsOn(
    [
        PatchOptions::class,
        SettingsPatch::class
    ]
)
@Description("Changes the YouTube launcher name to your choice (defaults to ReVanced Extended).")
@YouTubeCompatibility
class CustomBrandingNamePatch : ResourcePatch {
    override fun execute(context: ResourceContext): PatchResult {

        // App name
        val resourceFileNames = arrayOf("strings.xml")
        val appName = PatchOptions.YouTube_AppName

        context.forEach {
            if (!it.name.startsWithAny(*resourceFileNames)) return@forEach

            // for each file in the "layouts" directory replace all necessary attributes content
            context.xmlEditor[it.absolutePath].use { editor ->
            val resourcesNode = editor.file.getElementsByTagName("resources").item(0) as Element

                for (i in 0 until resourcesNode.childNodes.length) {
                    val node = resourcesNode.childNodes.item(i)
                    if (node !is Element) continue

                    val element = resourcesNode.childNodes.item(i) as Element
                    element.textContent = when (element.getAttribute("name")) {
                        "application_name" -> "$appName"
                        else -> continue
                    }
                }
            }
        }

        ResourceHelper.labelPatchSuccess(
            context,
            "$appName"
        )

        return PatchResultSuccess()
    }
}
