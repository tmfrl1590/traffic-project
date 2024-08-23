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
        maven("https://repository.map.naver.com/archive/maven") // naver map
    }
}
rootProject.name = "traffic"
include(":app")
include(":presentation")
include(":domain")
include(":data")
include(":common")
include(":navigation")
include(":di")
