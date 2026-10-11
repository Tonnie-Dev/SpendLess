plugins {
    id("spendless.android.library")
    id("spendless.android.compose")
    id("spendless.testing")
}

android {
    namespace = "dev.tonnie.presentation"
}

dependencies {
    api(AndroidX.lifecycle.runtime.ktx)
    api(AndroidX.activity.compose)

}