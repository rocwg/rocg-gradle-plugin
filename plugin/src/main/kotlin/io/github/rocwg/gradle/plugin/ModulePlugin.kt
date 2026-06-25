package io.github.rocwg.gradle.plugin

import io.github.rocwg.gradle.plugin.compile.CompileArgsPlugin
import io.github.rocwg.gradle.plugin.info.ExtractResources
import io.spring.javaformat.gradle.SpringJavaFormatPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Jar

/**
 * @author livk
 */
class ModulePlugin : Plugin<Project> {

	override fun apply(project: Project) {
		project.pluginManager.apply(CompileArgsPlugin::class.java)
		project.pluginManager.apply(CorePlugin::class.java)
		project.pluginManager.apply(SpringJavaFormatPlugin::class.java)

		project.tasks.register("checkstyle") {
			group = "other"
			dependsOn("checkstyleMain", "checkstyleTest", "checkFormat")
		}

		val extractResourcesProvider = project.tasks.register("extractLegalResources", ExtractResources::class.java) {
			getDestinationDirectory().set(project.layout.buildDirectory.dir("legal"))
			getResourceNames().add("LICENSE.txt")
		}

		project.tasks.withType(Jar::class.java).configureEach {
			dependsOn(extractResourcesProvider)
			metaInf {
				from(extractResourcesProvider)
			}
		}
	}
}
