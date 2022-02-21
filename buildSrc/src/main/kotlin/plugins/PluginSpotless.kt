package plugins

import com.diffplug.gradle.spotless.SpotlessExtension
import com.diffplug.gradle.spotless.SpotlessPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

open class PluginSpotless : Plugin<Project> {

    override fun apply(target: Project) {
        target.apply<SpotlessPlugin>()
        target.extensions.configure<SpotlessExtension> {
            format("misc") {
                target(
                    target.fileTree(
                        mapOf(
                            "dir" to ".",
                            "include" to listOf("**/*.md", "**/.gitignore", "**/*.yaml", "**/*.yml"),
                            "exclude" to listOf(
                                ".gradle/**",
                                ".gradle-cache/**",
                                "**/tools/**",
                                "**/build/**"
                            )
                        )
                    )
                )
                trimTrailingWhitespace()
                indentWithSpaces()
                endWithNewline()
            }

            format("xml") {
                target("**/res/**/*.xml")
                indentWithSpaces(4)
                trimTrailingWhitespace()
                endWithNewline()
            }

            kotlin {
                target(
                    target.fileTree(
                        mapOf(
                            "dir" to ".",
                            "include" to listOf("**/*.kt"),
                            "exclude" to listOf("**/build/**", "**/buildSrc/**", "**/.*")
                        )
                    )
                )
                licenseHeaderFile(
                    target.rootProject.file(".configs/spotless.kt"),
                    "^(package|object|import|interface)"
                )
                trimTrailingWhitespace()
                indentWithSpaces()
                endWithNewline()
            }

            kotlinGradle {
                target(
                    target.fileTree(
                        mapOf(
                            "dir" to ".",
                            "include" to listOf("**/*.gradle.kts", "*.gradle.kts"),
                            "exclude" to listOf("**/build/**")
                        )
                    )
                )
                licenseHeaderFile(
                    target.rootProject.file(".configs/spotless.kt"),
                    "package|import|tasks|apply|plugins|include|val|object|interface"
                )
                trimTrailingWhitespace()
                indentWithSpaces()
                endWithNewline()
            }
        }
    }
}