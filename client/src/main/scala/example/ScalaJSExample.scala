package example

import org.scalajs.dom
import org.scalajs.dom.html.{ Input, Pre }
import org.scalajs.dom.raw.KeyboardEvent
import rx.Ctx.Owner
import rx.Rx.Dynamic
import rx._
import shared.SharedMessages

import scala.scalajs.js
import scalatags.JsDom
import scalatags.JsDom.TypedTag
import scalatags.JsDom.all.{ p, _ }
import scalatags.stylesheet.{ Cls, StyleSheet }
import framework.Framework._
import rx._
import wiki.WIKI

import scalatags.JsDom.all._

object ScalaJSExample {

  object model {
    val moneyInputField = Var(0.0)
    val expencesInputField = Var(0.0)
    val ir35CheckBoxIn = Var(false)
    val marriageCheckBoxIn = Var(false)
    val pensionReliefCheckboxIn = Var(false)
    val maintenanceReliefeChecboxIn = Var(false)
    val charityReliefeChecboxIn = Var(false)

    val tax0FlatRate = Rx {if(ir35CheckBoxIn()) 0.2 + 0.2 else 0.2}
    val tax0 = Rx {moneyInputField() * tax0FlatRate() }
    val takeHome0 = Rx {moneyInputField() - tax0()}


    val stage1 = Rx {
      //some computations
      var taxAfterI = moneyInputField() * 0.8
      if (ir35CheckBoxIn()) {
        moneyInputField() * 0.8 * 0.8
      } else {
        moneyInputField() * 0.8
      }
    }

    val stage2 = Rx {
      if (marriageCheckBoxIn()) {
        stage1() + 231.0
      } else stage1()
    }

    val stage3 = Rx {
      if (pensionReliefCheckboxIn()) {
        stage2() * 1.1
      } else stage2()
    }

    val stage4 = Rx {
      if (maintenanceReliefeChecboxIn()) {
        stage3() + 320
      } else stage3()
    }

    val stage5 = Rx {
      if (charityReliefeChecboxIn()) {
        stage4() + 68
      } else stage4()
    }

    val takeHomeMoney = Rx {
      stage5() + expencesInputField()
    }

  }

  object view {

    val questionMark = span(cls := "glyphicon glyphicon-question-sign")

    val ir35InputField: Input = {
      val i = input(`type` := "checkbox").render
      i.onchange = (x: Any) => model.ir35CheckBoxIn() = i.checked
      i
    }

    val marriageInputField: Input = {
      val i = input(`type` := "checkbox").render
      i.onchange = (x: Any) => model.marriageCheckBoxIn() = i.checked
      i
    }

    val pensionReliefInputField: Input = {
      val i = input(`type` := "checkbox").render
      i.onchange = (x: Any) => model.pensionReliefCheckboxIn() = i.checked
      i
    }

    val maintenanceInputField: Input = {
      val i = input(`type` := "checkbox").render
      i.onchange = (x: Any) => model.maintenanceReliefeChecboxIn() = i.checked
      i
    }

    val charityReliefInputField: Input = {
      val i = input(`type` := "checkbox").render
      i.onchange = (x: Any) => model.charityReliefeChecboxIn() = i.checked
      i
    }
    val expensivesInputField: Input = {
      val i = input(placeholder := "put money here").render
      i.onkeyup = (x: Any) => model.expencesInputField() = i.value.toDouble
      i
    }

    val calc = div(cls := "text-center",
      h1("Tax Calculator"),
      p(
        cls := "group1",
        h4("Income/Earnings"),
        div(
          {
            val i = input(placeholder := "put money here", cls := "the-input").render
            i.onkeyup = (x: Any) => model.moneyInputField() = i.value.toDouble
            i
          },
          span(cls := "label-for-input", "put money here"),
          questionMark
        ),
        WIKI.main("IR35", label(WIKI.infoItem("IR35", WIKI.wikiservice.keys.IR35), ir35InputField).render),
        div(
          input(readonly, value := model.tax0FlatRate.map(x => f"$x%1.2f"), cls := "output"),
          span(cls := "label-for-input", "Your tax rate"), questionMark
        ),
        div(
          input(readonly, value := model.tax0.map(x => f"$x%1.2f"), cls := "output"),
          span(cls := "label-for-input", "Your initial tax"), questionMark
        ),
        div(
          input(readonly, value := model.takeHome0.map(x => f"$x%1.2f"), cls := "output"),
          span(cls := "label-for-input", "Your initial take home "), questionMark
        )
      ),
      //TODO NI


      p("put some more info about your profile:"),
      span(
        style := "display: inline",
        WIKI.main("Marriage allowance", label(WIKI.infoItem("Marriage allowance", WIKI.wikiservice.keys.marriageAllowance), marriageInputField).render),
        WIKI.main("Pension Relief", label(WIKI.infoItem("Pension Relief", WIKI.wikiservice.keys.pensionRelief), pensionReliefInputField).render),
        WIKI.main("Maintenance Relief", label(WIKI.infoItem("Maintenance relief", WIKI.wikiservice.keys.maintenanceRelief), maintenanceInputField).render),
        WIKI.main("Charity Relief", label(WIKI.infoItem("Charity relief", WIKI.wikiservice.keys.charityRelief), charityReliefInputField).render)
      ),
      div(
        //  p("you have to pay taxes:"),
        //  taxToPayOutput,
        span(
          style := "display: inline",
          p(
            WIKI.main("expenses", label(WIKI.infoItem("Self-Employed/Contractor expenses amount", WIKI.wikiservice.keys.expenses), expensivesInputField).render)
          ),
          p(
            WIKI.main("Take Home Pay", label(WIKI.infoItem("Earnings After Tax Deductions", WIKI.wikiservice.keys.takeHomePay), input(readonly, value := model.takeHomeMoney, cls := "output")).render)
          ),
          div(
            cls := "debug-pane",
            hidden, //comment it to hide debug
            p("ir35 = ", model.ir35CheckBoxIn.map(_.toString())),
            p("married = ", model.marriageCheckBoxIn.map(_.toString())),
            p("pension = ", model.pensionReliefCheckboxIn.map(_.toString())),
            p("maintenanceRelief = ", model.maintenanceReliefeChecboxIn.map(_.toString()))
          )
        )
      ),
      div(
        cls := "text-center",
        style := "width: 50%; height: 150%;",
        img(src := "http://www.bbc.co.uk/staticarchive/507fdbb5c9ca6cfe021cabeb0e98b4e4fc3623f1.gif", style := "width: 50%; height: 150%;")
      )
    )
  }

  val main = view.calc.render

  implicit def ctx: Owner = Ctx.Owner.safe()

}
