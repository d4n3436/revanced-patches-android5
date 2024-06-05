package app.revanced.patches.youtube.misc.spoofclient.fingerprints

import app.revanced.patcher.extensions.or
import app.revanced.patcher.fingerprint.method.impl.MethodFingerprint
import com.android.tools.smali.dexlib2.AccessFlags

internal object NerdsStatsVideoFormatBuilderFingerprint : MethodFingerprint(
    returnType = "Ljava/lang/String;",
    accessFlags = AccessFlags.PUBLIC or AccessFlags.STATIC,
    parameters = listOf("Lcom/google/android/libraries/youtube/innertube/model/media/FormatStreamModel;"),
    strings = listOf("codecs=\""),
)