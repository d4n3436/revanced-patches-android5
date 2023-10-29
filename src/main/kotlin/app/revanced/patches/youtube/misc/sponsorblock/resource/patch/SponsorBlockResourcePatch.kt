package app.revanced.patches.youtube.misc.sponsorblock.resource.patch

import app.revanced.patcher.annotation.Description
import app.revanced.patcher.annotation.Name
import app.revanced.patcher.data.ResourceContext
import app.revanced.patcher.patch.ResourcePatch
import app.revanced.patcher.patch.annotations.DependsOn
import app.revanced.patcher.patch.annotations.Patch
import app.revanced.patches.youtube.misc.settings.resource.patch.SettingsPatch
import app.revanced.patches.youtube.misc.sponsorblock.bytecode.patch.SponsorBlockBytecodePatch
import app.revanced.patches.youtube.misc.sponsorblock.bytecode.patch.SponsorBlockSecondaryBytecodePatch
import app.revanced.shared.annotation.YouTubeCompatibility
import app.revanced.shared.util.resources.ResourceHelper
import app.revanced.shared.util.resources.ResourceUtils
import app.revanced.shared.util.resources.ResourceUtils.copyResources
import app.revanced.shared.util.resources.ResourceUtils.copyXmlNode

// TODO: Fix (16.40.36) and include by default
@Patch(false)
@Name("sponsorblock")
@Description("Integrates SponsorBlock which allows skipping video segments such as sponsored content.")
@DependsOn(
    [
        SettingsPatch::class,
        SponsorBlockBytecodePatch::class,
        SponsorBlockSecondaryBytecodePatch::class
    ]
)
@YouTubeCompatibility
class SponsorBlockResourcePatch : ResourcePatch {

    override fun execute(context: ResourceContext) {
        /*
         merge SponsorBlock drawables to main drawables
         */

        arrayOf(
            ResourceUtils.ResourceGroup(
                "layout",
                "inline_sponsor_overlay.xml",
                "new_segment.xml",
                "skip_sponsor_button.xml"
            ),
            ResourceUtils.ResourceGroup(
                // required resource for back button, because when the base APK is used, this resource will not exist
                "drawable",
                "ic_sb_adjust.xml",
                "ic_sb_compare.xml",
                "ic_sb_edit.xml",
                "ic_sb_logo.xml",
                "ic_sb_publish.xml",
                "ic_sb_voting.xml"
            )
        ).forEach { resourceGroup ->
            context.copyResources("youtube/sponsorblock", resourceGroup)
        }

        /*
        merge xml nodes from the host to their real xml files
         */

        // copy nodes from host resources to their real xml files
        val hostingResourceStream =
            javaClass.classLoader.getResourceAsStream("youtube/sponsorblock/host/layout/youtube_controls_layout.xml")!!

        val targetXmlEditor = context.xmlEditor["res/layout/youtube_controls_layout.xml"]
        "com.google.android.apps.youtube.app.player.overlay.ControlsRelativeLayout".copyXmlNode(
            context.xmlEditor[hostingResourceStream],
            targetXmlEditor
        ).also {
            val children = targetXmlEditor.file.getElementsByTagName("com.google.android.apps.youtube.app.player.overlay.ControlsRelativeLayout").item(0).childNodes

            // Replace the startOf with the voting button view so that the button does not overlap
            for (i in 1 until children.length) {
                val view = children.item(i)

                // Replace the attribute for a specific node only
                if (!view.hasAttributes())
                    continue

                var value = view.attributes.getNamedItem("android:id").nodeValue


                if (!(value.endsWith("live_chat_overlay_button") || value.endsWith("player_learn_more_button"))) continue

                // voting button id from the voting button view from the youtube_controls_layout.xml host file
                val SBButtonId = "@+id/voting_button"

                view.attributes.getNamedItem("android:layout_toStartOf").nodeValue = SBButtonId

                break
            }
        }.close() // close afterwards

        /*
         add ReVanced Settings
         */
        ResourceHelper.addReVancedSettings(
            context,
            "PREFERENCE: SPONSOR_BLOCK"
        )

        ResourceHelper.patchSuccess(
            context,
            "sponsorblock"
        )
    }
}