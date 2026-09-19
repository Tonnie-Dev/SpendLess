plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "dev.tonnie.data"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:database"))
    implementation(project(":core:datastore"))
}