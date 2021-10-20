pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    resolutionStrategy {
        eachPlugin {
            val pluginId = requested.id.id
            when {
                pluginId == "io.github.cdsap.talaiot" ->
                    useVersion("1.5.1")

                pluginId.startsWith("com.android.") ->
                    useModule("com.android.tools.build:gradle:7.0.2")

                pluginId.startsWith("org.jetbrains.kotlin.") ->
                    useVersion("1.5.31")

            }
        }
    }

}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }


}


rootProject.name = "TalaiotConfCacheExample"

includeBuild("build-logic")

include("app")
