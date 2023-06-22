# YouTube ReVanced for Android 5

Currently only a minimal set of patches work (`microg-support`, `protobuf-spoof`, `client-spoof`, `hide-video-ads` and others).

## How to patch

- Prerequisites:
  - [Vanced MicroG](https://github.com/TeamVanced/VancedMicroG/releases/tag/v0.2.22.212658-212658001) installed on your device
  - [Patches from the Release section](https://github.com/d4n3436/revanced-patches-android5/releases)
  - [YouTube APK](https://www.apkmirror.com/apk/google-inc/youtube/youtube-16-40-36-release/youtube-16-40-36-android-apk-download/)
  - [revanced-integrations for Android 5](https://github.com/d4n3436/revanced-integrations/releases)
  - Zulu OpenJDK 17
  - [revanced-cli v2.21.1](https://github.com/revanced/revanced-cli/releases/tag/v2.21.1)

- Patch with the following command (file names changed for brevity):
```
java -jar revanced-cli.jar -a YouTube_16.40.36.apk -o YouTube_ReVanced_16.40.36.apk -b revanced-patches-2.160.6.jar -m revanced-integrations-0.96.4.apk -i microg-support -i protobuf-spoof -i client-spoof -i hide-video-ads -i enable-minimized-playback --exclusive
```

Refer to the original [repository](https://github.com/kitadai31/revanced-patches-android6-7) for more info.

### Note
I won't improve support for the remaining patches as I don't have any incentives to do so. I don't have any Android 5 device and I only modified the patches for testing.

I will, however, maintain the spoof patches and update them to keep the app working.
