package com.example.Connection

import slick.driver.MySQLDriver

trait MySqlComponent extends DbProvider {

  val driver = MySQLDriver

  import driver.api._

  val db = Database.forConfig("mysqlDB")

}

