package app.revanced.patches.youtube.layout.player.infocards.bytecode.fingerprints

import app.revanced.patcher.annotation.Name
import app.revanced.patcher.extensions.or
import app.revanced.patcher.fingerprint.method.impl.MethodFingerprint
import app.revanced.patches.youtube.misc.resourceid.patch.SharedResourcdIdPatch
import app.revanced.shared.annotation.YouTubeCompatibility
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.WideLiteralInstruction

@Name("infocards-incognito-parent-fingerprint")
@YouTubeCompatibility
object InfocardsIncognitoParentFingerprint : MethodFingerprint(
    returnType = "V",
    accessFlags = AccessFlags.PUBLIC or AccessFlags.CONSTRUCTOR,
    customFingerprint = { methodDef, _ ->
        methodDef.implementation?.instructions?.any {
            it.opcode.ordinal == Opcode.CONST.ordinal &&
                    (it as? WideLiteralInstruction)?.wideLiteral == SharedResourcdIdPatch.infoCardAccessibilityTeaserVibrateDurationMsId
        } == true
    }
)