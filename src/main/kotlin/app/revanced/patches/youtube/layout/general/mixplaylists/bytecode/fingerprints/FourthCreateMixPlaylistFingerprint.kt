package app.revanced.patches.youtube.layout.general.mixplaylists.bytecode.fingerprints

import app.revanced.patcher.fingerprint.method.impl.MethodFingerprint
import app.revanced.patches.youtube.misc.resourceid.patch.SharedResourcdIdPatch
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.WideLiteralInstruction

object FourthCreateMixPlaylistFingerprint : MethodFingerprint(
    opcodes = listOf(
        Opcode.CONST,
        Opcode.CONST_4,
        Opcode.INVOKE_VIRTUAL,
        Opcode.MOVE_RESULT_OBJECT,
        Opcode.MOVE_OBJECT,
        Opcode.CHECK_CAST
    ),
    customFingerprint = { methodDef, _ ->
        methodDef.implementation?.instructions?.any {
            it.opcode.ordinal == Opcode.CONST.ordinal &&
            (it as? WideLiteralInstruction)?.wideLiteral == SharedResourcdIdPatch.abclistmenuitemLabelId
        } == true
    }
)
