import java.util.Properties

plugins {
    id("traffic.android.application")
    id("traffic.hilt")
    alias(libs.plugins.oss.licenses)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
}

val properties = Properties()
properties.load(project.rootProject.file("local.properties").inputStream())

android {
    namespace = "com.system.traffic"

    defaultConfig {
        applicationId = "com.system.traffic"
        targetSdk = ProjectConfig.TARGET_SDK
        versionCode = ProjectConfig.VERSION_CODE
        versionName = ProjectConfig.VERSION_NAME

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
            buildConfigField(type = "String", name = "AD_UNIT_ID", value = "\"${properties.getProperty("DEBUG_AD_UNIT_ID")}\"")
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
            manifestPlaceholders["ADMOB_APP_ID"] = properties.getProperty("RELEASE_ADMOB_APP_ID")
            buildConfigField(type = "String", name = "AD_UNIT_ID", value = "\"${properties.getProperty("RELEASE_AD_UNIT_ID")}\"")

            signingConfig = signingConfigs.getByName("release")
        }
    }

    buildFeatures {
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(projects.core)
    implementation(projects.data)
    implementation(projects.domain)
    implementation(projects.presentation)
    implementation(projects.remote)
    implementation(projects.local)
    implementation(projects.design)
    implementation(projects.navigation)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.activity)

    // OSS Licenses (manifest에서 OssLicensesMenuActivity/OssLicensesActivity 참조)
    implementation(libs.oss.licenses)

    // Admob
    implementation(libs.play.services.ads)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.crashlytics)

    // splash screen
    implementation(libs.androidx.core.splashscreen)


}
