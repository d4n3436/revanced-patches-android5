# YouTube ReVanced for Android 5

Unofficial ReVanced Extended for YouTube 16.40.36. Based on [kitadai31's fork of ReVanced Extended for Android 6/7](https://github.com/kitadai31/revanced-patches-android6-7)

Around half of the patches currently work.

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
java -jar revanced-cli.jar patch YouTube_16.40.36.apk -p -o YouTube_ReVanced_16.40.36.apk -b revanced-patches-2.160.9.jar -m revanced-integrations-0.96.6.apk -i microg-support -i spoof-player-parameters -i client-spoof -i hide-video-ads -i enable-minimized-playback -i disable-update-screen --exclusive
```

- Or patch with all the supported patches (ignore the errors):
```
java -jar revanced-cli.jar patch YouTube_16.40.36.apk -p -o YouTube_ReVanced_16.40.36.apk -b revanced-patches-2.160.9.jar -m revanced-integrations-0.96.6.apk -e custom-seekbar-color -e optimize-resource
```

Refer to the original [repository](https://github.com/kitadai31/revanced-patches-android6-7) for more info.
