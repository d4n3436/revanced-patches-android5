package app.revanced.patches.youtube.misc.protobufspoof.patch

import app.revanced.patcher.annotation.Name
import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
import app.revanced.patcher.extensions.InstructionExtensions.addInstructionsWithLabels
import app.revanced.patcher.extensions.InstructionExtensions.getInstruction
import app.revanced.patcher.extensions.InstructionExtensions.removeInstruction
import app.revanced.patcher.extensions.InstructionExtensions.replaceInstruction
import app.revanced.patcher.fingerprint.method.impl.MethodFingerprint.Companion.resolve
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.annotations.DependsOn
import app.revanced.patcher.util.smali.ExternalLabel
import app.revanced.patches.youtube.misc.playertype.patch.PlayerTypeHookPatch
import app.revanced.patches.youtube.misc.protobufpoof.fingerprints.PlayerParameterBuilderFingerprint
import app.revanced.patches.youtube.misc.protobufspoof.fingerprints.ParamsMapPutFingerprint
import app.revanced.patches.youtube.misc.protobufspoof.fingerprints.PlayerResponseModelImplGeneralFingerprint
import app.revanced.patches.youtube.misc.protobufspoof.fingerprints.PlayerResponseModelImplLiveStreamFingerprint
import app.revanced.patches.youtube.misc.protobufspoof.fingerprints.PlayerResponseModelImplRecommendedLevel
import app.revanced.patches.youtube.misc.protobufspoof.fingerprints.PlayerResponseModelMethodFingerprint
import app.revanced.patches.youtube.misc.protobufspoof.fingerprints.StatsQueryParameterFingerprint
import app.revanced.patches.youtube.misc.protobufspoof.fingerprints.StoryboardRendererSpecFingerprint
import app.revanced.patches.youtube.misc.protobufspoof.fingerprints.StoryboardRendererSpecRecommendedLevelFingerprint
import app.revanced.patches.youtube.misc.protobufspoof.fingerprints.StoryboardThumbnailFingerprint
import app.revanced.patches.youtube.misc.protobufspoof.fingerprints.StoryboardThumbnailParentFingerprint
import app.revanced.shared.annotation.YouTubeCompatibility
import app.revanced.shared.extensions.exception
import app.revanced.shared.util.integrations.Constants.MISC_PATH
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.FiveRegisterInstruction
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction
import com.android.tools.smali.dexlib2.iface.instruction.TwoRegisterInstruction

