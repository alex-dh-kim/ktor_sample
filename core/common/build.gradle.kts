plugins {
    alias(libs.plugins.dh.android.library)
}

android {
    namespace = "com.dh.alex.module.common"
}

dependencies {
    implementation(libs.arrow)
}
