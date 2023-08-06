package app.revanced.patches.youtube.misc.osversion

import app.revanced.patcher.annotation.Name
import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.PatchResult
import app.revanced.patcher.patch.PatchResultSuccess
import app.revanced.shared.util.bytecode.BytecodeHelper

@Name("os-version-check-bytecode-patch")
class CheckOsVersionBytecodePatch : BytecodePatch() {
    override fun execute(context: BytecodeContext): PatchResult {
        BytecodeHelper.injectInit(context, "CheckOsVersionPatch", "checkOsVersion")
        return PatchResultSuccess()
    }
}