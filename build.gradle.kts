buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.oss.licenses.plugin)
    }
}

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.daggerHilt) apply false

    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.ksp) apply false

    id("com.google.gms.google-services") version "4.4.2" apply false

}