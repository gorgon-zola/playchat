package controllers

import play.api._
import play.api.mvc._
import org.pac4j.play._
import org.pac4j.core.client._
import org.pac4j.cas.client._
import org.pac4j.oauth.client._
import org.pac4j.http.client._
import org.pac4j.openid.client._
import org.pac4j.http.credentials._
import play.api.mvc.Results._

object Global extends GlobalSettings {

  override def onError(request: RequestHeader, t: Throwable): Result = {
    InternalServerError(
      views.html.error500.render()
    )
  }

  override def onStart(app: Application) {
    val hostName = Play.current.configuration.getString("application.baseUrl").getOrElse("invalid")// current ("application.baseUrl")
    
    Config.setErrorPage401(views.html.error401.render().toString())
    Config.setErrorPage403(views.html.error403.render().toString())
    Config.setDefaultSuccessUrl("http://"+hostName+"/room")
        
    // OAuth
    val facebookClient = new FacebookClient("636317399733107", "1ebbeda616516c18b846d383ef56e321")
    val googleClient = new Google2Client("611064916903.apps.googleusercontent.com", "VM-HK4WfJVMVysldOF4mMgRn")
    
    // DEFAULT_SCOPE =  "user_likes,user_about_me,user_birthday,user_education_history,email,user_hometown,user_relationship_details,user_location,user_religion_politics,user_relationships,user_website,user_work_history";
    facebookClient.setScope("user_about_me,publish_actions")
        
    val twitterClient = new TwitterClient("HVSQGAw2XmiwcKOTvZFbQ", "FSiO9G9VRR4KCuksky0kgGuo8gAVndYymr4Nl7qc8AA")
    
    // HTTP
    val formClient = new FormClient("http://"+hostName+"/theForm", new SimpleTestUsernamePasswordAuthenticator())
    val basicAuthClient = new BasicAuthClient(new SimpleTestUsernamePasswordAuthenticator())
        
    // CAS
    val casClient = new CasClient()
    //casClient.setGateway(true)
    //casClient.setLogoutHandler(new PlayLogoutHandler())
    casClient.setCasLoginUrl("http://"+hostName+"/cas/login")

	// OpenID
	val myOpenIdClient = new MyOpenIdClient()
    
    
    val clients = new Clients("http://"+hostName+"/callback", facebookClient, twitterClient, formClient, basicAuthClient, casClient, myOpenIdClient, googleClient)
    Config.setClients(clients)
    // for test purposes : profile timeout = 60 seconds
    // Config.setProfileTimeout(60)
  }  
}