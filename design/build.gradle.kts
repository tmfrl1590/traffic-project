plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.traffic.design"
    compileSdk = 35

    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
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
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.material)


    implementation(libs.androidx.material.icons.extended)

    implementation(platform("com.google.firebase:firebase-bom:34.0.0"))

    // Admob
    implementation(libs.play.services.ads)
}