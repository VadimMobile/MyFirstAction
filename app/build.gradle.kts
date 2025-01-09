plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "ru.netology.nmedia"
    compileSdk = 35

    defaultConfig {
        applicationId = "ru.netology.nmedia"
        minSdk = 23
        targetSdk = 35
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
buildFeatures{
    viewBinding = true
}
}

dependencies {

    implementation(libs.androidx.activity)
    val coreVersion = "1.15.0"
    val appcompatVersion = "1.7.0"
    val mdcVersion = "1.12.0"
    val constraintlayoutVersion = "2.2.0"
    val recyclerviewVersion = "1.3.2"
    val junitVersion = "4.13.2"
    val extJunitVersion = "1.2.1"
    val espressoCoreVersion = "3.6.1"
    val activityVersion = "1.9.3"
    val lifecycleVersion = "2.8.7"
    val gsonVersion = "2.11.0"

    implementation ("androidx.core:core-ktx:$coreVersion")
    implementation ("androidx.appcompat:appcompat:$appcompatVersion")
    implementation ("com.google.android.material:material:$mdcVersion")
    implementation ("androidx.constraintlayout:constraintlayout:$constraintlayoutVersion")
    implementation ("androidx.recyclerview:recyclerview:$recyclerviewVersion")
    implementation ("androidx.activity:activity-ktx:$activityVersion")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    implementation ("com.google.code.gson:gson:$gsonVersion")

    testImplementation ("junit:junit:$junitVersion")
    androidTestImplementation ("androidx.test.ext:junit:$extJunitVersion")
    androidTestImplementation ("androidx.test.espresso:espresso-core:$espressoCoreVersion")
}