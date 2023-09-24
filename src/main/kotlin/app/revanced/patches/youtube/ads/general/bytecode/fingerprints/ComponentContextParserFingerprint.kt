package app.revanced.patches.youtube.ads.general.bytecode.fingerprints

import app.revanced.patcher.fingerprint.method.impl.MethodFingerprint
import com.android.tools.smali.dexlib2.Opcode

object ComponentContextParserFingerprint : MethodFingerprint(
    opcodes = listOf(
        Opcode.INVOKE_INTERFACE,
        Opcode.MOVE_RESULT_OBJECT,
        Opcode.CHECK_CAST,
        Opcode.INVOKE_VIRTUAL,
        Opcode.GOTO,
        Opcode.INVOKE_VIRTUAL,
        Opcode.CONST_16,
        Opcode.INVOKE_VIRTUAL,
        Opcode.IPUT_OBJECT,
        Opcode.NEW_INSTANCE
    ),
    strings = listOf("LoggingProperties are not in proto format")
)