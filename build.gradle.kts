plugins {
    val kotlinVersion = "1.9.24"
    id("com.android.application") version "8.3.1" apply false
    id("org.jetbrains.kotlin.android") version kotlinVersion apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false

    // Kotlin Serialization
    kotlin("plugin.serialization") version kotlinVersion apply false
    kotlin("jvm") version kotlinVersion apply false
}