package plugins

import kotlinx.kover.KoverPlugin
import kotlinx.kover.api.KoverExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

class PluginKover : Plugin<Project> {

    override fun apply(target: Project) {
        target.apply<KoverPlugin>()

        target.extensions.configure<KoverExtension> {
            isDisabled = false
            coverageEngine.set(kotlinx.kover.api.CoverageEngine.INTELLIJ)
            intellijEngineVersion.set("1.0.656")
            jacocoEngineVersion.set("0.8.7")
            generateReportOnCheck = true
            disabledProjects = setOf(
                "test-utils"
            )
            instrumentAndroidPackage = true
            runAllTestsForProjectTask = true
        }
    }
}
