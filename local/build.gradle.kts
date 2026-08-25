plugins {
    id("traffic.android.library")
    id("traffic.hilt")
    id("kotlinx-serialization")
}

android {
    namespace = "com.system.traffic.local"
}

dependencies {
    implementation(projects.core)
    implementation(projects.data)
    implementation(projects.domain)

    // DataStore Preferences
    implementation(libs.androidx.datastore)

    // room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // Kotlin Serialization
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.serialization.json)
}
