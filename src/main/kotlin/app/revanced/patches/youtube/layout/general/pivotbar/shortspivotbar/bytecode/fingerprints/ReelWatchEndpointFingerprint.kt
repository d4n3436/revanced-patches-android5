package app.revanced.patches.youtube.layout.general.pivotbar.shortspivotbar.bytecode.fingerprints

import app.revanced.patcher.fingerprint.method.impl.MethodFingerprint

object ReelWatchEndpointFingerprint : MethodFingerprint(
    returnType = "V",
    customFingerprint = { methodDef, _ -> methodDef.name == "<init>" }
)