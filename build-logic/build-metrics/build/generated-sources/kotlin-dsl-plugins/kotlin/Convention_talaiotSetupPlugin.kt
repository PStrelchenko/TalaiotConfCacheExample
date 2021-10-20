/**
 * Precompiled [convention.talaiot-setup.gradle.kts][Convention_talaiot_setup_gradle] script plugin.
 *
 * @see Convention_talaiot_setup_gradle
 */
class Convention_talaiotSetupPlugin : org.gradle.api.Plugin<org.gradle.api.Project> {
    override fun apply(target: org.gradle.api.Project) {
        try {
            Class
                .forName("Convention_talaiot_setup_gradle")
                .getDeclaredConstructor(org.gradle.api.Project::class.java, org.gradle.api.Project::class.java)
                .newInstance(target, target)
        } catch (e: java.lang.reflect.InvocationTargetException) {
            throw e.targetException
        }
    }
}
