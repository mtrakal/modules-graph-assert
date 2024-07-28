plugins {
  alias(libs.plugins.pluginPublish)
  `java-gradle-plugin`
}

apply(plugin = "kotlin")

dependencies {
  implementation(gradleApi())
  implementation(libs.kotlin.stdlib)

  implementation(libs.junit)
}

group = "cz.mtrakal.module.graph.assertion"

gradlePlugin {
  website = "https://github.com/mtrakal/modules-graph-assert"
  vcsUrl = "https://github.com/mtrakal/modules-graph-assert"

  plugins {
    create("modulesGraphAssert") {
      id = "cz.mtrakal.module.graph.assertion"
      version = "3.0.1"
      displayName = "Modules Graph Assert Mermaid"
      description = "Gradle plugin to keep your modules graph healthy and lean with Mermaid output."
      implementationClass = "com.jraska.module.graph.assertion.ModuleGraphAssertionsPlugin"
      tags.addAll("graph", "assert", "build speed", "android", "java", "kotlin", "quality", "multiprojects", "module", "mermaid")
    }
  }
}
