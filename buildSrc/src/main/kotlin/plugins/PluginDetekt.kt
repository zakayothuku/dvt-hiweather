package plugins

import io.gitlab.arturbosch.detekt.DetektPlugin
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

open class PluginDetekt : Plugin<Project> {

    override fun apply(target: Project) {
        target.apply<DetektPlugin>()
        target.extensions.configure<DetektExtension> {
            toolVersion = "1.18.1"
            parallel = false
            buildUponDefaultConfig = true
            config = target.files("${target.rootDir}/.configs/detekt.yml")
            reports {
                xml {
                    enabled = true
                    destination = target.file("${target.buildDir}/reports/detekt/report.xml")
                }
                html {
                    enabled = true
                    destination = target.file("${target.buildDir}/reports/detekt/report.html")
                }
            }
        }
    }
}