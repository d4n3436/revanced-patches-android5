package app.revanced.patches.youtube.video.speed.bytecode.fingerprints

import app.revanced.patcher.fingerprint.method.impl.MethodFingerprint
import com.android.tools.smali.dexlib2.Opcode

object VideoSpeedChangedFingerprint : MethodFingerprint(
    opcodes = listOf(
        Opcode.IGET_OBJECT,
        Opcode.IF_EQZ,
        Opcode.IF_EQZ,
        Opcode.IGET,
        Opcode.CHECK_CAST,
        Opcode.IGET_OBJECT,
        Opcode.INVOKE_VIRTUAL
    ),
    customFingerprint = { methodDef, _ -> methodDef.name == "onItemClick" }
)