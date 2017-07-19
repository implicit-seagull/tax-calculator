package main

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
    val selectedMenu = Var(pageIds.landingPage)
    val page = Rx {
      val s: String = selectedMenu()
      pageContents(s)
    }
  }

  object pageIds {
    val landingPage = "About this program"
    val taxCalc = "Tax calculator"
    val vatCalc = "VAT Calculator"
    val all = List(landingPage, taxCalc, vatCalc)
  }

  lazy val pageContents = Map(
     pageIds.landingPage -> LandingPage.main,
     pageIds.taxCalc -> example.ScalaJSExample.main,
     pageIds.vatCalc -> example.ScalaJSExample2.main
  )

  lazy val navigation = Rx {
    div(
      id := "navigation",
      pageIds.all.map { pageId =>
        val menuItem = if ( pageId == model.selectedMenu()) {
          div(cls := "btn-group", style := "width:100%", button(
            style:= "width:98%", pageId)
          ).render
        }
        else {
          div(cls := "btn-group button", style := "width:100%", button(
            style := "width:98%", pageId)
          ).render
        }

        menuItem.onclick = (_: Any) => {
          model.selectedMenu() = pageId
        }

        ul(
          menuItem
        )
      }
    )
  }

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
