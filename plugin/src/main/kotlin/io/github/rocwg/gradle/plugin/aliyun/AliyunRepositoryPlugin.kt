package io.github.rocwg.gradle.plugin.aliyun

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin
import java.net.URI

/**
 * @author rocwg
 * 挂载基础阿里云仓设置（只负责配仓库地址和密码）
 */
abstract class AliyunRepositoryPlugin : Plugin<Project> {

	override fun apply(project: Project) {
		project.pluginManager.apply(MavenPublishPlugin::class.java)

		project.extensions.getByType(PublishingExtension::class.java).run {
			repositories.mavenLocal()
			val targetUrl = resolveTargetUrl(project)
			if (!targetUrl.isNullOrBlank()) {
				repositories.maven {
					name = resolveRepoName(project)
					url = URI.create(targetUrl)
					credentials {
						username = resolveUsername(project)
						password = resolvePassword(project)
					}
				}
			} else {
				project.logger.warn("⚠️ [Aliyun Build] 未检测到阿里云效 URL 配置，发布任务将失效！")
			}
		}
	}

	/**
	 * 内部私有方法：智能动态解析出最终的私库 URL（安全读取，不抛未知属性异常）
	 */
	private fun resolveTargetUrl(project: Project): String? {
		val providers = project.providers
		val snapshotUrl = providers.environmentVariable("ALIYUN_MAVEN_SNAPSHOT_URL").orNull
			?: providers.gradleProperty("aliyun_maven_snapshot_url").orNull
		val releaseUrl = providers.environmentVariable("ALIYUN_MAVEN_RELEASE_URL").orNull
			?: providers.gradleProperty("aliyun_maven_release_url").orNull

		val version = project.version.toString()
		project.logger.lifecycle("[Aliyun Build] 版本: $version")

		val targetUrl = if (version.endsWith("-SNAPSHOT")) snapshotUrl else releaseUrl
		project.logger.lifecycle("[Aliyun Build] 仓库: $targetUrl")
		return targetUrl
	}

	//解析环境变量与 gradle.properties
	private fun resolveUsername(project: Project): String? {
		val providers = project.providers
		return providers.environmentVariable("ALIYUN_MAVEN_USERNAME").orNull
			?: providers.gradleProperty("aliyun_maven_username").orNull
	}

	private fun resolvePassword(project: Project): String? {
		val providers = project.providers
		return providers.environmentVariable("ALIYUN_MAVEN_PASSWORD").orNull
			?: providers.gradleProperty("aliyun_maven_password").orNull
	}

	private fun resolveRepoName(project: Project): String {
		val providers = project.providers
		return providers.environmentVariable("ALIYUN_MAVEN_REPO_NAME").orNull
			?: providers.gradleProperty("aliyun_maven_repo_name").orNull
			?: "aliyun"
	}
}
