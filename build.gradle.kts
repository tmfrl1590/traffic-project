plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.daggerHilt) apply false

    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.ksp) apply false

    id("com.google.gms.google-services") version "4.4.2" apply false
    alias(libs.plugins.oss.licenses) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.ktlint) apply false
}

// 모든 모듈에 ktlint 적용 (룰은 루트 .editorconfig에서 관리)
subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        android.set(true)
        filter {
            exclude { it.file.path.contains("/build/") }
        }
    }
}