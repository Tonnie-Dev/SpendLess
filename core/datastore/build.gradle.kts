plugins {
    id("spendless.android.library")
}

android {
    namespace = "dev.tonnie.datastore"
}

dependencies {
    implementation(libs.androidx.datastore.preferences)

}