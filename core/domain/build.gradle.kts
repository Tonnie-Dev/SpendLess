plugins {
    id("spendless.kotlin.library")
}

dependencies {
    implementation(project(":core:model"))
    implementation(project(":core:common"))
    implementation(project(":core:repository"))
}