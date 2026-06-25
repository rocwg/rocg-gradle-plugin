package io.github.rocwg.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.tasks.bundling.Jar

/**
 * @author livk
 */
class RootPlugin : Plugin<Project> {

	override fun apply(project: Project) {
		project.pluginManager.apply(BasePlugin::class.java)
		project.pluginManager.apply(CorePlugin::class.java)

		project.tasks.withType(Jar::class.java).configureEach {
			enabled = false
		}
	}
}
