package io.github.rocwg.gradle.plugin.maven

import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.MavenPublishPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author livk
 */
abstract class MavenPortalPublishPlugin : Plugin<Project> {

	override fun apply(project: Project) {
		project.pluginManager.apply(MavenPublishPlugin::class.java)
		val group = project.group.toString()
		project.extensions.getByType(MavenPublishBaseExtension::class.java).run {
			publishToMavenCentral()
			signAllPublications()
			coordinates(group, project.name, project.version.toString())

			pom {
				project.afterEvaluate {
					this@pom.name.set(project.name)
					this@pom.description.set(project.description)
				}
				url.set("https://github.com/livk-cloud/${project.rootProject.name}")
				licenses {
					license {
						name.set("The Apache License, Version 2.0")
						url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
					}
				}
				developers {
					developer {
						name.set("livk")
						email.set("livk.cloud@gmail.com")
					}
				}
				scm {
					connection.set("git@github.com:livk-cloud/${project.rootProject.name}.git")
					url.set("https://github.com/livk-cloud/${project.rootProject.name}/")
				}
			}
		}
	}
}
