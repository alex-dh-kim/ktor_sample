plugins {
    alias(libs.plugins.dh.network)
}

android {
    namespace = "com.dh.alex.module.network"
}

dependencies {
    implementation(project(":core:common"))

    implementation(libs.arrow)
}
