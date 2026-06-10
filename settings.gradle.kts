pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "traffic"
include(":app")
include(":common")
include(":navigation")
include(":data")
include(":domain")
include(":remote")
include(":core")
include(":local")
include(":presentation")
