package wiki

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

import scalatags.JsDom.all._

object WIKI {

  object wikiservice {
    object keys {
      val hello = "hello"
    }

    val keystore = Map[String, String](
      keys.hello -> "Hello is a greeting word used to say you know hi but in slighty longer way ..."
    )

  }

  val info = Var("")

  def infoItem(textToBeDisplayed: String, wikiKey: String) = {

    val tag = em(
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

  val content = p(
    infoItem("Hello", wikiservice.keys.hello), " you are paying taxs"
  )

  val infoPanel = div(
    id := "infoPanel",
    p(info)
  )

  val main = div(
    id := "info demo",
    content,
    infoPanel
  )

}
