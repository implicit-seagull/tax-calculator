package example

import framework.Framework._
import org.scalajs.dom.html.Input
import rx.Ctx.Owner
import rx._
import wiki.WIKI

import scalatags.JsDom.all._

object ScalaJSExample2 {

  object model {
    val moneyInputField = Var(0.0)
    val tax = 0.80

    val ir35CheckBoxIn = Var(false)
    val marriageCheckBoxIn = Var(false)
    val expensesInput = Var(0.0)

    val stage1 = Rx {
      if (ir35CheckBoxIn()) {
        moneyInputField() * 0.95
      } else if(marriageCheckBoxIn()) {
        moneyInputField() * 0.8
      }
      else {
        moneyInputField()
      }
    }


    val takeHomeMoney =  Rx {
      stage1()
    }

  }

  object view {

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

    val calc = div(
      h1(cls := "text-center", "Vat Calculator"),
      p(
        cls := "group",
        h3("Income from good's sold"),
        {
          val i = input(placeholder := "put money here").render
          i.onkeyup = (x: Any) => model.moneyInputField() = i.value.toDouble
          i
        }
      ),
      span(
        style := "display: inline",
        WIKI.main("5% rate", label(WIKI.infoItem("5% rate", WIKI.wikiservice.keys.IR35), ir35InputField).render),
        WIKI.main("20% rate", label(WIKI.infoItem("20% rate", WIKI.wikiservice.keys.marriageAllowance), marriageInputField).render)
      ),
      div(
        //  p("you have to pay taxes:"),
        //  taxToPayOutput,
        span(
          style := "display: inline",
          p(
            "Income after vat",
            input(readonly, value := model.takeHomeMoney, cls := "output")
          ),

          div(
            cls := "debug-pane",
            //comment it to hide debug
            p("5% rate = ", model.ir35CheckBoxIn.map(_.toString())),
            p("20% rate = ", model.marriageCheckBoxIn.map(_.toString()))
          )
        )
      )
    )

  }

  val main = view.calc.render

  implicit def ctx: Owner = Ctx.Owner.safe()

}
