package app.revanced.patches.youtube.misc.protobufspoof.patch

import app.revanced.patcher.data.ResourceContext
import app.revanced.patcher.patch.PatchResult
import app.revanced.patcher.patch.PatchResultSuccess
import app.revanced.patcher.patch.ResourcePatch
import app.revanced.shared.patches.mapping.ResourceMappingPatch

class SpoofPlayerParameterResourcePatch : ResourcePatch {
    override fun execute(context: ResourceContext): PatchResult {
        // used in ScrubbedPreviewLayoutFingerprint
        scrubbedPreviewThumbnailResourceId = ResourceMappingPatch.resourceMappings.single {
            it.type == "id" && it.name == "thumbnail"
        }.id

        return PatchResultSuccess()
    }

    companion object {
        var scrubbedPreviewThumbnailResourceId: Long = -1
    }
}