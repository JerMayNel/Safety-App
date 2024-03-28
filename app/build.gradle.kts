plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    kotlin("kapt")
    id("com.google.gms.google-services")
    id("org.jetbrains.kotlin.plugin.parcelize")
}

android {
    namespace = "com.example.safetyapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.safetyapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures{
        viewBinding = true
    }
    androidResources{
        experimentalProperties["android.enableR8"] = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        version = "1.7.21"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database.ktx)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.play.services.location)
    implementation(libs.androidx.room.common)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("pl.droidsonroids.gif:android-gif-drawable:1.2.23")
    implementation(platform("com.google.firebase:firebase-bom:32.8.0"))
    implementation ("com.google.android.gms:play-services-maps:18.2.0")

    val room_version = "2.6.1"

    implementation ("androidx.room:room-runtime:$room_version")
    annotationProcessor ("androidx.room:room-compiler:$room_version")

    // To use Kotlin annotation processing tool (kapt)
    kapt ("androidx.room:room-compiler:$room_version")

    // optional - RxJava2 support for Room
    implementation( "androidx.room:room-rxjava2:$room_version")

    // optional - RxJava3 support for Room
    implementation ("androidx.room:room-rxjava3:$room_version")

    // optional - Guava support for Room, including Optional and ListenableFuture
    implementation ("androidx.room:room-guava:$room_version")

    // optional - Test helpers
    testImplementation ("androidx.room:room-testing:$room_version")

    // optional - Paging 3 Integration
    implementation ("androidx.room:room-paging:$room_version")

    implementation ("androidx.room:room-ktx:$room_version")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$room_version")
}