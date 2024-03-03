package app.revanced.patches.youtube.layout.fullscreen.landscapemode.fingerprints

import app.revanced.patcher.fingerprint.method.impl.MethodFingerprint

object OrientationParentFingerprint : MethodFingerprint (
    returnType = "V",
    customFingerprint = {methodDef, _ -> methodDef.name == "<init>" && methodDef.definingClass == "Lovi;"}
)