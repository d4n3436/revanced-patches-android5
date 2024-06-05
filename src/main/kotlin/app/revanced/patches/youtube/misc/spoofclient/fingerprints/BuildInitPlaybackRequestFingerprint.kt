package app.revanced.patches.youtube.misc.spoofclient.fingerprints

import app.revanced.patcher.fingerprint.method.impl.MethodFingerprint
import com.android.tools.smali.dexlib2.Opcode

internal object BuildInitPlaybackRequestFingerprint : MethodFingerprint(
    returnType = "Lorg/chromium/net/UrlRequest\$Builder;",
    opcodes = listOf(
        Opcode.MOVE_RESULT_OBJECT,
        Opcode.IGET_OBJECT, // Moves the request URI string to a register to build the request with.
    ),
    strings = listOf(
        "Content-Type",
        "Range",
    ),
)