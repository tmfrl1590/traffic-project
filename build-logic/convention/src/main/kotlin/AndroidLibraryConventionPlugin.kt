import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * 모든 안드로이드 라이브러리 모듈의 공통 설정
 * (compileSdk, minSdk, Java 17, release 빌드 타입)
 */
class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")
            pluginManager.apply("org.jetbrains.kotlin.android")

            extensions.configure<LibraryExtension> {
                compileSdk = ProjectConfig.COMPILE_SDK

                defaultConfig {
                    minSdk = ProjectConfig.MIN_SDK
                }

                buildTypes {
                    getByName("release") {
                        isMinifyEnabled = false
                    }
                }

                compileOptions {
                    sourceCompatibility = ProjectConfig.JAVA_VERSION
                    targetCompatibility = ProjectConfig.JAVA_VERSION
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
