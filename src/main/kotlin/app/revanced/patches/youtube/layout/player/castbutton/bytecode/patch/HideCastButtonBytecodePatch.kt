package app.revanced.patches.youtube.layout.player.castbutton.bytecode.patch

import app.revanced.patcher.annotation.Name
import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.shared.annotation.YouTubeCompatibility
import app.revanced.shared.util.integrations.Constants.PLAYER_LAYOUT

@Name("hide-cast-button-bytecode-patch")
@YouTubeCompatibility
class HideCastButtonBytecodePatch : BytecodePatch() {
    override fun execute(context: BytecodeContext) {
        context.classes.forEach { classDef ->
            classDef.methods.forEach { method ->
                if (classDef.type.endsWith("MediaRouteButton;") && method.name == "setVisibility") {
                    val setVisibilityMethod =
                        context.proxy(classDef).mutableClass.methods.first { it.name == "setVisibility" }

                    setVisibilityMethod.addInstructions(
                        0, """
                            invoke-static {p1}, $PLAYER_LAYOUT->hideCastButton(I)I
                            move-result p1
                        """
                    )
                }
            }
        }
    }
}
