package io.github.rocwg.gradle.plugin.dependency

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.bundling.Jar

/**
 * @author livk
 */
abstract class OptionalPlugin : Plugin<Project> {

	companion object {
		const val OPTIONAL = "optional"
	}

	override fun apply(project: Project) {
		project.pluginManager.apply(JavaPlugin::class.java)
		val configurations = project.configurations
		val optional = configurations.create(OPTIONAL).apply {
			isCanBeResolved = false
			isCanBeConsumed = false
		}
		project.plugins.withType(JavaPlugin::class.java).configureEach {
			val javaExt = project.extensions.getByType(JavaPluginExtension::class.java)
			with(javaExt) {
				val optionalSourceSet = sourceSets.create(OPTIONAL)
				registerFeature(OPTIONAL) {
					usingSourceSet(optionalSourceSet)
				}
				sourceSets.configureEach {
					configurations.named(compileClasspathConfigurationName).configure {
						extendsFrom(optional)
					}
					configurations.named(runtimeClasspathConfigurationName).configure {
						extendsFrom(optional)
					}
					configurations.named("${OPTIONAL}${JavaPlugin.API_CONFIGURATION_NAME.replaceFirstChar { c -> c.uppercase() }}")
						.configure {
							extendsFrom(optional)
						}
				}
				sourceSets.remove(optionalSourceSet)
			}
		}
		project.tasks.withType(Jar::class.java) {
			if (name == "${OPTIONAL}${JavaPlugin.JAR_TASK_NAME.replaceFirstChar { it.uppercase() }}") {
				isEnabled = false
			}
		}
	}
}
