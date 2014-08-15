import sbt._
import Keys._

object WaleeGaiaSync extends Build {
    lazy val gaiaSync = Project(id = "gaia-sync", base = file(".")) dependsOn(gaiaModel)
    lazy val gaiaModel = Project(id = "gaia-model", base = file("gaia-model"))    
}
