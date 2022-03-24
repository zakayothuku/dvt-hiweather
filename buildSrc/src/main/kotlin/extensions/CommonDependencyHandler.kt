import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.ProjectDependency
import org.gradle.api.artifacts.dsl.DependencyHandler

/**
 * Credit: https://github.com/igorwojda/android-showcase/blob/master/buildSrc/src/main/kotlin/CommonModuleDependency.kt
 * Define common dependencies, so they can be easily updated across another modules
 **/
fun DependencyHandler.addTestDependencies() {
    implementation(project(Modules.TEST_UTILS))

    testImplementation(Dependencies.TestDependencies.junit)
    testImplementation(Dependencies.TestDependencies.junit5Api)
    testImplementation(Dependencies.TestDependencies.junit5Engine)

    testImplementation(Dependencies.TestDependencies.testExt)
    testImplementation(Dependencies.TestDependencies.testRunner)
    testImplementation(Dependencies.TestDependencies.mockk)

    testImplementation(Dependencies.TestDependencies.roomTest)
    testImplementation(Dependencies.TestDependencies.coroutineTest)
    testImplementation(Dependencies.TestDependencies.archTesting)
    testImplementation(Dependencies.TestDependencies.mockWebServer)
    testImplementation(Dependencies.TestDependencies.fragmentTest)
    androidTestImplementation(Dependencies.TestDependencies.fragmentTest)

    testImplementation(Dependencies.TestDependencies.truth)
    androidTestImplementation(Dependencies.TestDependencies.truth)

    androidTestImplementation(Dependencies.TestDependencies.testCore)
    androidTestImplementation(Dependencies.TestDependencies.testExt)
    androidTestImplementation(Dependencies.TestDependencies.testRules)
    androidTestImplementation(Dependencies.TestDependencies.archTesting)
    androidTestImplementation(Dependencies.TestDependencies.coroutineTest)
    androidTestImplementation(Dependencies.TestDependencies.workTest)

    androidTestImplementation(Dependencies.TestAndroidDependencies.hiltTesting)

    androidTestImplementation(Dependencies.TestAndroidDependencies.Espresso.espresso)
    androidTestImplementation(Dependencies.TestAndroidDependencies.Espresso.espressoContrib)
    androidTestImplementation(Dependencies.TestAndroidDependencies.Espresso.espressoIntent)
    androidTestImplementation(Dependencies.TestAndroidDependencies.Espresso.idlingResource)
}

/**
 * These extensions mimic the extensions that are generated on the fly by Gradle.
 * They are used here to provide above dependency syntax that mimics Gradle Kotlin DSL
 * syntax in module\build.gradle.kts files.
 **/
@Suppress("detekt.UnusedPrivateMember")
fun DependencyHandler.implementation(dependencyNotation: Any): Dependency? =
    add("implementation", dependencyNotation)

@Suppress("detekt.UnusedPrivateMember")
fun DependencyHandler.api(dependencyNotation: Any): Dependency? =
    add("api", dependencyNotation)

@Suppress("detekt.UnusedPrivateMember")
fun DependencyHandler.kapt(dependencyNotation: Any): Dependency? =
    add("kapt", dependencyNotation)

private fun DependencyHandler.testImplementation(dependencyNotation: Any): Dependency? =
    add("testImplementation", dependencyNotation)

private fun DependencyHandler.androidTestImplementation(dependencyNotation: Any): Dependency? =
    add("androidTestImplementation", dependencyNotation)

private fun DependencyHandler.project(
    path: String,
    configuration: String? = null
): ProjectDependency {
    val notation = if (configuration != null) {
        mapOf("path" to path, "configuration" to configuration)
    } else {
        mapOf("path" to path)
    }

    return uncheckedCast(project(notation))
}

@Suppress("unchecked_cast", "nothing_to_inline", "detekt.UnsafeCast")
private inline fun <T> uncheckedCast(obj: Any?): T = obj as T
