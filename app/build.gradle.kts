plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "dev.pgm.cocktailpedia"
    compileSdk = 35

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }

    kotlin {
        sourceSets.all {
            kotlin.srcDir("build/generated/ksp/$name/kotlin")
        }
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
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":usecases"))
    implementation(project(":test:unit"))

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
    implementation(libs.androidx.ui.text.google.fonts)
    //Icons
    implementation(libs.androidx.material)
    implementation(libs.androidx.material.icons.extended)

    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)

    implementation(libs.coil.compose)
    implementation(libs.kotlinx.coroutines.android)
    //Serialization
    implementation(libs.kotlinx.serialization.json)// Or your preferred format

    implementation (libs.retrofit.core)
    implementation (libs.okhttp)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    testImplementation(libs.room.testing)

    //Koin
    implementation (libs.koin.android)
    implementation (libs.koin.annotations)
    implementation (libs.koin.androidx.compose)
    ksp (libs.koin.ksp.compiler)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.koin.test)


    testImplementation(libs.mockk)
    implementation(libs.turbine)
    testImplementation("org.robolectric:robolectric:4.9")}