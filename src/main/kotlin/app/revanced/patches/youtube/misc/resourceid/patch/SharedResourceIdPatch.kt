package app.revanced.patches.youtube.misc.resourceid.patch

import app.revanced.patcher.annotation.Name
import app.revanced.patcher.data.ResourceContext
import app.revanced.patcher.patch.ResourcePatch
import app.revanced.patcher.patch.annotations.DependsOn
import app.revanced.shared.annotation.YouTubeCompatibility
import app.revanced.shared.patches.mapping.ResourceMappingPatch

@Name("shared-resource-id")
@DependsOn([ResourceMappingPatch::class])
@YouTubeCompatibility
class SharedResourcdIdPatch : ResourcePatch {
    internal companion object {
        var dislikeButtonId: Long = -1
        var infoCardAccessibilityTeaserVibrateDurationMsId: Long = -1
        var inVideoProgrammingOverlayId: Long = -1
        var playerHeadingViewContainerId: Long = -1
        var abclistmenuitemLabelId: Long = -1
        var accessibilityProgressTimeLabelId: Long = -1
        var accountSwitcherAccessibilityLabelId: Long = -1
        var appearanceStringId: Long = -1
        var backgroundCategoryLabelId: Long = -1
        var bottompaneloverlaytextLabelId: Long = -1
        var bottomUiContainerResourceId: Long = -1
        var controlsLayoutStubResourceId: Long = -1
        var educationTextViewResourceId: Long = -1
        var floatybarQueueLabelId: Long = -1
        var imageOnlyTabId: Long = -1
        var imageWithTextTabId: Long = -1
        var layoutCircle: Long = -1
        var layoutIcon: Long = -1
        var layoutVideo: Long = -1
        var liveChatButtonId: Long = -1
        var timebarColorLabelId: Long = -1
        var tooltipLabelId: Long = -1
        var videoqualityfragmentLabelId: Long = -1
    }

    override fun execute(context: ResourceContext) {

        fun findSharedResourceId(type: String, name: String) = ResourceMappingPatch
            .resourceMappings
            .single { it.type == type && it.name == name }.id

        dislikeButtonId = findSharedResourceId("id", "dislike_button")
        infoCardAccessibilityTeaserVibrateDurationMsId = findSharedResourceId("integer", "info_card_accessibility_teaser_vibrate_duration_ms")
        inVideoProgrammingOverlayId = findSharedResourceId("layout", "youtube_invideo_programming_overlay")
        playerHeadingViewContainerId = findSharedResourceId("layout", "player_heading_view_container")
        abclistmenuitemLabelId = findSharedResourceId("layout", "abc_list_menu_item_layout")
        accessibilityProgressTimeLabelId = findSharedResourceId("string", "accessibility_player_progress_time")
        accountSwitcherAccessibilityLabelId = findSharedResourceId("string", "account_switcher_accessibility_label")
        appearanceStringId = findSharedResourceId("string", "app_theme_appearance_dark")
        backgroundCategoryLabelId = findSharedResourceId("string", "pref_background_and_offline_category")
        bottompaneloverlaytextLabelId = findSharedResourceId("id", "bottom_panel_overlay_text")
        bottomUiContainerResourceId = findSharedResourceId("id", "bottom_ui_container_stub")
        controlsLayoutStubResourceId = findSharedResourceId("id", "controls_layout_stub")
        educationTextViewResourceId = findSharedResourceId("id", "user_education_text_view")
        floatybarQueueLabelId = findSharedResourceId("string", "floaty_bar_queue_status")
        imageOnlyTabId = findSharedResourceId("layout", "image_only_tab")
        imageWithTextTabId = findSharedResourceId("layout", "image_with_text_tab")
        layoutCircle = findSharedResourceId("layout", "endscreen_element_layout_circle")
        layoutIcon = findSharedResourceId("layout", "endscreen_element_layout_icon")
        layoutVideo = findSharedResourceId("layout", "endscreen_element_layout_video")
        liveChatButtonId = findSharedResourceId("id", "live_chat_overlay_button")
        timebarColorLabelId = findSharedResourceId("color", "inline_time_bar_progress_color")
        tooltipLabelId = findSharedResourceId("layout", "tooltip_content_view")
        videoqualityfragmentLabelId = findSharedResourceId("layout", "video_quality_bottom_sheet_list_fragment_title")
    }
}