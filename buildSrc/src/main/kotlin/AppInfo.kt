/**
 * Configuration of android build
 */
object AppInfo {
    const val APPLICATION_ID = "com.dvttest.hiweather"

    const val COMPILE_SDK_VERSION = 31
    const val MIN_SDK_VERSION = 24
    const val TARGET_SDK_VERSION = 30

    private const val versionMajor = 1 // Major UI or Infrastructure or Business Changes
    private const val versionMinor = 0 // New Features
    private const val versionPatch = 0 // Bug Fixes, Patches, Enhancements

    const val VERSION_CODE = versionMajor * 10000 + versionMinor * 100 + versionPatch
    const val VERSION_NAME = "$versionMajor.$versionMinor.$versionPatch"
}
