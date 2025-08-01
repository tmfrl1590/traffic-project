plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    id("kotlinx-serialization")
}
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
    }
}

dependencies {
    // Kotlin Serialization
    implementation(libs.kotlinx.serialization.json)

    // Okhttp3
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // retrofit
    implementation(libs.retrofit)

    // Coroutines
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)
}