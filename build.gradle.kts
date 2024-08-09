plugins {
    val kotlinVersion = "2.0.0"
    id("com.android.application") version "8.3.1" apply false
    id("org.jetbrains.kotlin.android") version kotlinVersion apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false

    id("org.jetbrains.kotlin.plugin.compose") version kotlinVersion apply false

    // Kotlin Serialization
    kotlin("plugin.serialization") version kotlinVersion apply false
    kotlin("jvm") version kotlinVersion apply false
    id("com.android.library") version "8.3.1" apply false
}