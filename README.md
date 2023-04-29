# YouTube ReVanced for Android 5

Currently only a minimal set of patches work (`microg-support`, `protobuf-spoof`, `client-spoof`, `hide-video-ads` and others).

## How to patch:

- Prerequisites:
  - [Vanced MicroG](https://github.com/TeamVanced/VancedMicroG/releases/tag/v0.2.22.212658-212658001) installed on your device
  - [Patches from the Release section](https://github.com/d4n3436/revanced-patches-android5/releases)
  - [YouTube APK](https://www.apkmirror.com/apk/google-inc/youtube/youtube-16-40-36-release/youtube-16-40-36-android-apk-download/)
  - revanced-integrations from [here](https://github.com/kitadai31/revanced-integrations/releases)
  - [revanced-cli](https://github.com/revanced/revanced-cli/releases)

- Patch with the following command:
```
java -jar revanced-cli.jar -a YouTube_16.40.36.apk -o YouTube_ReVanced_16.40.36.apk -b revanced-patches-2.160.4.jar -m revanced-integrations-0.96.3.apk -i microg-support -i protobuf-spoof -i client-spoof -i hide-video-ads --exclusive
```

- Enable protobuf spoof in settings (Settings -> ReVanced settings -> Extended settings -> Enable protobuf spoof)

Refer to the original [repository](https://github.com/kitadai31/revanced-patches-android6-7) for more info.
