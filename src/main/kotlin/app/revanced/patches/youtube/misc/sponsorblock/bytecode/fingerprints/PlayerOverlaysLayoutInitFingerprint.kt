package app.revanced.patches.youtube.misc.sponsorblock.bytecode.fingerprints

import app.revanced.patcher.fingerprint.method.impl.MethodFingerprint

object PlayerOverlaysLayoutInitFingerprint : MethodFingerprint(

    customFingerprint = { methodDef, _ -> methodDef.returnType.endsWith("YouTubePlayerOverlaysLayout;") }
)