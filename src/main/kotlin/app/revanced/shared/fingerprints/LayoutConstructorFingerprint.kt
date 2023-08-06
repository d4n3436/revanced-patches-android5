package app.revanced.shared.fingerprints

import app.revanced.patcher.fingerprint.method.impl.MethodFingerprint

object LayoutConstructorFingerprint : MethodFingerprint(
    strings = listOf("1.0x"),
    customFingerprint = {
            methodDef, _ -> methodDef.definingClass.endsWith("YouTubeControlsOverlay;")
    }
)