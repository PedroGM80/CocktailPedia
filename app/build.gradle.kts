plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    kotlin("plugin.serialization") version "1.9.0" // Use the appropriate version

}

android {
    namespace = "dev.pgm.cocktailpedia"
    compileSdk = 34

    defaultConfig {
        applicationId = "dev.pgm.cocktailpedia"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
    composeCompiler {
        enableStrongSkippingMode = true
    }

    packaging {
        resources.excludes.add("META-INF/AL2.0")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.androidx.material3.android)
    debugImplementation(libs.compose.ui.tooling)
//Google fonts
    implementation ("androidx.compose.ui:ui-text-google-fonts:1.6.0")
    //Icons
    implementation("androidx.compose.material:material:1.7.4")
    implementation ("androidx.compose.material:material-icons-extended:1.6.2")

    implementation (libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)

    implementation(libs.coil.compose)
    implementation(libs.kotlinx.coroutines.android)
    //Serializacion
    implementation(libs.kotlinx.serialization.json)// Or your preferred format

    implementation (libs.retrofit.v290)
    implementation (libs.okhttp)
}