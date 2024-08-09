import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("kotlinx-serialization")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace= "com.system.traffic"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.system.traffic"
        minSdk = 28
        targetSdk = 34
        versionCode = 23
        versionName = "1.4.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    composeCompiler {
        enableStrongSkippingMode = true
        includeSourceInformation = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // retrofit
    implementation(libs.retrofit)

    // kotlinx-serialization-converter
    implementation(libs.retrofit2.kotlinx.serialization.converter)

    implementation(libs.kotlinx.serialization.json)

    //Okhttp3
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // DataStore Preferences
    implementation(libs.androidx.datastore.preferences)

    // Icon Extended
    implementation(libs.androidx.material.icons.extended)

    // LiveData
    implementation("androidx.compose.runtime:runtime-livedata:1.6.7")

    implementation("com.google.android.gms:play-services-ads:23.1.0")

    // Sandwich
    implementation(libs.sandwich.retrofit)

    // Custom Progress Bar
    implementation(libs.msz.progress.indicator)
}