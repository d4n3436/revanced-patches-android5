package app.revanced.patches.youtube.misc.updatescreen.resource.patch

import app.revanced.patcher.annotation.Description
import app.revanced.patcher.annotation.Name
import app.revanced.patcher.data.ResourceContext
import app.revanced.patcher.patch.ResourcePatch
import app.revanced.patcher.patch.annotations.DependsOn
import app.revanced.patcher.patch.annotations.Patch
import app.revanced.patches.youtube.misc.updatescreen.bytecode.patch.UpdateScreenBytecodePatch
import app.revanced.shared.annotation.YouTubeCompatibility
import app.revanced.shared.util.resources.ResourceHelper

@Patch
@Name("disable-update-screen")
@Description("Disables the app update screen.")
@DependsOn([UpdateScreenBytecodePatch::class])
@YouTubeCompatibility
class UpdateScreenPatch : ResourcePatch {
    override fun execute(context: ResourceContext) {

        ResourceHelper.patchSuccess(
            context,
            "disable-new-version"
        )
    }
}