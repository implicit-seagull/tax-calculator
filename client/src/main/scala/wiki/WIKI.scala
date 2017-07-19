package wiki

import example.TextAreaStats.Model
import org.scalajs.dom
import org.scalajs.dom.html.{ Element, Pre }
import org.scalajs.dom.raw.KeyboardEvent
import rx.Ctx.Owner
import rx.Ctx.Data
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

class WIKI(implicit c: Ctx.Owner, v: Ctx.Data) {

  object wikiservice {
    object keys {
      val hello = "hello"
      val IR35 = "IR35"
      val marriageAllowance = "marriageAllowance"
      val pensionRelief = "pension"
      val NI = "NI"
      val incomeTax = "IncomeTax"
    }

    val keystore = Map[String, String](
      keys.hello -> "Hello is a greeting word used to say you know hi but in slighty longer way ...",
      keys.incomeTax -> """Standard personal (tax free) allowance of £11,500 per year,
          |Standard tax rate- 20% on all incomes between £11,501 and £45,000,
          |Higher rate - 40% on all incomes between £45,001 and £150,000,
          | Additional rate - 45% on all incomes over £150,000 per year,
          |You have No personal tax allowance on incomes over £123,000 per year
          |
        """.stripMargin,
      keys.IR35 -> "Pay 25% more tax if you’re inside IR35"
    )

  }

  val info = Var("")
  val xxx = Rx { info() == "" }

  def infoItem(textToBeDisplayed: String, wikiKey: String): Element = {

    val tag = em(
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

  def infoPanel(key: String)(implicit c: Ctx.Owner, v: Ctx.Data) =
    if (xxx()) {
      div(
        span(id := key, `class` := "glyphicon glyphicon-info-sign"),
        label(info)
      )
    } else {
      div(
        label(info)
      )
    }

  def main(thi: String, element: Element) =
    div(
      element,
      infoPanel(thi)
    )

}
