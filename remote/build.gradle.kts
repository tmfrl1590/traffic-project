import java.util.Properties

plugins {
    id("traffic.android.library")
    id("traffic.hilt")
    id("kotlinx-serialization")
}

val properties = Properties()
properties.load(project.rootProject.file("local.properties").inputStream())

android {
    namespace = "com.system.traffic.remote"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "BASE_URL", "\"${properties.getProperty("BASE_URL")}\"")
        buildConfigField("String", "SERVICE_KEY", "\"${properties.getProperty("SERVICE_KEY")}\"")
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.core)
    implementation(projects.data)

    // retrofit
    implementation(libs.retrofit)

    // kotlinx-serialization-converter
    implementation(libs.retrofit2.kotlinx.serialization.converter)

    //Okhttp3
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // Kotlin Serialization
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.serialization.json)
}
