package landingpage

import org.scalajs.dom
import org.scalajs.dom.html.Pre
import org.scalajs.dom.raw.KeyboardEvent
import rx.Ctx.Owner
import rx.Rx.Dynamic
import rx._
import shared.SharedMessages

import scala.scalajs.js
import scalatags.JsDom
import scalatags.JsDom.TypedTag
import scalatags.JsDom.all._
import scalatags.generic.StylePair
import scalatags.stylesheet.{ Cls, StyleSheet }
import framework.Framework._
import rx._
import wiki.WIKI

import scalatags.JsDom.all._

object LandingPage {

  implicit def ctx: Owner = Ctx.Owner.safe()

  lazy val main = div(
    cls := "text-center",
    "This is landing page",
    p("paying taxes is very important"),
    p(
      "here you will lean how:",
      ul(
        p("asdfas"),
        p("asdfas"),
        p("asdfas")
      )
    )
  ).render
}
