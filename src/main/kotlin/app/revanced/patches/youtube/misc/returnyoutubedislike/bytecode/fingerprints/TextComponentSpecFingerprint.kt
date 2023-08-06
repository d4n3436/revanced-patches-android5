package app.revanced.patches.youtube.misc.returnyoutubedislike.bytecode.fingerprints

import app.revanced.patcher.fingerprint.method.impl.MethodFingerprint
import org.jf.dexlib2.iface.instruction.NarrowLiteralInstruction
import org.jf.dexlib2.AccessFlags
import org.jf.dexlib2.Opcode

object TextComponentSpecFingerprint : MethodFingerprint(
    returnType = "L",
    accessFlags = AccessFlags.STATIC.getValue(),
    opcodes = listOf(Opcode.CMPL_FLOAT),
    customFingerprint = { methodDef, _ ->
        methodDef.implementation!!.instructions.any {
            ((it as? NarrowLiteralInstruction)?.narrowLiteral == 16842907)
        }
    }
)