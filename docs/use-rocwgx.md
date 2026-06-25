



```shell
在  libs.versions.toml

[versions]
rocwg-plugin = "1.0.3-SNAPSHOT"

[plugins]
rocwg-bom = { id = "io.github.rocwg.bom", version.ref = "rocwg-plugin" }
rocwg-root = { id = "io.github.rocwg.root", version.ref = "rocwg-plugin" }
rocwg-module = { id = "io.github.rocwg.module", version.ref = "rocwg-plugin" }
rocwg-common = { id = "io.github.rocwg.common", version.ref = "rocwg-plugin" }
rocwg-service = { id = "io.github.rocwg.service", version.ref = "rocwg-plugin" }
rocwg-deployed = { id = "io.github.rocwg.mvn.deployed", version.ref = "rocwg-plugin" }
rocwg-jacoco = { id = "io.github.rocwg.jacoco", version.ref = "rocwg-plugin" }

使用这个插件的应用项目的 根目录 settings.gradle.kts
pluginManagement {
	plugins {
		alias(libs.plugins.rocwg.bom)
		alias(libs.plugins.rocwg.root)
		alias(libs.plugins.rocwg.module)
		alias(libs.plugins.rocwg.common)
		alias(libs.plugins.rocwg.deployed)
		alias(libs.plugins.rocwg.jacoco)
		alias(libs.plugins.rocwg.service)
	}


CONFIGURE SUCCESSFUL in 27ms
e: file:///D:/roc-github/rocwg-spring-extension/settings.gradle.kts:3:9: Unresolved reference 'libs'.
e: file:///D:/roc-github/rocwg-spring-extension/settings.gradle.kts:3:14: 'fun Project.plugins(block: PluginDependenciesSpec.() -> Unit): Nothing' is deprecated. The plugins {} block must not be used here. If you need to apply a plugin imperatively, please use apply<PluginType>() or apply(plugin = "id") instead.
e: file:///D:/roc-github/rocwg-spring-extension/settings.gradle.kts:3:22: Unresolved reference 'rocwg'.
e: file:///D:/roc-github/rocwg-spring-extension/settings.gradle.kts:4:3: None of the following candidates is applicable:

fun alias(notation: Provider<PluginDependency>): PluginDependencySpec:
  Argument type mismatch: actual type is 'File', but 'Provider<PluginDependency>' was expected.
```

