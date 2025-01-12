plugins {
    alias(libs.plugins.dh.android.library)
    alias(libs.plugins.dh.hilt)
}

android {
    namespace = "com.dh.alex.module.data"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:network"))
    implementation(project(":core:model"))

    implementation(libs.arrow)
}
