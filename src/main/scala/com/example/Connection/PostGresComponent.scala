package com.example.Connection

import slick.driver.PostgresDriver

trait PostgresComponent extends DbProvider {

  val driver = PostgresDriver

  import driver.api._

  val db = Database.forConfig("myPostgresDB")

}

