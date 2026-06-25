package io.github.rocwg.gradle.plugin.aliyun

import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.MavenPublishPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author rocwg
 * 挂载阿里云专属的自动化装箱与元数据配置插件
 * 利用三方插件实现普通 Java 项目的【零配置全自动挂载】
 */
abstract class AliyunPortalPublishPlugin : Plugin<Project> {

	override fun apply(project: Project) {
		// 1. 激活三方发布外挂
		project.pluginManager.apply(MavenPublishPlugin::class.java)

		val group = project.group.toString()
		val providers = project.providers

		// 2. 拿到三方插件的扩展进行全自动配置
		project.extensions.getByType(MavenPublishBaseExtension::class.java).run {
			//【重点】删除了 publishToMavenCentral() ➡️ 坚决不去中央仓撞墙
			//【重点】删除了 signAllPublications()   ➡️ 坚决不开启烦人的 GPG 签名

			// 自动化核心：帮你的普通 Java 模块自动配置坐标
			coordinates(group, project.name, project.version.toString())

			// 顺手把你的私服 POM 元数据也做标准化规范
			pom {
				project.afterEvaluate {
					this@pom.name.set(project.name)
					this@pom.description.set(project.description)
				}
				url.set("https://github.com/rocwg/${project.rootProject.name}")
				licenses {
					license {
						name.set("The Apache License, Version 2.0")
						url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
					}
				}
				developers {
					developer {
						name.set("rocwg")
						email.set("rocwg@example.com") // 换成你的邮箱
					}
				}
			}
		}
	}
}
