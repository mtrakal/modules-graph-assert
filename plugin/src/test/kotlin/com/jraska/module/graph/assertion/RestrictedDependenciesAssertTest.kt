package com.jraska.module.graph.assertion

import com.jraska.module.graph.DependencyGraph
import org.gradle.api.GradleException
import org.junit.Test

class RestrictedDependenciesAssertTest {
  @Test
  fun passesWithNoMatchingMatchers() {
    val dependencyGraph = testGraph()

    RestrictedDependenciesAssert(emptySet()).assert(dependencyGraph)
  }

  @Test(expected = GradleException::class)
  fun failsWhenFeatureCannotDependOnLib() {
    val dependencyGraph = testGraph()

    RestrictedDependenciesAssert(setOf("feature -X> lib2")).assert(dependencyGraph)
  }

  @Test(expected = GradleException::class)
  fun failsWhenLibCannotDependOnAndroid() {
    val dependencyGraph = testGraph()

    RestrictedDependenciesAssert(setOf("lib[0-9]* -X> [a-z]*-android")).assert(dependencyGraph)
  }

  @Test
  fun passesWithNoMatchersToAlias() {
    val dependencyGraph =
      DependencyGraph.create(
        "app" to "feature",
        "app" to "feature2",
        "feature2" to "core",
        "feature" to "core",
      )

    val aliases =
      mapOf(
        "app" to "App",
        "feature2" to "Impl",
        "feature" to "Impl",
        "core" to "Api",
      )

    RestrictedDependenciesAssert(
      setOf("Api -X> Impl", "Impl -X> Impl"),
      aliases,
    ).assert(dependencyGraph)
  }

  @Test(expected = GradleException::class)
  fun failsWithMatchersToAlias() {
    val dependencyGraph =
      DependencyGraph.create(
        "app" to "feature",
        "app" to "feature2",
        "feature2" to "core",
        "feature" to "core",
        "feature" to "feature2",
      )

    val aliases =
      mapOf(
        "app" to "App",
        "feature2" to "Impl",
        "feature" to "Impl",
        "core" to "Api",
      )

    RestrictedDependenciesAssert(
      setOf("Api -X> Impl", "Impl -X> Impl"),
      aliases,
    ).assert(dependencyGraph)
  }

  private fun testGraph(): DependencyGraph =
    DependencyGraph.create(
      "app" to "feature",
      "app" to "feature2",
      "app" to "lib",
      "feature" to "lib",
      "feature" to "lib2",
      "feature" to "feature2",
      "lib" to "core",
      "lib" to "core-android",
      "lib2" to "core-android",
      "core-android" to "core",
    )
}
