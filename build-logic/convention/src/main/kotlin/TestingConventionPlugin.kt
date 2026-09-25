import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType

class TestingConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {

            tasks.withType<Test>().configureEach {
                useJUnitPlatform()
            }

            dependencies {
                add(
                        "testImplementation",
                        libs.findLibrary("junit-jupiter").get()
                )

                add(
                        "testImplementation",
                        libs.findLibrary("kotest-assertions-core").get()
                )

                add(
                        "testImplementation",
                        libs.findLibrary("kotest-runner-junit5").get()
                )
            }
        }
    }
}