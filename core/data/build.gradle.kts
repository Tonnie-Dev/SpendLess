plugins {
    id("spendless.android.library")
    id("spendless.android.koin")
    id("spendless.testing")
}

android {
    namespace = "dev.tonnie.data"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:common"))
    implementation(project(":core:domain"))
    implementation(project(":core:repository"))
    implementation(project(":core:database"))
    implementation(project(":core:datastore"))
}