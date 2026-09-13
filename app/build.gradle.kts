plugins {
    id("spendless.android.application")
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp.plugin)
    alias(libs.plugins.kotlin.serializer.plugin)
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


    // Core Libs
    implementation(AndroidX.core.ktx)
    implementation(AndroidX.lifecycle.runtime.ktx)
    implementation(AndroidX.activity.compose)
    implementation(platform(AndroidX.compose.bom))
    implementation(AndroidX.compose.ui)
    implementation(AndroidX.compose.ui.graphics)
    implementation(AndroidX.compose.ui.toolingPreview)

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
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}