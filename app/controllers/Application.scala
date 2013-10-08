package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.iteratee._
import models._
import akka.actor._
import scala.concurrent.duration._
import org.pac4j.play.scala.ScalaController
import org.pac4j.oauth.client.FacebookClient
import org.pac4j.oauth.client.TwitterClient
import org.pac4j.http.client.FormClient
import org.pac4j.http.client.BasicAuthClient
import org.pac4j.cas.client.CasClient
import org.pac4j.http.credentials.SimpleTestUsernamePasswordAuthenticator
import org.pac4j.openid.client.MyOpenIdClient
import org.pac4j.core.client.Clients
import org.pac4j.play.Config

object Application extends ScalaController {

  /**
   * Just display the home page.
   */
  def index = Action { implicit request =>
    val newSession = getOrCreateSessionId(request)
    val urlFacebook = getRedirectionUrl(request, newSession, "FacebookClient", "/?0")
    val profile = getUserProfile(request)
    Ok(views.html.index(profile, urlFacebook)).withSession(newSession)
  }

  /**
   * Display the chat room page.
   */
  def chatRoom(username: Option[String]) = Action { implicit request =>
     val profile = getUserProfile(request)
     println("test" + profile)
     
    username.filterNot(_.isEmpty).map { username =>
      Ok(views.html.chatRoom(profile.getDisplayName()))
    }.getOrElse {
      Redirect(routes.Application.index).flashing(
        "error" -> "Please choose a valid username.")
    }
  }

  /**
   * Handles the chat websocket.
   */
  def chat(username: String) = WebSocket.async[JsValue] { request =>
    ChatRoom.join(username)
  }
}