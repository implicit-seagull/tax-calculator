package main

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
import scalatags.stylesheet.{Cls, StyleSheet}
import framework.Framework._
import landingpage.LandingPage
import rx._
import wiki.WIKI

import scalatags.JsDom.all._

object Main extends js.JSApp {

  def main(): Unit = {
    val main = dom.document.getElementById("main")
    main.innerHTML = ""
    main.appendChild(mainDiv)
  }


  object model {
    val selectedMenu = Var(pageIds.taxCalc)
    val page = Rx {
      val s: String = selectedMenu()
      pageContents(s)
    }
  }

  object pageIds {
    val landingPage = "landing-page"
    val taxCalc = "tax-calculator"
    val wikiExamle = "wiki-example"
    val all = List(landingPage, taxCalc, wikiExamle)
  }

  lazy val pageContents = Map(
    pageIds.landingPage -> LandingPage.main,
    pageIds.taxCalc -> example.ScalaJSExample.main,
    pageIds.wikiExamle -> WIKI.main
  )

  lazy val navigation = div(
    id := "navigation",
    pageIds.all.map { page =>

      val menuItem = li(page).render

      menuItem.onclick = (_: Any) => {
        model.selectedMenu() = page
      }

      ul(
        menuItem
      )
    }
  )



  val contentPage = div(
    id := "contentPage",
    model.page
  )

  val mainDiv = div(
    navigation,
    contentPage
  ).render


  implicit def ctx: Owner = Ctx.Owner.safe()

}
