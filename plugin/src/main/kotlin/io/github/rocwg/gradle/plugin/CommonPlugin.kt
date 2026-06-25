package io.github.rocwg.gradle.plugin

import io.github.rocwg.gradle.plugin.compile.ResourcesPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaLibraryPlugin

/**
 * @author livk
 */
class CommonPlugin : Plugin<Project> {

	override fun apply(project: Project) {
		project.pluginManager.apply(JavaLibraryPlugin::class.java)
		project.pluginManager.apply(ModulePlugin::class.java)
		project.pluginManager.apply(ResourcesPlugin::class.java)
	}
}
