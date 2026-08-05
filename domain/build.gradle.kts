plugins {
    id("traffic.android.library")
    id("traffic.hilt")
    id("kotlinx-serialization")
    id("kotlin-parcelize")
}

android {
    namespace = "com.traffic.domain"
}

dependencies {
    implementation(projects.core)

    // Kotlin Serialization
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.serialization.json)
}
