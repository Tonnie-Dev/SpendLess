plugins {
    id("spendless.kotlin.library")
    id("spendless.kotlin.koin")
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:common"))
    implementation(project(":core:repository"))
}