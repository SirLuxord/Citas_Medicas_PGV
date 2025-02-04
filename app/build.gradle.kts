plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services") version "4.4.2"
    id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false
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
}



dependencies {
    implementation(libs.androidx.fragment.ktx) {
        exclude(group = "com.intellij", module = "annotations")
    }
    implementation(libs.lifecycle.viewmodel.ktx) {
        exclude(group = "com.intellij", module = "annotations")
    }
    implementation(libs.room.runtime) {
        exclude(group = "com.intellij", module = "annotations")
    }
    implementation(libs.room.ktx) {
        exclude(group = "com.intellij", module = "annotations")
    }
    implementation(libs.androidx.room.compiler.v250) {
        exclude(group = "com.intellij", module = "annotations")
    }
    implementation(platform(libs.firebase.bom)) {
        exclude(group = "com.intellij", module = "annotations")
    }
    implementation(libs.firebase.auth.ktx) {
        exclude(group = "com.intellij", module = "annotations")
    }
    implementation(libs.androidx.core.ktx) {
        exclude(group = "com.intellij", module = "annotations")
    }
    implementation(libs.androidx.appcompat) {
        exclude(group = "com.intellij", module = "annotations")
    }
    implementation(libs.material) {
        exclude(group = "com.intellij", module = "annotations")
    }
    implementation(libs.androidx.activity) {
        exclude(group = "com.intellij", module = "annotations")
    }
    implementation(libs.androidx.constraintlayout) {
        exclude(group = "com.intellij", module = "annotations")
    }
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit) {
        exclude(group = "com.intellij", module = "annotations")
    }
    androidTestImplementation(libs.androidx.junit) {
        exclude(group = "com.intellij", module = "annotations")
    }
    androidTestImplementation(libs.androidx.espresso.core) {
        exclude(group = "com.intellij", module = "annotations")
    }
}

