package app.revanced.shared.fingerprints

import app.revanced.patcher.fingerprint.method.impl.MethodFingerprint
import app.revanced.patches.youtube.misc.resourceid.patch.SharedResourcdIdPatch
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.WideLiteralInstruction

object LayoutConstructorFingerprint : MethodFingerprint(
    customFingerprint = {
            methodDef, _ -> methodDef.definingClass.endsWith("YouTubeControlsOverlay;") &&
            methodDef.implementation?.instructions?.any {
                it.opcode.ordinal == Opcode.CONST.ordinal &&
                        (it as? WideLiteralInstruction)?.wideLiteral == SharedResourcdIdPatch.playerHeadingViewContainerId
            } == true
    }
)