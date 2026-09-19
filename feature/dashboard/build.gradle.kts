plugins {
    id("spendless.android.library")
    id("spendless.android.compose")
}

android {
    namespace = "dev.tonnie.dashboard"
}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(project(":core:model"))
    implementation(project(":core:datastore"))
}

