package app.revanced.patches.youtube.misc.materialyou.patch

import app.revanced.patcher.annotation.Description
import app.revanced.patcher.annotation.Name
import app.revanced.patcher.data.ResourceContext
import app.revanced.patcher.patch.PatchException
import app.revanced.patcher.patch.ResourcePatch
import app.revanced.patcher.patch.annotations.DependsOn
import app.revanced.patcher.patch.annotations.Patch
import app.revanced.patches.youtube.misc.settings.resource.patch.SettingsPatch
import app.revanced.shared.annotation.YouTubeCompatibility
import app.revanced.shared.patches.theme.bytecode.GeneralThemePatch
import app.revanced.shared.util.resources.ResourceHelper
import app.revanced.shared.util.resources.ResourceUtils.copyXmlNode
import java.nio.file.Files
import java.nio.file.StandardCopyOption

@Patch(false)
@Name("materialyou")
@Description("Enables MaterialYou theme for Android 12+")
@DependsOn(
    [
        GeneralThemePatch::class,
        SettingsPatch::class
    ]
)
@YouTubeCompatibility
class MaterialYouPatch : ResourcePatch {
    override fun execute(context: ResourceContext) {

        val drawables1 = "drawable-night-v31" to arrayOf(
            "new_content_dot_background.xml"
        )

        val drawables2 = "drawable-v31" to arrayOf(
            "new_content_count_background.xml",
            "new_content_dot_background.xml"
        )

        val layout1 = "layout-v31" to arrayOf(
            "new_content_count.xml"
        )

        arrayOf(drawables1, drawables2, layout1).forEach { (path, resourceNames) ->
            try {
                Files.createDirectory(context["res"].resolve(path).toPath())
            } catch (e: NoSuchMethodError) {
                throw PatchException("Material You needs Android 12+")
            }

            resourceNames.forEach { name ->
                val monetPath = "$path/$name"

                Files.copy(
                    this.javaClass.classLoader.getResourceAsStream("youtube/materialyou/$monetPath")!!,
                    context["res"].resolve(monetPath).toPath(),
                    StandardCopyOption.REPLACE_EXISTING
                )
            }
        }

         val sourcePath = this.javaClass.classLoader.getResourceAsStream("youtube/materialyou/host/values-v31/colors.xml")!!
         val relativePath = context.xmlEditor["res/values-v31/colors.xml"]

        "resources".copyXmlNode(
            context.xmlEditor[sourcePath],
            relativePath
        ).close()

        /*
         add settings
         */

        ResourceHelper.themePatchSuccess(
            context,
            "default",
            "materialyou"
        )
    }
}
