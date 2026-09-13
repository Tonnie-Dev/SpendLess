import org.gradle.kotlin.dsl.`kotlin-dsl`

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

}

gradlePlugin {

    plugins {

        register("androidApplication") {
            id = "spendless.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
    }
}