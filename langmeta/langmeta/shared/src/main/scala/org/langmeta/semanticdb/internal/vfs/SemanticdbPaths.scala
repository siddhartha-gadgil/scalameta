package org.langmeta.internal
package semanticdb
package vfs

import org.langmeta.internal.io.PathIO
import org.langmeta.io.RelativePath

object SemanticdbPaths {
  private val semanticdbPrefix = RelativePath("META-INF").resolve("semanticdb")
  private val semanticdbExtension = "semanticdb"
  private val scalaExtension = "scala"

  def isSemanticdb(path: RelativePath): Boolean = {
    path.toNIO.startsWith(semanticdbPrefix.toNIO) &&
    PathIO.extension(path.toNIO) == semanticdbExtension
  }

  def toScala(path: RelativePath): RelativePath = {
    require(isSemanticdb(path))
    val scalaSibling = path.resolveSibling(_.stripSuffix("." + semanticdbExtension))
    semanticdbPrefix.relativize(scalaSibling)
  }

  def isScala(path: RelativePath): Boolean = {
    PathIO.extension(path.toNIO) == scalaExtension
  }

  def fromScala(path: RelativePath): RelativePath = {
    require(isScala(path))
    val semanticdbSibling = path.resolveSibling(_ + "." + semanticdbExtension)
    semanticdbPrefix.resolve(semanticdbSibling)
  }
}
