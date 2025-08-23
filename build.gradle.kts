// Top-level build file where you can add configuration options common to all sub-projects/modules.
/*
    Using ksp instead of kapt.
    Kapt (the Kotlin Annotation Processing Tool): ...
    KSP (Kotlin Symbol Processing): is a Kotlin-first alternative to kapt.
    KSP analyzes Kotlin code directly, which is up to 2x faster.
    It also has a better understanding of Kotlin language constructs.
    Read more at: https://developer.android.com/build/migrate-to-ksp#kts
*/
/*
    In each module that uses Compose, apply the compose plugin.
    Important to add the compiler. Read more here:
    https://developer.android.com/develop/ui/compose/compiler
*/
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false

    id("com.google.devtools.ksp") version "2.2.10-2.0.2" apply false

    alias(libs.plugins.compose.compiler) apply false
}