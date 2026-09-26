plugins {
    id("spendless.android.library")
    id("spendless.android.koin")
}

android {
    namespace = "dev.tonnie.datastore"
}

dependencies {
    implementation(libs.androidx.datastore.preferences)

}