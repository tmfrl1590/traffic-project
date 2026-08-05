plugins {
    id("traffic.android.library.compose")
    id("traffic.hilt")
    id("kotlinx-serialization")
}

android {
    namespace = "com.system.traffic.navigation"
}

dependencies {
    implementation(projects.presentation)
    implementation(projects.design)

    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.material3)

    implementation(libs.kotlinx.serialization.json)

    // Material Icons Extended
    implementation(libs.androidx.material.icons.extended)

    // hilt
    implementation(libs.androidx.hilt.navigation.compose)

    // navigation3
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.jetbrains.lifecycle.viewmodel.nav3)
}
