package app.revanced.patches.youtube.misc.protobufspoof.fingerprints

import app.revanced.patcher.extensions.or
import app.revanced.patcher.fingerprint.method.impl.MethodFingerprint
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode

object StoryboardRendererSpecFingerprint : MethodFingerprint(
    accessFlags = AccessFlags.PUBLIC or AccessFlags.FINAL,
    returnType = "V",
    parameters = listOf("L"),
    opcodes = listOf(
        Opcode.MOVE_RESULT_OBJECT,
        Opcode.INVOKE_DIRECT,
        Opcode.MOVE_OBJECT,
        Opcode.IPUT_OBJECT,
        Opcode.GOTO,
        Opcode.MUL_INT_LIT16,
        Opcode.INT_TO_LONG,
        Opcode.IF_EQZ
    ),
    strings = listOf("\\|"),
)