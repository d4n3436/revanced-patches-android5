package app.revanced.patches.youtube.misc.protobufspoof.fingerprints

import app.revanced.patcher.extensions.or
import app.revanced.patcher.fingerprint.method.impl.MethodFingerprint
import app.revanced.shared.util.bytecode.isWideLiteralExists
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode

object PlayerResponseModelImplRecommendedLevel : MethodFingerprint(
    returnType = "I",
    accessFlags = AccessFlags.PUBLIC or AccessFlags.FINAL,
    parameters = emptyList(),
    opcodes = listOf(
        Opcode.SGET_OBJECT,
        Opcode.IGET,
        Opcode.RETURN
    ),
    customFingerprint = { methodDef, _ ->
        methodDef.definingClass.endsWith("/PlayerResponseModelImpl;") && methodDef.isWideLiteralExists(
            55735497
        )
    }
)