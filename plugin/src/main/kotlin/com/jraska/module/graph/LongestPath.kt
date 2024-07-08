package com.jraska.module.graph

data class LongestPath(
  val nodeNames: List<String>,
) {
  fun pathString(): String = nodeNames.joinToString(" -> ")

  override fun toString() = "'${pathString()}'"
}
