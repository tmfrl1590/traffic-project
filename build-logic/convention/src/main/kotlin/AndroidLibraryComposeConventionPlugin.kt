import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Compose를 사용하는 라이브러리 모듈 설정
 * (traffic.android.library + compose compiler + buildFeatures.compose)
 */
class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("traffic.android.library")
            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

            extensions.configure<LibraryExtension> {
                buildFeatures {
                    compose = true
                }
            }
        }
    }
}
