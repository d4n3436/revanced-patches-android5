package app.revanced.patches.youtube.misc.spoofclient.fingerprints

import app.revanced.patcher.extensions.or
import app.revanced.patcher.fingerprint.method.impl.MethodFingerprint
import app.revanced.patches.youtube.misc.spoofclient.fingerprints.CreatePlayerRequestBodyWithModelFingerprint.indexOfModelInstruction
import app.revanced.patches.youtube.misc.spoofclient.fingerprints.CreatePlayerRequestBodyWithModelFingerprint.indexOfReleaseInstruction
import app.revanced.shared.util.bytecode.getReference
import app.revanced.shared.util.bytecode.indexOfFirstInstruction
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.Method
import com.android.tools.smali.dexlib2.iface.reference.FieldReference

object CreatePlayerRequestBodyWithModelFingerprint : MethodFingerprint(
    returnType = "L",
    accessFlags = AccessFlags.PUBLIC or AccessFlags.FINAL,
    parameters = emptyList(),
    opcodes = listOf(Opcode.OR_INT_LIT16),
    customFingerprint = { methodDef, _ ->
        indexOfModelInstruction(methodDef) >= 0
                && indexOfReleaseInstruction(methodDef) >= 0
    }
) {
    fun indexOfModelInstruction(methodDef: Method) =
        methodDef.indexOfFirstInstruction {
            getReference<FieldReference>().toString() == "Landroid/os/Build;->MODEL:Ljava/lang/String;"
        }

    fun indexOfReleaseInstruction(methodDef: Method) =
        methodDef.indexOfFirstInstruction {
            getReference<FieldReference>().toString() == "Landroid/os/Build${'$'}VERSION;->RELEASE:Ljava/lang/String;"
        }
}