package app.revanced.patches.youtube.extended.forcevp9.bytecode.fingerprints

import app.revanced.patcher.extensions.or
import app.revanced.patcher.fingerprint.method.impl.MethodFingerprint
import org.jf.dexlib2.AccessFlags
import org.jf.dexlib2.Opcode

object Vp9SecondaryFingerprint : MethodFingerprint(
    returnType = "Z",
    accessFlags = AccessFlags.PUBLIC or AccessFlags.STATIC,
    parameters = listOf("L", "I"),
    opcodes = listOf(
        Opcode.RETURN,
        Opcode.CONST_4,
        Opcode.RETURN
    )
)
