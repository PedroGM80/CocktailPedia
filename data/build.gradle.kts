plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}
dependencies {
    implementation(project(":domain"))
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)
}