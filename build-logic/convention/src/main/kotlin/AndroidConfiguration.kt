import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCommon(
    commonExtension: CommonExtension,
) {
    commonExtension.apply {

        compileSdk {
            version = release(37)
        }

        defaultConfig.minSdk = 24

        compileOptions.apply {
            isCoreLibraryDesugaringEnabled = true
            sourceCompatibility = JavaVersion.VERSION_21
            targetCompatibility = JavaVersion.VERSION_21
        }

    }

    dependencies {
        add(
                "coreLibraryDesugaring",
                libs.findLibrary("desugar-jdk-libs").get()
        )
    }
}






