import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * 앱 모듈 공통 설정
 * (compileSdk, minSdk, Java 17, Compose)
 * applicationId, versionCode/Name, signing, buildTypes 등 앱 고유 설정은 app 모듈에 유지
 */
class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.application")
            pluginManager.apply("org.jetbrains.kotlin.android")
            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

            extensions.configure<ApplicationExtension> {
                compileSdk = ProjectConfig.COMPILE_SDK

                defaultConfig {
                    minSdk = ProjectConfig.MIN_SDK
                }

                compileOptions {
                    sourceCompatibility = ProjectConfig.JAVA_VERSION
                    targetCompatibility = ProjectConfig.JAVA_VERSION
                }

                buildFeatures {
                    compose = true
                }
            }

            tasks.withType<KotlinCompile>().configureEach {
                compilerOptions {
                    jvmTarget.set(ProjectConfig.JVM_TARGET)
                }
            }
        }
    }
}
