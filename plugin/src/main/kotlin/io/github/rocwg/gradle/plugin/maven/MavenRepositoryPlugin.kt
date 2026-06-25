package io.github.rocwg.gradle.plugin.maven

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin
import java.net.URI

/**
 * @author livk
 */
abstract class MavenRepositoryPlugin : Plugin<Project> {

	override fun apply(project: Project) {
		project.pluginManager.apply(MavenPublishPlugin::class.java)

		project.extensions.getByType(PublishingExtension::class.java).run {
			repositories.mavenLocal()
			try {
				val releasesRepoUrl = project.property("mvn.releasesRepoUrl").toString()
				val snapshotsRepoUrl = project.property("mvn.releasesRepoUrl").toString()
				repositories.maven {
					name = "CustomizeMaven"
					isAllowInsecureProtocol = true
					url = if (project.version.toString().endsWith("SNAPSHOT")) {
						URI(snapshotsRepoUrl)
					} else {
						URI(releasesRepoUrl)
					}
					credentials {
						username = project.property("mvn.username").toString()
						password = project.property("mvn.password").toString()
					}
				}
			} catch (_: Exception) {
				// 忽略异常
			}
		}
	}
}
