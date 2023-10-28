package app.revanced.patches.youtube.misc.protobufpoof.fingerprints

import app.revanced.patcher.extensions.or
import app.revanced.patcher.fingerprint.method.impl.MethodFingerprint
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode

object PlayerParameterBuilderFingerprint : MethodFingerprint(
    accessFlags = AccessFlags.PUBLIC or AccessFlags.FINAL,
    returnType = "L",
    parameters = listOf(
        "Ljava/lang/String;",
        "[B",
        "Ljava/lang/String;",
        "Ljava/lang/String;",
        "I",
        "I",
        "Ljava/util/Set;",
        "Ljava/lang/String;",
        "L",
        "Z"
    ),
    opcodes = listOf(
        Opcode.NEW_INSTANCE,
        Opcode.IGET_OBJECT,
        Opcode.INVOKE_DIRECT,
        Opcode.INVOKE_VIRTUAL
    ),
    customFingerprint = { methodDef, _ ->
        methodDef.name == "b"
    }
)