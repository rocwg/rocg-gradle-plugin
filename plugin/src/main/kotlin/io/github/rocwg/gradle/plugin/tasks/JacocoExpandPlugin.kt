package io.github.rocwg.gradle.plugin.tasks

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import org.gradle.testing.jacoco.tasks.JacocoReport

/**
 * @author livk
 */
abstract class JacocoExpandPlugin : Plugin<Project> {

	companion object {
		const val TASK_NAME = "jacocoTestReport"
	}

	override fun apply(project: Project) {

		project.pluginManager.apply(JacocoPlugin::class.java)

		// 所有 Test 任务执行完自动触发报告
		project.tasks.withType(Test::class.java).configureEach {
			finalizedBy(TASK_NAME)
		}

		// 配置 Jacoco 报告
		project.tasks.named(TASK_NAME, JacocoReport::class.java).configure {
			dependsOn("test")
			reports {
				xml.required.set(true)
				html.required.set(false)
			}
		}
	}
}
