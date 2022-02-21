package extensions

import com.android.build.gradle.internal.dsl.BuildType
import org.gradle.api.Project
import java.util.Properties


/**
 * Extension to adds a new string field to the generated BuildConfig class.
 *
 * @param name the name of the field
 * @param value the string value of the field
 */
fun BuildType.buildConfigStringField(name: String, value: String) {
    this.buildConfigField("String", name, "\"$value\"")
}

/**
 * Obtain property declared on `$projectRoot/local.properties` file.
 *
 * @param propertyName the name of declared property
 */
fun Project.getLocalProperty(propertyName: String): String {
    return getLocalProperty(propertyName, this)
}

/**
 * Util to obtain property declared on `$projectRoot/local.properties` file.
 *
 * @param propertyName the name of declared property
 * @param project the project reference
 *
 * @return the value of property name, otherwise throw [Exception]
 */
fun getLocalProperty(propertyName: String, project: Project): String {
    return getProperty("local.properties", propertyName, project)
}

/**
 * Util to obtain property declared on file in path.
 *
 * @param fileName the file name declaring the properties
 * @param propertyName the name of declared property
 * @param project the project reference
 *
 * @return the value of property name, otherwise throw [Exception]
 */
private fun getProperty(fileName: String, propertyName: String, project: Project): String {
    val properties = Properties().apply {
        val propertiesFile = project.rootProject.file(fileName)
        if (propertiesFile.exists()) {
            load(propertiesFile.inputStream())
        }
    }

    return properties.getProperty(propertyName) ?: run {
        throw NoSuchFieldException("Not defined property: $propertyName")
    }
}


