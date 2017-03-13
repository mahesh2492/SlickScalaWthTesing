package com.example.Connection

import slick.driver.JdbcProfile

trait DbProvider {

  val driver: JdbcProfile

  import driver.api._

  val db: Database

}