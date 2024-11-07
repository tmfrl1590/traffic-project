plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.daggerHilt)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.traffic.main"
    compileSdk = 34

    defaultConfig {
        minSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    implementation(project(":navigation"))
    implementation(project(":domain"))
    implementation(project(":feature:splash"))
    implementation(project(":feature:home"))
    implementation(project(":feature:station"))
    implementation(project(":feature:line"))
    implementation(project(":feature:map"))
    implementation(project(":feature:setting"))
    implementation(project(":feature:bus_arrive"))

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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Coil
    implementation(libs.coil.compose)

    // Material Icons Extended
    implementation(libs.androidx.material.icons.extended)

    // retrofit
    implementation(libs.retrofit)

    // Sandwich
    implementation(libs.sandwich.retrofit)

    // Coroutines
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    // Gson
    implementation(libs.gson)

    // Custom Progress Indicator
    implementation(libs.msz.progress.indicator)

    debugImplementation(libs.ui.tooling)

    // 네이버 지도 SDK
    implementation(libs.map.sdk)
    implementation(libs.naver.map.compose)
    implementation(libs.play.services.location)
    implementation(libs.naver.map.location)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics.ktx)
    implementation(libs.firebase.messaging)

    // In-App Update
    implementation(libs.app.update.ktx)

    // OpenSource License
    implementation(libs.oss.licenses)
}