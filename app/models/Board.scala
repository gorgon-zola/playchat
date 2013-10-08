package models

import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

case class Board(id: Pk[Long] = NotAssigned, name: String, comment: String)

object Board {
  val simple = {
	get[Pk[Long]]("id") ~
	get[String]("name") ~
	get[String]("comment") map {
	  case id~name~comment => Board(id, name, comment)
	}
  }
  
	def last5 = {
	  DB.withConnection { implicit conn =>
	  	SQL(
	  	    """
	  	    SELECT * 
	  	    FROM Board
	  	    LIMIT 5
	  	    """
	  	).as(Board.simple *)
	  }
	}
}