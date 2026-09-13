import org.gradle.kotlin.dsl.`kotlin-dsl`
import org.jetbrains.kotlin.gradle.internal.backend.common.serialization.metadata.DynamicTypeDeserializer.id

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
        register("androidLibrary") {
            id = "spendless.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
    }

}