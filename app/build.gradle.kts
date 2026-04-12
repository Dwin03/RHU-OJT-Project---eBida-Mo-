plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.tsismisapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.tsismisapp"
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
}

dependencies {

    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation(libs.androidx.monitor)
    implementation(libs.androidx.junit.ktx)
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
//
//    implementation("io.coil-kt:coil:2.2.2")
//    implementation("io.coil-kt:coil-gif:2.2.2")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:okhttp:4.9.3")

    implementation ("com.android.volley:volley:1.2.1")

    implementation ("androidx.activity:activity:1.10.1")

    implementation ("androidx.recyclerview:recyclerview:1.2.1")

    implementation("androidx.appcompat:appcompat:1.6.2")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.volley)

//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.appcompat)
//    implementation(libs.material)
//    implementation(libs.androidx.activity)
//    implementation(libs.androidx.constraintlayout)
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
}