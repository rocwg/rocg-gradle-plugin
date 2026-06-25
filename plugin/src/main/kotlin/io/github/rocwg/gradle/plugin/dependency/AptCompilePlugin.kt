package io.github.rocwg.gradle.plugin.dependency

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin

/**
 * @author livk
 */
abstract class AptCompilePlugin : Plugin<Project> {

	companion object {
		const val APT_COMPILE = "aptCompile"

		val DEPENDENCY_NAMES_SET = setOf(
			JavaPlugin.COMPILE_CLASSPATH_CONFIGURATION_NAME,
			JavaPlugin.ANNOTATION_PROCESSOR_CONFIGURATION_NAME,
			JavaPlugin.TEST_COMPILE_CLASSPATH_CONFIGURATION_NAME,
			JavaPlugin.TEST_ANNOTATION_PROCESSOR_CONFIGURATION_NAME
		)
	}

	override fun apply(project: Project) {
		project.pluginManager.apply(JavaPlugin::class.java)

		val configurations = project.configurations

		val apt = configurations.create(APT_COMPILE).apply {
			isCanBeResolved = false
			isCanBeConsumed = false
		}

		project.plugins.withType(JavaPlugin::class.java).configureEach {
			DEPENDENCY_NAMES_SET.forEach { configName ->
				configurations.named(configName).configure {
					extendsFrom(apt)
				}
			}
		}
	}
}
