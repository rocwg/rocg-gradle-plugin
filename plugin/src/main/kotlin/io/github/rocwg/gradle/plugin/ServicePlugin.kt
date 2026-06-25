package io.github.rocwg.gradle.plugin

import io.github.rocwg.gradle.plugin.info.BootPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin

/**
 * @author livk
 */
class ServicePlugin : Plugin<Project> {

	override fun apply(project: Project) {
		project.pluginManager.apply(JavaPlugin::class.java)
		project.pluginManager.apply(ModulePlugin::class.java)
		project.pluginManager.apply(BootPlugin::class.java)
	}
}
