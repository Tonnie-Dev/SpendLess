plugins {
    `kotlin-dsl`
}

group = "dev.tonnie.buildlogic"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    implementation(libs.ksp.gradlePlugin)
}

gradlePlugin {

    plugins {

        register("androidApplication") {
            id = "spendless.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "spendless.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("androidCompose") {
            id = "spendless.android.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }

        register("androidKsp") {
            id = "spendless.android.ksp"
            implementationClass = "AndroidKspConventionPlugin"
        }

        register("kotlinSerialization") {
            id = "spendless.kotlin.serialization"
            implementationClass = "KotlinSerializationConventionPlugin"
        }

        register("kotlinLibrary") {
            id = "spendless.kotlin.library"
            implementationClass = "KotlinLibraryConventionPlugin"
        }
    }

}