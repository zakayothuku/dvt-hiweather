package plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.jlleitschuh.gradle.ktlint.KtlintPlugin
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

class PluginKtlint : Plugin<Project> {
    override fun apply(target: Project) {
        target.apply<KtlintPlugin>()
        target.extensions.configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
            version.set("0.42.1")
            android.set(true)
            outputToConsole.set(true)
            outputColorName.set("RED")
            enableExperimentalRules.set(true)
            additionalEditorconfigFile.set(target.file("${target.rootDir}/.editorconfig"))
            reporters {
                reporter(ReporterType.HTML)
            }
            filter {
                exclude("**/generated/**")
                include("**/kotlin/**")
            }
        }
    }
}
