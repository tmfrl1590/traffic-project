# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# ====================================================================
# 구글 플레이 콘솔 크래시 디버깅 정보 보존 (원본 소스파일명 & 줄 번호)
# ====================================================================
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile


# ====================================================================
# 리플렉션 및 어노테이션 정보 보존 (Retrofit, Room, Serialization 필수)
# ====================================================================
# Generic 타입 정보(Signature), 내부 클래스 구조(InnerClasses, EnclosingMethod), 어노테이션(*Annotation*) 보존
-keepattributes Signature,InnerClasses,EnclosingMethod,*Annotation*


# 안드로이드 핵심 컴포넌트 팩토리 클래스 리플렉션 보호
-keep class androidx.core.app.CoreComponentFactory { *; }


# ====================================================================
# Kotlinx Serialization
# ====================================================================
-keepclassmembers class * {
    @kotlinx.serialization.SerialName <fields>;
    @kotlinx.serialization.Serializer *** serializer(...);
    public synthetic static *** write$Self(...);
}
-keep,allowobfuscation class * implements kotlinx.serialization.KSerializer
-keepclassmembers class * implements kotlinx.serialization.KSerializer {
    *** INSTANCE;
}


# ====================================================================
# Retrofit 2 & OkHttp 3
# ====================================================================
-keepclassmembernames interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn okhttp3.**
-dontwarn retrofit2.**


# ====================================================================
# Naver Map SDK
# ====================================================================
-keep class com.naver.maps.map.** { *; }
-keep interface com.naver.maps.map.** { *; }
-keep class com.naver.maps.geometry.** { *; }
-dontwarn com.naver.maps.map.**


# ====================================================================
# Room Database
# ====================================================================
-keep class * extends androidx.room.RoomDatabase
-dontwarn androidx.room.paging.**
-keepclassmembers class * {
    @androidx.room.Entity *;
    @androidx.room.PrimaryKey *;
    @androidx.room.ColumnInfo *;
}


# ====================================================================
# Hilt & Dagger
# ====================================================================
# Hilt 생성 코드 및 EntryPoint 보존 (클래스 전체 keep보다 효율적)
-keep class * extends dagger.hilt.internal.UnsafeCasts
-keep class * implements dagger.hilt.internal.GeneratedEntryPoint
-keepclassmembers,allowobfuscation class * {
    @dagger.hilt.InstallIn *;
    @dagger.hilt.EntryPoint *;
}
-keep class **_HiltModules* { *; }
-keep class **_Factory { *; }
-keep class **_MembersInjector { *; }