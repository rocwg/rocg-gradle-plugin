package io.github.rocwg.gradle.plugin.info

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.work.DisableCachingByDefault
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

/**
 * @author livk
 */
@DisableCachingByDefault(because = "提取资源操作非常快，不需要占用额外的缓存空间")
abstract class ExtractResources : DefaultTask() {

	@Input
	abstract fun getResourceNames(): ListProperty<String>

	@OutputDirectory
	abstract fun getDestinationDirectory(): DirectoryProperty

	@TaskAction
	fun extractResources() {
		for (resourceName in getResourceNames().get()) {
			javaClass.classLoader.resources(resourceName).forEach { url ->
				if (url.path.contains("buildSrc")) {
					url.openStream().use {
						copy(it, FileOutputStream(getDestinationDirectory().file(resourceName).get().asFile))
					}
				}
			}
		}
	}

	fun copy(input: InputStream, out: OutputStream): Int {
		input.use {
			out.use {
				val count = input.transferTo(out).toInt()
				out.flush()
				return count
			}
		}
	}
}
