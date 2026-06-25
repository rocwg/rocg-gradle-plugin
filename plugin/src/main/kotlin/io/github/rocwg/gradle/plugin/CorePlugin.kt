package io.github.rocwg.gradle.plugin

import io.github.rocwg.gradle.plugin.dependency.AptCompilePlugin
import io.github.rocwg.gradle.plugin.dependency.ManagementPlugin
import io.github.rocwg.gradle.plugin.dependency.OptionalPlugin
import io.github.rocwg.gradle.plugin.info.ManifestPlugin
import io.github.rocwg.gradle.plugin.tasks.DeleteExpand
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.quality.CheckstyleExtension
import org.gradle.api.plugins.quality.CheckstylePlugin
import java.io.File

/**
 * @author livk
 */
class CorePlugin : Plugin<Project> {

	override fun apply(project: Project) {
		project.pluginManager.apply(DeleteExpand::class.java)
		project.pluginManager.apply(ManagementPlugin::class.java)
		project.pluginManager.apply(OptionalPlugin::class.java)
		project.pluginManager.apply(AptCompilePlugin::class.java)
		project.pluginManager.apply(ManifestPlugin::class.java)
		project.pluginManager.apply(CheckstylePlugin::class.java)

		project.extensions.getByType(CheckstyleExtension::class.java).run {
			val checkstyleVersion = project.rootProject
				.extensions
				.getByType(VersionCatalogsExtension::class.java)
				.named("libs")
				.findVersion("checkstyle")
				.get()
				.displayName
			toolVersion = checkstyleVersion
			configFile = File("${project.rootDir.path}/src/checkstyle/checkstyle.xml")
		}
	}
}
