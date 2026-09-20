plugins {
    id("spendless.android.library")
    id("spendless.android.ksp")
    id("spendless.android.koin")
}

android {
    namespace = "dev.tonnie.database"
}

dependencies {
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
}