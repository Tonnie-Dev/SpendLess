plugins {
    id("spendless.android.library")
    id("spendless.android.koin")}

android {
    namespace = "dev.tonnie.data"
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:common"))
    implementation(project(":core:repository"))
    implementation(project(":core:database"))
    implementation(project(":core:datastore"))
}