package io.github.rocwg.gradle.plugin

import io.github.rocwg.gradle.plugin.maven.DeployedPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlatformExtension
import org.gradle.api.plugins.JavaPlatformPlugin

/**
 * @author livk
 */
class BomPlugin : Plugin<Project> {

	override fun apply(project: Project) {
		project.pluginManager.apply(JavaPlatformPlugin::class.java)
		project.pluginManager.apply(DeployedPlugin::class.java)
		project.extensions.getByType(JavaPlatformExtension::class.java).allowDependencies()
	}
}
