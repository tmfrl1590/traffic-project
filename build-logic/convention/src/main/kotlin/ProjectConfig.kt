import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

/**
 * 프로젝트 공통 버전 상수
 * SDK 버전이나 앱 버전을 올릴 때 이 파일만 수정하면 됨
 */
object ProjectConfig {
    const val COMPILE_SDK = 37
    const val TARGET_SDK = 36
    const val MIN_SDK = 28

    const val VERSION_CODE = 60
    const val VERSION_NAME = "2.3.0"

    val JAVA_VERSION = JavaVersion.VERSION_17
    val JVM_TARGET = JvmTarget.JVM_17
}
