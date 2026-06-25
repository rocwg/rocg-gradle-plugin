package io.github.rocwg.gradle.plugin.maven

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.plugins.signing.SigningExtension
import org.gradle.plugins.signing.SigningPlugin

/**
 * @author livk
 */
abstract class DeployedPlugin : Plugin<Project> {

	override fun apply(project: Project) {
		project.pluginManager.apply(SigningPlugin::class.java)
		project.pluginManager.apply(MavenRepositoryPlugin::class.java)
		project.pluginManager.apply(MavenPortalPublishPlugin::class.java)

		project.extensions.configure(SigningExtension::class.java) {
			useGpgCmd()
		}
	}
}
