plugins {
    id("spendless.android.application")
    id("spendless.android.compose")
    id("spendless.android.ksp")
    id("spendless.kotlin.serialization")
}

android {
    namespace = "dev.tonnie.spendless"

    defaultConfig {
        applicationId = "dev.tonnie.spendless"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
}

dependencies {
    implementation(project(":feature:onboarding"))
    implementation(project(":core:designsystem"))

    // Core Libs
    implementation(AndroidX.core.ktx)
    implementation(AndroidX.lifecycle.runtime.ktx)
    implementation(AndroidX.activity.compose)

    // Navigation 3
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)

    implementation(libs.androidx.lifecycle.viewmodel.navigation3)

    // Material 3
    implementation(AndroidX.compose.material3)

    // Window Size Class
    implementation(AndroidX.compose.material3.windowSizeClass)
    implementation(libs.material3.adaptive)

    // Material Extended Icons
    implementation(AndroidX.compose.material.icons.extended)

    // Splash Screen
    implementation(AndroidX.core.splashscreen)

    // Room
    implementation(AndroidX.room.ktx)
    ksp(AndroidX.room.compiler)

    // Data Store
    implementation(AndroidX.dataStore.preferences)

    // Koin
    implementation(Koin.android)
    implementation(Koin.compose)

    // Kotlinx Serialization
    implementation(KotlinX.serialization.json)

    // Logging
    implementation(JakeWharton.timber)

    // Biometrics
    implementation(AndroidX.biometric)

    // Process lifecycle
    implementation(AndroidX.lifecycle.process)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.kotlinTest)
    testImplementation(libs.junit.jupiter)
    testImplementation(Testing.Kotest.assertions.core)
    testImplementation(Testing.Kotest.runner.junit5)
    testImplementation(libs.kxml2)

    // Android Tests
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(AndroidX.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
}
