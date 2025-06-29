# Ktor 관련 규칙
-keep class io.ktor.** { *; }
-keep class kotlinx.serialization.** { *; }
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt

# Serialization 관련
-keepclassmembers class **$$serializer {
    *** INSTANCE;
}
-keepclassmembers class ** {
    *** Companion;
}
-keepclassmembers enum * { *; }

# SLF4J 관련
-keep class org.slf4j.** { *; }
-dontwarn org.slf4j.**

# 동적 참조 경고 무시
-dontwarn java.awt.Window$CustomWindowDecoration
-dontwarn io.ktor.utils.io.jvm.javaio.PollersKt

# 중복 클래스 무시
-dontnote **
