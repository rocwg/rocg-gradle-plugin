package io.github.rocwg.gradle.plugin.compile

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.javadoc.Javadoc
import org.gradle.api.tasks.testing.Test
import org.gradle.external.javadoc.JavadocOutputLevel
import org.gradle.external.javadoc.StandardJavadocDocletOptions

/**
 * @author livk
 */
abstract class CompileArgsPlugin : Plugin<Project> {

	companion object {
		val COMPILER_ARGS = listOf(
			"-Xlint:-options",
			"-Xlint:varargs",
			"-Xlint:rawtypes",
			"-Xlint:deprecation",
			"-Xlint:unchecked",
			"-Werror",
			"-parameters"
		)

		const val UTF_8 = "UTF-8"
	}

	override fun apply(project: Project) {
		project.pluginManager.apply(JavaPlugin::class.java)

		// Javadoc
		project.tasks.withType(Javadoc::class.java).configureEach {
			isFailOnError = false
			val options = options as StandardJavadocDocletOptions
			options.encoding = UTF_8
			options.outputLevel = JavadocOutputLevel.QUIET
			options.addStringOption("Xdoclint:none", "-quiet")
		}

		// JavaCompile
		project.tasks.withType(JavaCompile::class.java)
			.matching {
				it.name in listOf(
					JavaPlugin.COMPILE_JAVA_TASK_NAME,
					JavaPlugin.COMPILE_TEST_JAVA_TASK_NAME,
					"compileTestFixturesJava"
				)
			}
			.configureEach { addCompile(this) }

		// Test
		project.tasks.withType(Test::class.java).configureEach {
			useJUnitPlatform()
		}
	}

	private fun addCompile(javaCompile: JavaCompile) {
		javaCompile.options.compilerArgs.addAll(COMPILER_ARGS)
		javaCompile.options.encoding = UTF_8
		javaCompile.sourceCompatibility = JavaVersion.VERSION_21.toString()
		javaCompile.targetCompatibility = JavaVersion.VERSION_21.toString()
	}
}
