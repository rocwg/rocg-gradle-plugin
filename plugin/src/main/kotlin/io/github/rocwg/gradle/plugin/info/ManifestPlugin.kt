package io.github.rocwg.gradle.plugin.info

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.bundling.Jar
import org.gradle.util.GradleVersion

/**
 * @author livk
 */
abstract class ManifestPlugin : Plugin<Project> {

	override fun apply(project: Project) {
		project.pluginManager.apply(JavaPlugin::class.java)
		project.tasks.withType(Jar::class.java).configureEach {
			manifest {
				attributes.putIfAbsent("Implementation-Group", project.group)
				attributes.putIfAbsent("Implementation-Title", project.name)
				attributes.putIfAbsent("Implementation-Version", project.version)
				attributes.putIfAbsent("Created-Jdk", System.getProperty("java.version"))
				attributes.putIfAbsent("Gradle-Version", GradleVersion.current())
			}
		}
	}
}
