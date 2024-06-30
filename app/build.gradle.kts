plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.room")
    id("kotlin-kapt")
   // id("com.google.devtools.ksp")
    kotlin("plugin.serialization") version "1.6.0" // Use the Kotlin serialization plugin
}

android {
    room {
        schemaDirectory("$projectDir/schemas")
    }

    namespace = "com.sap.testretrofit"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.sap.testretrofit"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }


    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    sourceSets {
        getByName("main").java.srcDirs("build/generated/ksp/main/kotlin")
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {

    // Room  DB
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    //implementation("com.google.devtools.ksp:symbol-processing-api:2.0.0-RC2-1.0.20")

    // To use Kotlin Symbol Processing (KSP)
    //ksp("androidx.room:room-compiler:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.1")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation(platform("androidx.compose:compose-bom:2024.05.00"))
    implementation("androidx.compose.ui:ui:1.6.7")
    implementation("androidx.compose.runtime:runtime-livedata:1.6.7")
    implementation("androidx.compose.ui:ui-graphics:1.6.7")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.1")
    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation(platform("androidx.compose:compose-bom:2024.05.00"))
//    androidTestImplementation(platform("androidx.compose:compose-bom:2024.02.01"))
//    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    // Material 3
    implementation("androidx.compose.material3:material3")
 //   implementation("androidx.compose.material3:material3-window-size-class:1.3.0")
    // kotlinx serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // Retrofit for networking
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.3")
    //implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    // Koin for dependency injection
    implementation("io.insert-koin:koin-android:3.6.0-wasm-alpha2")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.14")
    implementation("io.insert-koin:koin-androidx-compose:3.6.0-wasm-alpha2")
    // Coil
    implementation("io.coil-kt:coil-compose:2.4.0")

    // voyager
    val voyagerVersion = "1.1.0-beta02"
    // For screen transitions
    implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion") // Or latest version

    // For tab-based navigation
    implementation("cafe.adriel.voyager:voyager-tab-navigator:$voyagerVersion") // Or latest version

    // For Android integration (if applicable)
    implementation("cafe.adriel.voyager:voyager-android:$voyagerVersion")

    // Koin integration
    implementation("cafe.adriel.voyager:voyager-koin:$voyagerVersion")


}
kapt {
    correctErrorTypes = true
}

/*repositories {
   mavenCentral()
}*/

/*ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}*/
