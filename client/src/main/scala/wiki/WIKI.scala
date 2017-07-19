package wiki

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
      val IR35 = "IR35"
      val marriageAllowance = "marriageAllowance"
      val pensionRelief = "pension"
      val NI = "NI"
      val maintenanceRelief = "maintenanceRelief"
      val charityRelief = "charity Relief"
      val incomeTax = "Income Tax"
      val expenses = "Self-Employed/Contractor expenses amount"
      val takeHomePay = "Take Home Pay"
    }

    val keystore = Map[String, String](
      keys.maintenanceRelief -> "Maintenance Relief :  If you are providing maintenance payments to an ex-spouse or former civil partner or for children under 21, you can claim back 10% of the maintenance payments up to £326",
      keys.IR35 -> "Pay 25% more tax if you’re inside IR35",
      keys.marriageAllowance -> "Marriage allowance : Allows you to transfer up to £1,150 from your tax allowance to your spouse if their earnings are higher than yours. Reduces their tax by £230 pounds for that year.   ",
      keys.pensionRelief -> "Pension Relief : 20% added to your pension pot after your employer claims the relief. This is only if they take pension contributions before tax.  (Only applies to tax rates of above 20%) At 40% tax you get 20% relief. At 45% tax you get 25% relief. ",
      keys.charityRelief -> "Charity Relief : tick this if you have donated to charity this year,  this allows you to reclaim the difference between the donated amount and what the charity received in relief. ",
      keys.incomeTax -> "Income Tax : tax paid on your income. The standard tax rate is 20%. This increases to 40% with income over £45,000 pounds and 45% with income over £150,000. ",
      keys.expenses -> "if you are self-employed/contractor, you can claim work related expenses and remove them from your tax bill. This include, but are not limited to travel costs, office, staff salaries and marketing costs. ",
      keys.takeHomePay -> "Take Home Pay"
    )
  }

  val info = Var("")
  val condition: Rx[Boolean] = Rx { info() == "" }
  val condition2: String => Rx[Boolean] = str => Rx { info().contains(str) }

  def infoItem(textToBeDisplayed: String, wikiKey: String) = {

    val tag = label(
      id := wikiKey,
      textToBeDisplayed
    ).render

    tag.onmouseover = (x: dom.Event) => {
      info() = wikiservice.keystore(wikiKey)
    }

    tag.onmouseleave = (x: Any) => {
      info() = ""
    }

    tag
  }

  def infoPanel(key: String)(implicit ctx: Ctx.Owner) = Rx {
    dom.console.log(key)
    if (!condition() && condition2(key)()) {
      div(
        id := key,
        span(`class` := "glyphicon glyphicon-info-sign"),
        label(info)
      )
    } else {
      div(
        id := key,
        label("")
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
