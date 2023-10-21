package app.revanced.patches.youtube.misc.updatescreen.bytecode.fingerprints

import app.revanced.patcher.extensions.or
import app.revanced.patcher.fingerprint.method.impl.MethodFingerprint
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode

object UpdateScreenFingerprint : MethodFingerprint(
    returnType = "Landroid/content/Intent",
    accessFlags = AccessFlags.PUBLIC or AccessFlags.FINAL,
    parameters = listOf("Landroid/content/Intent"),
    opcodes = listOf(
        Opcode.IGET_OBJECT,
        Opcode.IGET_OBJECT,
        Opcode.SGET_OBJECT,
        Opcode.INVOKE_VIRTUAL,
        Opcode.MOVE_RESULT_OBJECT,
        Opcode.IGET_OBJECT,
        Opcode.IF_NEZ,
        Opcode.SGET_OBJECT,
        Opcode.IGET_OBJECT
    ),
    strings = listOf("show_force_upgrade", "forward_intent")
)