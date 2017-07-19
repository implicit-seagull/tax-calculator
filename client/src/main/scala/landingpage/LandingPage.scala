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
import scalatags.JsDom.all.{p, _}
import scalatags.stylesheet.{Cls, StyleSheet}
import framework.Framework._
import rx._
import wiki.WIKI

import scalatags.JsDom.all._

object LandingPage {

  implicit def ctx: Owner = Ctx.Owner.safe()

  lazy val main = div( cls := "text-center",
    h1("Landing page -"),
      p("Welcome to the Personal Virtual Accountant landing page. From here you can navigate to calculators and metrics outputs regarding potential tax payments/reliefs and take home pay."),
    p(
      ul(
        li("Tax Calculator takes you to a new instance of the real time tax calculator for personal/sole-trader users"),
        li("VAT calculator: takes you to a new instance of the VAT calculator to calculate what VAT is owed on your income from goods sales.")
      )
    )
  ).render
}
