plugins {
    id("spendless.android.library")
    id("spendless.android.compose")
    id("spendless.android.koin")
}

android {
    namespace = "dev.tonnie.designsystem"
}

dependencies {
    api(AndroidX.compose.material3)
}
