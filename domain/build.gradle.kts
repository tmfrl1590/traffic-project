plugins {
    id("traffic.android.library")
    id("traffic.hilt")
    id("kotlinx-serialization")
    id("kotlin-parcelize")
}

android {
    namespace = "com.system.traffic.domain"
}

dependencies {
    // domain의 공개 API(RemoteRepository 등)가 core 타입(Result, DataError)을 노출하므로 api로 선언
    api(projects.core)

    // Kotlin Serialization
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.serialization.json)

    // Coroutines (Flow, Dispatchers.IO 직접 사용)
    implementation(libs.coroutines.core)
}
