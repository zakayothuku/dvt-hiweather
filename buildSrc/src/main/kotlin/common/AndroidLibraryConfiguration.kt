package common

import AppInfo
import Dependencies
import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class AndroidLibraryConfiguration : Plugin<Project> {

    override fun apply(target: Project) {
        target.plugins.apply(Dependencies.Plugins.Android.androidLibrary)
        target.plugins.apply(Dependencies.Plugins.Kotlin.parcelize)
        // target.plugins.apply(Dependencies.Plugins.daggerHiltAndroidPlugin)

        // Configure common android build parameters.
        val androidExtension = target.extensions.getByName("android")
        if (androidExtension is BaseExtension) {

            androidExtension.apply {
                compileSdkVersion(AppInfo.COMPILE_SDK_VERSION)

                defaultConfig {
                    minSdk = AppInfo.MIN_SDK_VERSION
                    targetSdk = AppInfo.TARGET_SDK_VERSION
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                target.tasks.withType(KotlinCompile::class.java).configureEach {
                    kotlinOptions {
                        jvmTarget = "11"
                    }
                }

                dataBinding {
                    isEnabled = true
                }

                buildFeatures.viewBinding = true

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_11
                    targetCompatibility = JavaVersion.VERSION_11
                }

                lintOptions {
                    isAbortOnError = false
                    isWarningsAsErrors = true
                    isCheckDependencies = true
                    isIgnoreTestSources = true
                    lintConfig = target.file(".configs/lint.xml")
                    disable("GradleDeprecated")
                    disable("OldTargetApi")
                    disable("GradleDependency")
                }

                testOptions {
                    unitTests.isIncludeAndroidResources = true
                    unitTests.isReturnDefaultValues = true
                }

                sourceSets {
                    getByName("main") {
                        java.srcDir("src/main/kotlin")
                    }
                    getByName("test") {
                        java.srcDir("src/test/kotlin")
                    }
                    getByName("androidTest") {
                        java.srcDir("src/androidTest/kotlin")
                    }
                }

                packagingOptions {
                    resources.excludes.add("**/attach_hotspot_windows.dll")
                    resources.excludes.add("META-INF/licenses/**")
                    resources.excludes.add("META-INF/LICENSE.md")
                    resources.excludes.add("META-INF/NOTICE.md")
                    resources.excludes.add("META-INF/AL2.0")
                    resources.excludes.add("META-INF/LGPL2.1")
                }
            }
        }
    }
}