@Name("spoof-player-parameters-bytecode-patch")
@DependsOn([PlayerTypeHookPatch::class])
@YouTubeCompatibility
class SpoofPlayerParameterBytecodePatch : BytecodePatch(
    listOf(
        PlayerParameterBuilderFingerprint,
        PlayerResponseModelImplGeneralFingerprint,
        PlayerResponseModelImplLiveStreamFingerprint,
        PlayerResponseModelImplRecommendedLevel,
        StoryboardRendererSpecFingerprint,
        StoryboardRendererSpecRecommendedLevelFingerprint,
        StoryboardThumbnailParentFingerprint,
        StatsQueryParameterFingerprint,
        ParamsMapPutFingerprint
    )
) {
    override fun execute(context: BytecodeContext) {

        var playerResponseClass = StoryboardThumbnailParentFingerprint.result?.classDef ?: throw StoryboardThumbnailParentFingerprint.exception
        var playerResponseModelMethod = PlayerResponseModelMethodFingerprint.also { it.resolve(context, playerResponseClass) }.result?.method
            ?: throw PlayerResponseModelMethodFingerprint.exception

        /**
         * Hook player parameter
         */
        PlayerParameterBuilderFingerprint.result?.let {
            it.mutableMethod.apply {
                val videoIdRegister = 1
                val playerParameterRegister = 3

                addInstructions(
                    0, """
                        invoke-static {p$videoIdRegister, p$playerParameterRegister}, $INTEGRATIONS_CLASS_DESCRIPTOR->spoofParameter(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
                        move-result-object p$playerParameterRegister
                        """
                )
            }
        } ?: throw PlayerParameterBuilderFingerprint.exception

        /**
         * Forces the SeekBar thumbnail preview container to be shown
         * This is used only if the storyboard spec fetch fails, or when viewing paid videos.
         */
        StoryboardThumbnailParentFingerprint.result?.classDef?.let { classDef ->
            StoryboardThumbnailFingerprint.also {
                it.resolve(
                    context,
                    classDef
                )
            }.result?.let {
                it.mutableMethod.apply {
                    val targetIndex = it.scanResult.patternScanResult!!.endIndex
                    val targetRegister =
                        getInstruction<OneRegisterInstruction>(targetIndex).registerA

                    // Since this is end of the method must replace one line then add the rest.
                    addInstructions(
                        targetIndex + 1,
                        """
                            invoke-static {}, $INTEGRATIONS_CLASS_DESCRIPTOR->getSeekbarThumbnailOverrideValue()Z
                            move-result v$targetRegister
                            return v$targetRegister
                            """
                    )
                    removeInstruction(targetIndex)
                }
            } ?: throw StoryboardThumbnailFingerprint.exception
        } ?: throw StoryboardThumbnailParentFingerprint.exception

        /**
         * Hook StoryBoard Renderer URL
         */

        var offset = 0;

        arrayOf(
            PlayerResponseModelImplGeneralFingerprint to 1,
            PlayerResponseModelImplLiveStreamFingerprint to 1,
            StoryboardRendererSpecFingerprint to 0
        ).map { (fingerprint, increment) ->
            fingerprint.also {
                it.resolve(context, playerResponseModelMethod, playerResponseClass)
            }.result?.let {
                it.mutableMethod.apply {
                    val getStoryBoardIndex = it.scanResult.patternScanResult!!.endIndex + offset
                    offset += 3
                    val getStoryBoardRegister =
                        (getInstruction(getStoryBoardIndex) as OneRegisterInstruction?)?.registerA
                            ?: getInstruction<TwoRegisterInstruction>(getStoryBoardIndex).registerA

                    addInstructionsWithLabels(
                        getStoryBoardIndex + increment, """
                            if-nez v$getStoryBoardRegister, :ignore
                            invoke-static {}, $INTEGRATIONS_CLASS_DESCRIPTOR->getStoryboardRendererSpec()Ljava/lang/String;
                            move-result-object v$getStoryBoardRegister
                            """, ExternalLabel("ignore", getInstruction(getStoryBoardIndex + increment))
                    )
                } ?: throw fingerprint.exception
            }
        }

        /**
         * Hook recommended value and StoryBoard Renderer for live stream
         */

        StoryboardRendererSpecRecommendedLevelFingerprint.also {
            it.resolve(context, playerResponseModelMethod, playerResponseClass)
        }.result?.let {
            it.mutableMethod.apply {
                /*
                val moveOriginalRecommendedValueIndex = it.scanResult.patternScanResult!!.endIndex + 9
                val originalValueRegister =
                    getInstruction<TwoRegisterInstruction>(moveOriginalRecommendedValueIndex).registerA
                */

                val liveStreamStoryBoardUrlHookIndex =
                    implementation!!.instructions.indexOfFirst { instruction ->
                        instruction.opcode == Opcode.INVOKE_VIRTUAL_RANGE  //Changed point for 16.40.36
                    } + 2 // inside the if scope
                offset += 2;

                // string register
                val liveStreamStoryBoardUrlRegister =
                    getInstruction<OneRegisterInstruction>(liveStreamStoryBoardUrlHookIndex - 6).registerA
                /*
                addInstructions(
                    moveOriginalRecommendedValueIndex + 1, """
                        invoke-static { v$originalValueRegister }, $INTEGRATIONS_CLASS_DESCRIPTOR->getRecommendedLevel(I)I
                        move-result v$originalValueRegister
                        """
                )
                */

                addInstructions(
                    liveStreamStoryBoardUrlHookIndex, """
                        invoke-static { v$liveStreamStoryBoardUrlRegister }, $INTEGRATIONS_CLASS_DESCRIPTOR->getStoryboardRendererSpec(Ljava/lang/String;)Ljava/lang/String;
                        move-result-object v$liveStreamStoryBoardUrlRegister
                        """
                )
            }
        } ?: throw StoryboardRendererSpecRecommendedLevelFingerprint.exception


        PlayerResponseModelImplRecommendedLevel.also {
            it.resolve(context, playerResponseModelMethod, playerResponseClass)
        }.result?.let {
            it.mutableMethod.apply {
                val moveOriginalRecommendedValueIndex = it.scanResult.patternScanResult!!.endIndex + offset
                val originalValueRegister =
                    getInstruction<TwoRegisterInstruction>(moveOriginalRecommendedValueIndex).registerA

                addInstructions(
                    moveOriginalRecommendedValueIndex + 1, """
                        invoke-static { v$originalValueRegister }, $INTEGRATIONS_CLASS_DESCRIPTOR->getRecommendedLevel(I)I
                        move-result v$originalValueRegister
                        """
                )
            }

        } ?: throw PlayerResponseModelImplRecommendedLevel.exception

        // Fix stats not being tracked.
        // Due to signature spoofing "adformat" is present in query parameters made for /stats requests,
        // even though, for regular videos, it should not be.
        // This breaks stats tracking.
        // Replace the ad parameter with the video parameter in the query parameters.
        StatsQueryParameterFingerprint.result?.let {
            val putMethod = ParamsMapPutFingerprint.result?.method?.toString()
                ?: throw ParamsMapPutFingerprint.exception

            it.mutableMethod.apply {
                val adParamIndex = it.scanResult.stringsScanResult!!.matches.first().index
                val videoParamIndex = adParamIndex + 3

                // Replace the ad parameter with the video parameter.
                replaceInstruction(adParamIndex, getInstruction(videoParamIndex))

                // Call paramsMap.put instead of paramsMap.putIfNotExist
                // because the key is already present in the map.
                val putAdParamIndex = adParamIndex + 1
                val putIfKeyNotExistsInstruction = getInstruction<FiveRegisterInstruction>(putAdParamIndex)
                replaceInstruction(
                    putAdParamIndex,
                    "invoke-virtual { " +
                            "v${putIfKeyNotExistsInstruction.registerC}, " +
                            "v${putIfKeyNotExistsInstruction.registerD}, " +
                            "v${putIfKeyNotExistsInstruction.registerE} }, " +
                            putMethod,
                )
            }
        } ?: throw StatsQueryParameterFingerprint.exception

    }

    private companion object {
        const val INTEGRATIONS_CLASS_DESCRIPTOR =
            "$MISC_PATH/SpoofPlayerParameterPatch;"
    }
}