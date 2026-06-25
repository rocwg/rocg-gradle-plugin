package io.github.rocwg.gradle.plugin.dependency

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.plugins.JavaTestFixturesPlugin
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin

/**
 * @author livk
 */
abstract class ManagementPlugin : Plugin<Project> {

	companion object {
		const val MANAGEMENT = "management"
	}

	override fun apply(project: Project) {
		val configurations = project.configurations
		project.pluginManager.apply(JavaPlugin::class.java)
		configurations.create(MANAGEMENT) {
			isCanBeResolved = false
			isCanBeConsumed = false
			val plugins = project.plugins
			plugins.withType(JavaPlugin::class.java).configureEach {
				project.extensions.getByType(JavaPluginExtension::class.java)
					.sourceSets.configureEach {
						configurations.named(compileClasspathConfigurationName) {
							extendsFrom(this@create)
						}
						configurations.named(runtimeClasspathConfigurationName) {
							extendsFrom(this@create)
						}
						configurations.named(annotationProcessorConfigurationName) {
							extendsFrom(this@create)
						}
					}
			}
			plugins.withType(JavaTestFixturesPlugin::class.java).configureEach {
				configurations.named("testFixturesCompileClasspath") {
					extendsFrom(this@create)
				}
				configurations.named("testFixturesRuntimeClasspath") {
					extendsFrom(this@create)
				}
			}
			plugins.withType(MavenPublishPlugin::class.java) {
				project.extensions.getByType(PublishingExtension::class.java).publications
					.withType(MavenPublication::class.java).configureEach {
						versionMapping {
							allVariants { fromResolutionResult() }
						}
					}
			}
		}
	}
}
