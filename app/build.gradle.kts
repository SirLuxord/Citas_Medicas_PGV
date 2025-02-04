plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services") version "4.4.2"
    id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false
    id("kotlin-kapt")
}

android {
    namespace = "com.example.citas_medicas"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.citas_medicas"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    viewBinding {
        enable = true
    }
    buildFeatures {
        viewBinding = true
    }
}



dependencies {
    kapt(libs.androidx.room.compiler.v250)

    implementation(libs.androidx.fragment.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth.ktx)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

