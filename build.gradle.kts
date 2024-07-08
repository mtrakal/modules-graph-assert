import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version libs.versions.kotlin.get()
  alias(libs.plugins.detekt)
  alias(libs.plugins.ktlint)
}

kotlin {
  jvmToolchain(
    libs.versions.java
      .get()
      .toInt(),
  )
}

java {
  sourceCompatibility = JavaVersion.toVersion(libs.versions.java.get())
  targetCompatibility = JavaVersion.toVersion(libs.versions.java.get())

  toolchain.languageVersion.set(
    JavaLanguageVersion.of(
      libs.versions.java
        .get()
        .toInt(),
    ),
  )
}

tasks.withType<JavaCompile> {
  options.compilerArgs.addAll(
    listOf(
      "-source",
      libs.versions.java.get(),
      "-target",
      libs.versions.java.get(),
    ),
  )
}

tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = libs.versions.java.get()
}

subprojects {
  apply(
    plugin =
      rootProject.libs.plugins.ktlint
        .get()
        .pluginId,
  )
  apply(
    plugin =
      rootProject.libs.plugins.detekt
        .get()
        .pluginId,
  )

  tasks.withType<JavaCompile> {
    options.compilerArgs.addAll(
      listOf(
        "-source",
        libs.versions.java.get(),
        "-target",
        libs.versions.java.get(),
      ),
    )
  }

  configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
    version.set(
      rootProject.libs.versions.ktlint
        .get(),
    )
    verbose.set(true)
    filter {
      exclude("**/generated/**", "**/build/**")
      include("src//kotlin")
    }
  }

  detekt {
    buildUponDefaultConfig = true
    parallel = true
  }
}
