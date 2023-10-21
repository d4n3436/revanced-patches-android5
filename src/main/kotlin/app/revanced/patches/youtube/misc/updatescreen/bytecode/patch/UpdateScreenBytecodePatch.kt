package app.revanced.patches.youtube.misc.updatescreen.bytecode.patch

import app.revanced.patcher.annotation.Name
import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.replaceInstructions
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patches.youtube.misc.updatescreen.bytecode.fingerprints.UpdateScreenFingerprint
import app.revanced.shared.annotation.YouTubeCompatibility
import app.revanced.shared.extensions.exception

@Name("disable-update-screen-bytecode-patch")
@YouTubeCompatibility
class UpdateScreenBytecodePatch : BytecodePatch(
    listOf(
        UpdateScreenFingerprint
    )
) {
    override fun execute(context: BytecodeContext) {

        UpdateScreenFingerprint.result?.mutableMethod?.replaceInstructions(
            0,
            """
                const/4 v0, 0x0
                return-object v0
                """
        ) ?: throw UpdateScreenFingerprint.exception
    }
}
