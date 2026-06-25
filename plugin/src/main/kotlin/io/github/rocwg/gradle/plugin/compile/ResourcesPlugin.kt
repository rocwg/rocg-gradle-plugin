package io.github.rocwg.gradle.plugin.compile

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaLibraryPlugin
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.compile.JavaCompile

/**
 * @author livk
 */
abstract class ResourcesPlugin : Plugin<Project> {

	override fun apply(project: Project) {
		project.pluginManager.apply(JavaLibraryPlugin::class.java)
		project.tasks.named(JavaPlugin.COMPILE_JAVA_TASK_NAME, JavaCompile::class.java) {
			dependsOn(JavaPlugin.PROCESS_RESOURCES_TASK_NAME)
		}
	}
}
