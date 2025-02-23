plugins {
    alias(libs.plugins.java.library)
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.ksp)
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}
dependencies {
    implementation(project(":domain"))
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)

    //Koin
    implementation (libs.koin.core)
    implementation (libs.koin.annotations)
    ksp (libs.koin.ksp.compiler)
}
