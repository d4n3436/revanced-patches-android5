package app.revanced.patches.youtube.misc.returnyoutubedislike.bytecode.fingerprints

import app.revanced.patcher.extensions.or
import app.revanced.patcher.fingerprint.method.impl.MethodFingerprint
import app.revanced.patches.youtube.misc.resourceid.patch.SharedResourcdIdPatch.Companion.dislikeButtonId
import app.revanced.shared.util.bytecode.isWideLiteralExists
import com.android.tools.smali.dexlib2.AccessFlags

object ButtonTagFingerprint : MethodFingerprint(
    returnType = "V",
    accessFlags = AccessFlags.PUBLIC or AccessFlags.FINAL,
    parameters = listOf("L"),
    customFingerprint = { methodDef, _ -> methodDef.isWideLiteralExists(dislikeButtonId) }
)