// ROOT BUILD.GRADLE.KTS (KSP VERSION)
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.ksp) apply false // REPLACED: kapt with ksp
    alias(libs.plugins.hilt) apply false // ADDED: Hilt
}
