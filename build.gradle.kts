// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    val room_version = "2.6.1"
    id("com.android.application") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.devtools.ksp") version "1.8.21-1.0.11" apply false
    id("androidx.room") version room_version apply false
 //   id("com.google.devtools.ksp") version "1.8.21-1.0.11"
}