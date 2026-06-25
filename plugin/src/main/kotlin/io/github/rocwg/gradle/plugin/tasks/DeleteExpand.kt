package io.github.rocwg.gradle.plugin.tasks

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Delete

/**
 * @author livk
 */
abstract class DeleteExpand : Plugin<Project> {

	companion object {
		val CLEAN_FILES = setOf(
			"build",
			"out",
			"bin",
			"src/main/generated",
			"src/test/generated_tests"
		)
	}

	override fun apply(project: Project) {

		project.tasks.withType(Delete::class.java) {
			CLEAN_FILES.forEach { path ->
				delete(project.layout.projectDirectory.dir(path))
			}
		}
	}
}
