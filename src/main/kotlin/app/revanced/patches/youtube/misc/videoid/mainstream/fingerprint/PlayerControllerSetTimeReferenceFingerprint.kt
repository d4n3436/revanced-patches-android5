package app.revanced.patches.youtube.misc.videoid.mainstream.fingerprint

import app.revanced.patcher.fingerprint.method.impl.MethodFingerprint
import com.android.tools.smali.dexlib2.Opcode

object PlayerControllerSetTimeReferenceFingerprint : MethodFingerprint(
    opcodes = listOf(Opcode.INVOKE_DIRECT_RANGE, Opcode.IGET_OBJECT),
    strings = listOf("Media progress reported outside media playback: ")
)