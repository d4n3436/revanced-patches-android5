# YouTube ReVanced for Android 5

Unofficial ReVanced Extended for YouTube 16.40.36. Based on [kitadai31's fork of ReVanced Extended for Android 6/7](https://github.com/kitadai31/revanced-patches-android6-7)

## How to patch

- Prerequisites:
    - [Vanced MicroG](https://github.com/TeamVanced/VancedMicroG/releases/tag/v0.2.22.212658-212658001) installed on your device
    - [Patches from the Release section](https://github.com/d4n3436/revanced-patches-android5/releases)
    - [YouTube APK](https://www.apkmirror.com/apk/google-inc/youtube/youtube-16-40-36-release/youtube-16-40-36-android-apk-download/)
    - [revanced-integrations for Android 5](https://github.com/d4n3436/revanced-integrations/releases)
    - Zulu OpenJDK 17
    - [revanced-cli v3.1.1](https://github.com/ReVanced/revanced-cli/releases/tag/v3.1.1)

- Patch with the following command (file names changed for brevity):
```
java -jar revanced-cli.jar patch YouTube_16.40.36.apk -p -o YouTube_ReVanced_16.40.36.apk -b revanced-patches-2.161.3.jar -m revanced-integrations-0.97.0.apk
```

- Or use [auto-cli](https://github.com/taku-nm/auto-cli) for automated patching. The prerequisites are downloaded automatically.
- You can also use the [fork of ReVanced Manager for Android 5-7](https://github.com/kitadai31/revanced-manager-android5-7).

## 🧩 Patches List

### [📦 `com.google.android.youtube`](https://play.google.com/store/apps/details?id=com.google.android.youtube)
<details>

| 💊 Patch | 📜 Description | 🏹 Target Version |
|:--------:|:--------------:|:-----------------:|
| `client-spoof` | Spoofs the YouTube client to prevent playback issues. | 16.40.36 |
| `custom-branding-icon-afn-blue` | Changes the YouTube launcher icon (Afn / Blue). | 16.40.36 |
| `custom-branding-icon-afn-red` | Changes the YouTube launcher icon (Afn / Red). | 16.40.36 |
| `custom-branding-icon-revancify` | Changes the YouTube launcher icon (Revancify). | 16.40.36 |
| `custom-branding-name` | Changes the YouTube launcher name to your choice (defaults to ReVanced Extended). | 16.40.36 |
| `custom-video-speed` | Adds more video speed options. | 16.40.36 |
| `default-video-quality` | Adds ability to set default video quality settings. | 16.40.36 |
| `default-video-speed` | Adds ability to set default video speed settings. | 16.40.36 |
| `disable-haptic-feedback` | Disable haptic feedback when swiping. | 16.40.36 |
| `disable-landscape-mode` | Disable landscape mode when entering fullscreen. | 16.40.36 |
| `disable-update-screen` | Disables the app update screen. | 16.40.36 |
| `enable-external-browser` | Use an external browser to open the url. | 16.40.36 |
| `enable-minimized-playback` | Enables minimized and background playback. | 16.40.36 |
| `enable-old-quality-layout` | Enables the original quality flyout menu. | 16.40.36 |
| `enable-open-links-directly` | Bypass URL redirects (youtube.com/redirect) when opening links in video descriptions. | 16.40.36 |
| `enable-seekbar-tapping` | Enables tap-to-seek on the seekbar of the video player. | 16.40.36 |
| `enable-tablet-miniplayer` | Enables the tablet mini player layout. | 16.40.36 |
| `enable-wide-searchbar` | Replaces the search icon with a wide search bar. This will hide the YouTube logo when active. | 16.40.36 |
| `force-premium-heading` | Forces premium heading on the home screen. | 16.40.36 |
| `force-vp9-codec` | Forces the VP9 codec for videos. | 16.40.36 |
| `header-switch` | Add switch to change header. | 16.40.36 |
| `hide-auto-captions` | Hide captions from being automatically enabled. | 16.40.36 |
| `hide-auto-player-popup-panels` | Hide automatic popup panels (playlist or live chat) on video player. | 16.40.36 |
| `hide-autoplay-button` | Hides the autoplay button in the video player. | 16.40.36 |
| `hide-cast-button` | Hides the cast button in the video player. | 16.40.36 |
| `hide-channel-watermark` | Hides creator's watermarks on videos. | 16.40.36 |
| `hide-comment-component` | Adds options to hide comment component under a video. | 16.40.36 |
| `hide-create-button` | Hides the create button in the navigation bar. | 16.40.36 |
| `hide-crowdfunding-box` | Hides the crowdfunding box between the player and video description. | 16.40.36 |
| `hide-email-address` | Hides the email address in the account switcher. | 16.40.36 |
| `hide-endscreen-cards` | Hides the suggested video cards at the end of a video in fullscreen. | 16.40.36 |
| `hide-endscreen-overlay` | Hide endscreen overlay on swipe controls. | 16.40.36 |
| `hide-firsttime-background-notification` | Disable notification when you launch background play for the first time. | 16.40.36 |
| `hide-fullscreen-panels` | Hides video description and comments panel in fullscreen view. | 16.40.36 |
| `hide-general-ads` | Hooks the method which parses the bytes into a ComponentContext to filter components. | 16.40.36 |
| `hide-info-cards` | Hides info-cards in videos. | 16.40.36 |
| `hide-live-chat-button` | Hides the live chat button in the video player. | 16.40.36 |
| `hide-mix-playlists` | Removes mix playlists from home feed and video player. | 16.40.36 |
| `hide-next-prev-button` | Hides the next prev button in the player controller. | 16.40.36 |
| `hide-player-captions-button` | Hides the captions button in the video player. | 16.40.36 |
| `hide-player-overlay-filter` | Remove the dark filter layer from the player's background. | 16.40.36 |
| `hide-shorts-button` | Hides the shorts button in the navigation bar. | 16.40.36 |
| `hide-shorts-component` | Hides other Shorts components. | 16.40.36 |
| `hide-snackbar` | Hides the snackbar action popup. | 16.40.36 |
| `hide-suggested-actions` | Hide the suggested actions bar inside the player. | 16.40.36 |
| `hide-time-and-seekbar` | Hides progress bar and time counter on videos. | 16.40.36 |
| `hide-tooltip-content` | Hides the tooltip box that appears on first install. | 16.40.36 |
| `hide-video-ads` | Removes ads in the video player. | 16.40.36 |
| `layout-switch` | Tricks the dpi to use some tablet/phone layouts. | 16.40.36 |
| `materialyou` | Enables MaterialYou theme for Android 12+ | 16.40.36 |
| `microg-support` | Allows YouTube ReVanced to run without root and under a different package name with Vanced MicroG. | 16.40.36 |
| `optimize-resource` | Removes duplicate resources and adds missing translation files from YouTube. | 16.40.36 |
| `os-version-check` | Check the Android version and show a warning if the device is Android 6.0 or higher. | 16.40.36 |
| `overlay-buttons` | Add overlay buttons for ReVanced Extended. | 16.40.36 |
| `patch-options` | Create an options.toml file. | 16.40.36 |
| `return-youtube-dislike` | Shows the dislike count of videos using the Return YouTube Dislike API. | 16.40.36 |
| `settings` | Applies mandatory patches to implement ReVanced settings into the application. | 16.40.36 |
| `sponsorblock` | Integrates SponsorBlock which allows skipping video segments such as sponsored content. | 16.40.36 |
| `spoof-app-version` | Spoof the YouTube client version to enable the new layout or restore old layout. | 16.40.36 |
| `spoof-player-parameters` | Spoofs player parameters to prevent the endless buffering issue. | 16.40.36 |
| `swipe-controls` | Adds volume and brightness swipe controls. | 16.40.36 |
| `switch-create-notification` | Switches the create button and notification button. | 16.40.36 |
| `theme` | Applies a custom theme (default: amoled). | 16.40.36 |
| `translations` | Add Crowdin Translations. | 16.40.36 |
</details>
