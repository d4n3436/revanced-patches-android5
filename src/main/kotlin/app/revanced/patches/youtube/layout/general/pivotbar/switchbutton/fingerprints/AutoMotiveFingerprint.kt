package app.revanced.patches.youtube.layout.general.pivotbar.switchbutton.fingerprints

import app.revanced.patcher.fingerprint.method.impl.MethodFingerprint
import com.android.tools.smali.dexlib2.Opcode

object AutoMotiveFingerprint : MethodFingerprint(
    opcodes = listOf(
        Opcode.CONST_STRING,
        Opcode.GOTO,
        Opcode.INVOKE_STATIC,
        Opcode.MOVE_RESULT,
        Opcode.IF_EQZ
    ),
    strings = listOf("Android Automotive")
)