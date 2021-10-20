import io.github.cdsap.talaiot.logger.LogTracker
import io.github.cdsap.talaiot.plugin.TalaiotPluginExtension

plugins {
    id("io.github.cdsap.talaiot")
}

object ConventionTalaiotConstants {
    const val BUILD_METRICS_TAG_PROPERTY = "buildMetricsTag"
}


fun getBuildMetricsTag(
    project: Project,
    rootProject: Project
): String? {
    val requestedTasks: List<String> = project.gradle.startParameter.taskNames
    val isLocalBuild = rootProject.isLocalBuild()
    val hasBuildTagProperty = project.hasProperty(ConventionTalaiotConstants.BUILD_METRICS_TAG_PROPERTY)

    return if (hasBuildTagProperty.not() && requestedTasks.size == 1 && isLocalBuild) {
        when (requestedTasks[0]) {
            ":app:assembleDebug" -> {
                "local-debug"
            }

            else -> {
                null
            }
        }
    } else {
        project.properties[ConventionTalaiotConstants.BUILD_METRICS_TAG_PROPERTY]?.toString()
    }
}

fun Project.isLocalBuild(): Boolean {
    return hasProperty("android.injected.invoked.from.ide")
}

fun getSystemEnvironmentVar(name: String): String? {
    return System.getenv(name)
}


val buildMetricsTag = getBuildMetricsTag(project, rootProject)

if (buildMetricsTag != null) {
    configure<TalaiotPluginExtension> {
        logger = LogTracker.Mode.INFO

        publishers {
            outputPublisher {}
        }

        metrics {
            defaultMetrics = true
            performanceMetrics = true
            gradleSwitchesMetrics = true

            val shortPlanName: String = System.getenv("BUILD_PLAN_NAME") ?: ""
            val agentId: String = System.getenv("BUILD_AGENT_ID") ?: ""
            val isConfigurationCacheEnabled = providers
                .systemProperty("org.gradle.unsafe.configuration-cache")
                .forUseAtConfigurationTime()
                .isPresent


            customBuildMetrics(
                mapOf(
                    ConventionTalaiotConstants.BUILD_METRICS_TAG_PROPERTY to buildMetricsTag,
                    "modulesCount" to rootProject.allprojects.size.toString(),
                    "fromIde" to rootProject.isLocalBuild().toString(),
                    "ciPlanName" to shortPlanName,
                    "ciAgentId" to agentId,
                    "isR8Enabled" to (project.properties["android.enableR8"] != "false").toString(),
                    "isConfigurationCacheEnabled" to isConfigurationCacheEnabled.toString()
                )
            )
        }

        filter {
            build {
                success = true
            }
        }
    }
}
