package example

import example.TextAreaStats.Model
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

object TextAreaStats {

  lazy val render: dom.Element = {
    Model.words.map(x => dom.console.log(s"words changed: $x"))
    View.mainDiv
  }

  private object Model {
    val words = Var("") //let's create reactive variable. This will be our mode
    val charactersCount: Rx[Int] = words.map(_.length)
    val wordsCount: Rx[Int] = words.map(_.split(' ').count(_.nonEmpty))
  }

  private object View {
    lazy val wordbox: dom.html.TextArea = {
      val ta = textarea().render //textArea must be rendered first in order to use dom._ API
      ta.onkeyup =
        (x: Any) => {
          dom.console.log("wordbox on keyup ...")
          Model.words() = ta.value //note here we're replacing default onkeyup callback
        }
      ta
    }

    lazy val chars: Rx[Int] = Model.charactersCount
    lazy val words = pre(Model.wordsCount.map(_.toString()))

    class Color(jsName: String, cssName: String) extends Style(jsName, cssName)

    object Color extends Color("color", "color") {
      lazy val A = this := "#382865"
      lazy val B = this := "#FF2865"
      lazy val C: StylePair[dom.Element, String] = this := "#38CC65"
      val colors = List("red", "green", "blue", "cyan", "brown")

      val D: StylePair[dom.Element, Dynamic[String]] = this := Rx {
        val index = chars() % colors.length
        colors(index)
      }
    }

    //    def iPre[T](s: T)(implicit ev$1: T => Frag): TypedTag[Pre] = pre(s, style := "display: inline")
    def iPre[T](s: T)(implicit ev$1: T => Frag): TypedTag[Pre] = pre(s, display.inline, boxShadow := "0 1px 1px 0")

    div(
      input(),
      input()
    )

    lazy val mainDiv = div(
      wordbox,
      ul(
        li(span("characters:", raw("&nbsp;"), iPre(chars).apply(color := chars.map(x => if (x == 0) (color.red).v else "inherit")))),
        li(span("characters:", raw("&nbsp;"), iPre(chars).apply(Color.A))),
        li(span("characters:", raw("&nbsp;"), iPre(chars).apply(Color.D))),
        li(span("words:", words)),
        li(div(
          SimpleSS.x,
          "The div"
        ))
      ),
      JsDom.tags2.style(SimpleSS.styleSheetText)

    ).render

  }

}

object SimpleSS extends StyleSheet {
  val x: Cls = cls(
    backgroundColor := "red",
    height := 125
  )
  val y = cls.hover(
    opacity := 0.5
  )

  val z = cls(x.splice, y.splice)
  initStyleSheet()
}
