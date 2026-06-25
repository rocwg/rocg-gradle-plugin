package io.github.rocwg.gradle.plugin.info

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.bundling.Jar
import org.springframework.boot.gradle.plugin.SpringBootPlugin
import org.springframework.boot.gradle.tasks.bundling.BootJar

/**
 * @author livk
 */
abstract class BootPlugin : Plugin<Project> {

	override fun apply(project: Project) {
		project.pluginManager.apply(JavaPlugin::class.java)
		project.pluginManager.apply(SpringBootPlugin::class.java)

		project.tasks.named(SpringBootPlugin.BOOT_JAR_TASK_NAME, BootJar::class.java) {
			archiveBaseName.set(project.name)
			archiveFileName.set("${archiveBaseName.get()}.${archiveExtension.get()}")
			duplicatesStrategy = DuplicatesStrategy.EXCLUDE
		}

		project.tasks.named(JavaPlugin.JAR_TASK_NAME, Jar::class.java) {
			enabled = false
		}
	}
}
