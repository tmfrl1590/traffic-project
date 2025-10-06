import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.daggerHilt)
    alias(libs.plugins.compose.compiler)
    id("com.google.android.gms.oss-licenses-plugin")
    id("com.google.gms.google-services")
}

val properties = Properties()
properties.load(project.rootProject.file("local.properties").inputStream())

android {
    namespace= "com.system.traffic"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.system.traffic"
        minSdk = 28
        targetSdk = 35
        versionCode = 36
        versionName = "2.0.6"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("release") {
            storeFile = file(properties.getProperty("RELEASE_STORE_FILE"))
            storePassword = properties.getProperty("RELEASE_STORE_PASSWORD")
            keyAlias = properties.getProperty("RELEASE_KEY_ALIAS")
            keyPassword = properties.getProperty("RELEASE_STORE_PASSWORD")
        }
    }
    buildTypes {
        debug {
            // 애드몹 앱 id
            manifestPlaceholders["ADMOB_APP_ID"] = properties.getProperty("DEBUG_ADMOB_APP_ID")
            // 광고단위 id
            buildConfigField("String", "AD_UNIT_ID", "\"${properties.getProperty("DEBUG_AD_UNIT_ID")}\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
            manifestPlaceholders["ADMOB_APP_ID"] = properties.getProperty("RELEASE_ADMOB_APP_ID")
            buildConfigField("String", "AD_UNIT_ID", "\"${properties.getProperty("RELEASE_AD_UNIT_ID")}\"")

            signingConfig = signingConfigs.getByName("release")
        }
    }
    composeCompiler {
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
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":remote"))
    implementation(project(":local"))
    implementation(project(":feature:main"))
    implementation(project(":feature:home"))
    implementation(project(":feature:station"))
    implementation(project(":feature:line"))
    implementation(project(":feature:map"))
    implementation(project(":feature:setting"))

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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Admob
    implementation(libs.play.services.ads)
}
