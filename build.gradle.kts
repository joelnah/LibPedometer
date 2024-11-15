plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()
    namespace = "na.family.prayer.pedometer"
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.versions.javaVer.get())
        targetCompatibility = JavaVersion.toVersion(libs.versions.javaVer.get())
    }

    kotlinOptions {
        jvmTarget = libs.versions.javaVer.get()
    }
}

dependencies {
    implementation (libs.androidx.core.ktx)
    implementation (libs.androidx.appcompat)
    implementation (libs.material)
    testImplementation (libs.junit)
    androidTestImplementation (libs.androidx.junit)
    androidTestImplementation (libs.androidx.espresso.core)
    implementation (libs.androidx.ui)

    implementation (libs.permissions)
    debugImplementation(libs.nahutils)
    implementation (libs.gson)
}