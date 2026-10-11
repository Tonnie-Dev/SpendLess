plugins {
    id("spendless.android.library")
    id("spendless.android.compose")
    id("spendless.android.koin")
}

android {
    namespace = "dev.tonnie.dashboard"
}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(project(":core:model"))
    implementation(project(":core:datastore"))
    implementation(project(":core:presentation"))
    implementation(project(":core:domain"))
}

