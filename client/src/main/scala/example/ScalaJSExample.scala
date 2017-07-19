package example

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

object ScalaJSExample {

  object logic {

    model.incomeTax.foreach { x =>
      dom.console.log(x)
    }
  }

  object view {

    val meneyInput = input(placeholder := "0").render

    meneyInput.onkeyup = (x: Any) => {
      dom.console.log("wordbox on keyup ...")
      model.in() = meneyInput.value.toDouble

    }
    //

    val earningsAfterInitialTax = input().render
    //todo get values right
    val taxToPayOutput = input(readonly, value := model.incomeTax)
    val corporationTax = input(readonly, value := model.incomeTax)
    val nationalInsurance = input(readonly, value := model.incomeTax)
    val calc = div(
      p("Income/Earnings"),
      meneyInput,
      p("Earnings after income tax deductions and NI"),
      earningsAfterInitialTax,
      p(""),
      span(
        style := "display: inline",
        WIKI.main("IR35", label(WIKI.infoItem("IR35", WIKI.wikiservice.keys.IR35), input(`type` := "checkbox")).render),
        WIKI.main("Marriage allowance", label(WIKI.infoItem("Marriage allowance", WIKI.wikiservice.keys.marriageAllowance), input(`type` := "checkbox")).render),
        WIKI.main("Pension Relief", label(WIKI.infoItem("Pension Relief", WIKI.wikiservice.keys.pensionRelief), input(`type` := "checkbox")).render),
        WIKI.main("Maintenance Relief", label(WIKI.infoItem("Maintenance relief", WIKI.wikiservice.keys.maintenanceRelief), input(`type` := "checkbox")).render)
      ),
      div(
        //  p("you have to pay taxes:"),
        //  taxToPayOutput,
        span(
          style := "display: inline",
          h1(
            "Self-Employed/Contractor expenses amount",
            corporationTax
          ),
          h2(
            "Earnings After Tax Deductions",
            nationalInsurance
          )
        )
      )
    )

  }

  object model {
    val in = Var(0.0)
    val tax = 0.80
    val incomeTax = Rx {
      in() * tax
    }
  }

  val main = view.calc.render

  implicit def ctx: Owner = Ctx.Owner.safe()

}
