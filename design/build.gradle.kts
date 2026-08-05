plugins {
    id("traffic.android.library.compose")
}

android {
    namespace = "com.traffic.design"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.activity)

    // Admob
    implementation(libs.play.services.ads)
}
