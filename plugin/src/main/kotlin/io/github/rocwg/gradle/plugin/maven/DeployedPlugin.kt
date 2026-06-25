package io.github.rocwg.gradle.plugin.maven

import io.github.rocwg.gradle.plugin.aliyun.AliyunPortalPublishPlugin
import io.github.rocwg.gradle.plugin.aliyun.AliyunRepositoryPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.plugins.signing.SigningExtension
import org.gradle.plugins.signing.SigningPlugin

/**
 * @author livk
 */
abstract class DeployedPlugin : Plugin<Project> {

	override fun apply(project: Project) {
		val logger = project.logger
		val publishTarget = getPublishTarget(project)
		logger.lifecycle("[Build Logic] 当前模块 [${project.name}] 检测到发布目标模式为: $publishTarget")

		when (publishTarget.lowercase()) {
			"central", "portal" -> {
				logger.lifecycle("[Build Logic] 正在为您加载中央仓发布流（启用 GPG 与 MavenPortal 插件）...")
				applyCentral(project)
			}

			"local", "aliyun" -> {
				logger.lifecycle("[Build Logic] 正在为您加载内网私库发布流（仅启用自定义 aliyun 发布插件）.")
				applyAliyun(project)
			}

			else -> {
				throw IllegalArgumentException(
					"⚠️ [Build Logic] 无法识别的发布目标 '$publishTarget'！可选值为: 'local' (内网私库) 或 'central' (中央仓)"
				)
			}
		}
	}

	/**
	 * 模式 A：全球中央仓公开发布模式（激活 GPG 签名与门户发布插件）
	 */
	private fun applyCentral(project: Project) {
		project.pluginManager.apply(SigningPlugin::class.java)
		project.pluginManager.apply(MavenRepositoryPlugin::class.java)
		project.pluginManager.apply(MavenPortalPublishPlugin::class.java)

		project.extensions.configure(SigningExtension::class.java) {
			useGpgCmd()
		}
	}

	/**
	 * 模式 B：公司内部私库模式（只走你最清爽的动态阿里云通道）
	 */
	private fun applyAliyun(project: Project) {
		project.pluginManager.apply(AliyunRepositoryPlugin::class.java)
		project.pluginManager.apply(AliyunPortalPublishPlugin::class.java)
	}

	// 获取发布目标配置（优先读环境变量，其次读 gradle.properties，默认是内网私库 'local'）
	private fun getPublishTarget(project: Project): String {
		return project.providers.environmentVariable("PUBLISH_TARGET").orNull
			?: project.providers.gradleProperty("publish.target").orNull
			?: "local" // 默认为公司内网私库
	}
}
