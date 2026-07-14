plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.daggerHilt)
    alias(libs.plugins.ksp)
    id("kotlinx-serialization")
}

android {
    namespace = "com.system.traffic.local"
    compileSdk = 35

    defaultConfig {
        minSdk = 26
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
    implementation(projects.data)
    implementation(projects.domain)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // DataStore Preferences
    implementation(libs.androidx.datastore.preferences)

    // room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // Kotlin Serialization
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.serialization.json)
}