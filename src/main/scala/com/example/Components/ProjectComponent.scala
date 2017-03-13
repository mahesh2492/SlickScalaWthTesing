package com.example.Components

import com.example.Connection.{MySqlComponent, PostgresComponent, DbProvider}
import com.example.Models.Project

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}


trait ProjectTable extends EmployeeTable with  MySqlComponent{
  this: DbProvider =>

  import driver.api._

  class ProjectTable(tag: Tag) extends Table[Project](tag, "project") {
    val id = column[Int]("id")

    def employeeProjectFK = foreignKey("employee_project_fk",id,employeeTableQuery)(_.id)

    val pname = column[String]("pname",O.PrimaryKey)

    val team_members = column[Int]("team_members")

    val lead = column[String]("lead")

    def * = (id,pname,team_members,lead) <> (Project.tupled, Project.unapply)

  }
  val projectTableQuery = TableQuery[ProjectTable]
}

object ProjectComponent extends ProjectTable {
  this: DbProvider =>

  import driver.api._

  def create = db.run(projectTableQuery.schema.create)


  def insert(prj: Project) = db.run {
    projectTableQuery += prj
  }

  def delete(pname: String) = {
    val query = projectTableQuery.filter(x => x.pname === pname)
    val action = query.delete
    db.run(action)
  }

  def updateName(id:Int,name:String):Future[Int] = {
    val query = projectTableQuery.filter(_.id === id).map(_.pname).update(name)
    db.run(query)
  }

  def getAll: Future[List[Project]] = { db.run { projectTableQuery.to[List].result}}

  def upsert(emp : Project) = {
    val data: List[Project] = Await.result(getAll,Duration.Inf)

    val flag: List[Boolean] = data.map(x => if(x.pname == emp.pname) true else false)

    if(flag.contains(true))
    {


      val action = projectTableQuery.filter(_.pname === emp.pname).map(x => (x.team_members,x.lead)).update((emp.team_members,emp.lead))
      db.run(action)
    }
    else {
      val action: Future[Int] = insert(emp)

    }


  }
  def sortByProjectName() =  {
    projectTableQuery.sortBy(_.pname)
  }
}


