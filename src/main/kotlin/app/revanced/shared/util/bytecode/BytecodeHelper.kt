package app.revanced.shared.util.bytecode

import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.addInstruction
import app.revanced.patcher.extensions.InstructionExtensions.replaceInstruction
import app.revanced.shared.util.integrations.Constants.UTILS_PATH

internal object BytecodeHelper {

    fun injectInit(
        context: BytecodeContext,
        descriptor: String,
        methods: String
    ) {
        context.classes.forEach { classDef ->
            classDef.methods.forEach { method ->
                if (classDef.type.endsWith("WatchWhileActivity;") && method.name == "onCreate") {
                    val hookMethod =
                        context.proxy(classDef).mutableClass.methods.first { it.name == "onCreate" }

                    hookMethod.addInstruction(
                        2,
                        "invoke-static/range {p0 .. p0}, $UTILS_PATH/$descriptor;->$methods(Landroid/content/Context;)V"
                    )
                }
            }
        }
    }

    fun patchStatus(
        context: BytecodeContext,
        name: String
    ) {
        context.classes.forEach { classDef ->
            classDef.methods.forEach { method ->
                if (classDef.type.endsWith("PatchStatus;") && method.name == name) {
                    val patchStatusMethod =
                        context.proxy(classDef).mutableClass.methods.first { it.name == name }

                    patchStatusMethod.replaceInstruction(
                        0,
                        "const/4 v0, 0x1"
                    )
                }
            }
        }
    }
}