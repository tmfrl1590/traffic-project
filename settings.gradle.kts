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
include(":common")
include(":navigation")
include(":di")
include(":data")
include(":domain")
include(":feature:main")
include(":feature:splash")
include(":feature:home")
include(":feature:station")
include(":feature:line")
include(":feature:map")
include(":feature:setting")
include(":feature:bus_arrive")
include(":remote")
include(":core")
