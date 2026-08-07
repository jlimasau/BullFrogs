plugins {
    id("com.android.application")
    id("com.google.gms.google-services") version "4.4.4"
    id("org.jetbrains.kotlin.android")

}

android {
    namespace = "com.marimon.bullfrogs"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.marimon.bullfrogs"
        minSdk = 30
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    useLibrary("wear-sdk")






    buildFeatures {

        viewBinding = true
    }





}

dependencies {
    implementation(libs.play.services.wearable)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.compose.material)
    implementation(libs.compose.foundation)
    implementation(libs.wear.tooling.preview)
    implementation(libs.activity.compose)
    implementation(libs.core.splashscreen)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.wear)

    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
    // Import the Firebase BoM to manage your library versions


    // Add the dependency for the Realtime Database library
    // When using the BoM, you don't specify a version for the library
    implementation("com.google.firebase:firebase-database:22.0.1")

    // Add the Kotlin extensions (ktx) library for database functionality.
    // Note: The KTX library is still used for specific extension functions,
    // but its version is managed by the BoM.

}