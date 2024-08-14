plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.saidur.supperquiz"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.saidur.supperquiz"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
       viewBinding = true
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    // ViewModel
    implementation (libs.lifecycle.viewmodel)
    // LiveData
    implementation (libs.lifecycle.livedata)
    //retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    //glide
    implementation (libs.github.glide)
    annotationProcessor (libs.compiler)

    //dagger2
   // implementation (libs.dagger)
    //annotationProcessor (libs.dagger.compiler)
    implementation ("com.google.dagger:dagger:2.52")
    annotationProcessor ("com.google.dagger:dagger-compiler:2.52")

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}