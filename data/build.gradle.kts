plugins {
    id("traffic.android.library")
    id("traffic.hilt")
    id("kotlinx-serialization")
}

android {
    namespace = "com.system.traffic.data"
}

dependencies {
    implementation(projects.core)
    implementation(projects.domain)

    // Kotlin Serialization
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.serialization.json)

    // Coroutines (Flow 직접 사용)
    implementation(libs.coroutines.core)
}
