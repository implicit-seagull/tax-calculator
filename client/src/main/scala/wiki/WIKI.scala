package wiki

import example.TextAreaStats.Model
import org.scalajs.dom
import org.scalajs.dom.html.{ Element, Pre }
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

import scalatags.JsDom.all._

object WIKI {

  object wikiservice {
    object keys {
      val hello = "hello"
      val IR35 = "IR35"
      val marriageAllowance = "marriageAllowance"
      val pensionRelief = "pension"
      val NI = "NI"
      val maintenanceRelief = "IncomeTax"
    }

    val keystore = Map[String, String](
      keys.hello -> "Hello is a greeting word used to say you know hi but in slighty longer way ...",
      keys.maintenanceRelief -> " If you are providing maintenance payments to an ex-spouse or former civil partner or for children under 21, you can claim back 10% of the maintenance payments up to £326",
      keys.IR35 -> "Pay 25% more tax if you’re inside IR35",
      keys.marriageAllowance -> "Allows you to transfer up to £1,150 from your tax allowance to your spouse if their earnings are higher than yours. Reduces their tax by £230 pounds for that year.   ",
      keys.pensionRelief -> "20% added to your pension pot after your employer claims the relief. This is only if they take pension contributions before tax.  (Only applies to tax rates of above 20%) At 40% tax you get 20% relief. At 45% tax you get 25% relief. "
    )

  }

  val info = Var("")
  val condition = Rx { info() == "" }

  def infoItem(textToBeDisplayed: String, wikiKey: String) = {

    val tag = label(
      id := wikiKey,
      textToBeDisplayed
    ).render

    tag.onmouseover = (x: Any) => {
      info() = wikiservice.keystore(wikiKey)
    }

    tag.onmouseleave = (x: Any) => {
      info() = ""
    }

    tag
  }

  //  span(id := thi, `class` := "glyphicon glyphicon-info-sign"),

  val content = p(
    infoItem("Hello", wikiservice.keys.hello), " you are paying taxs"
  )

  def infoPanel(key: String)(implicit ctx: Ctx.Owner) = Rx {
    if (!condition()) {
      div(
        id := key,
        span(`class` := "glyphicon glyphicon-info-sign"),
        label(info)
      )
    } else {
      div(
        id := key,
        label(info)
      )
    }
  }

  def main(thi: String, element: Element)(implicit ctx: Ctx.Owner) =
    div(
      id := thi,
      element,
      infoPanel(thi)
    )

  implicit lazy val ctx = Ctx.Owner.safe()
}

object Fields {

  val IR35 = WIKI.main("IR35", label(WIKI.infoItem("IR35", WIKI.wikiservice.keys.IR35), input(`type` := "checkbox")).render).render

}
