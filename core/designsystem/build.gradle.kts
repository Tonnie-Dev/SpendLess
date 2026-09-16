plugins {
    id("spendless.android.library")
    id("spendless.android.compose")
}

android {
    namespace = "dev.tonnie.designsystem"
}

dependencies {
    api(AndroidX.compose.material3)

}
