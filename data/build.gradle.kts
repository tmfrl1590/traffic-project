plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.daggerHilt)
    alias(libs.plugins.ksp)
    id("kotlinx-serialization")
}

android {
    namespace = "com.traffic.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 28
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(projects.core)
    implementation(projects.domain)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    // hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // Kotlin Serialization
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.serialization.json)

    // DataStore Preferences
    implementation(libs.androidx.datastore.preferences)

    // room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
}