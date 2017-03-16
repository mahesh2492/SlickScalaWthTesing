package com.example.Components

import com.example.Connection.{MySqlComponent, PostgresComponent, DbProvider}
import com.example.Models.Project

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}


trait ProjectTable extends EmployeeTable {
  this: DbProvider =>

  import driver.api._

  class ProjectTable(tag: Tag) extends Table[Project](tag, "project") {
    val id = column[Int]("id",O.PrimaryKey,O.AutoInc)

    val pname = column[String]("pname")

    val team_members = column[Int]("team_members")

    val lead = column[String]("lead")

    def employeeProjectFK = foreignKey("employee_project_fk",id,employeeTableQuery)(_.id)

    def * = (pname,team_members,lead,id) <> (Project.tupled, Project.unapply)

  }
  val projectTableQuery = TableQuery[ProjectTable]
}

trait ProjectComponent extends ProjectTable {
  this: DbProvider =>

  import driver.api._

  def create = db.run(projectTableQuery.schema.create)


  def insert(proj: Project) = db.run {
    projectTableQuery.returning(projectTableQuery.map(_.id)) += proj
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
//    val data: List[Project] = Await.result(getAll,Duration.Inf)
//
//    val flag: List[Boolean] = data.map(x => if(x.pname == emp.pname) true else false)
//
//    if(flag.contains(true))
//    {
//
//
//      val action = projectTableQuery.filter(_.pname === emp.pname).map(x => (x.team_members,x.lead)).update((emp.team_members,emp.lead))
//      db.run(action)
//    }
//    else {
//      val action: Future[Int] = insert(emp)
//
//    }

    db.run {
      projectTableQuery.insertOrUpdate(emp)
    }
  }
  def sortByProjectName() =  {
    projectTableQuery.sortBy(_.pname)
    true
  }

  def getProjectById(id: Int) =  {
    val query = projectTableQuery.filter(x => x.id === id).to[List].result
    db.run(query)
  }

  def addMultipleProjects(pr1: Project,pr2: Project) =
  {
    val ins1 = projectTableQuery += pr1

    val ins2 = projectTableQuery += pr2
    val res = ins1 andThen ins2
    //res.map(println)
    db.run(res)
  }

  def getProjectsByEmployeeName = {
    val action = {
      for {
        (e, p) <- employeeTableQuery join projectTableQuery on (_.id === _.id)
      } yield (e.name, p.pname)
    }.to[List].result
    db.run(action)
  }

  def insertPlainSql = {
    val action = sqlu"insert into project values('todo list',2,'mahesh',113);"
    db.run(action)

  }

  def teamCount: Future[Option[Int]] = db.run{

    val a = projectTableQuery.map(_.team_members)
    a.max.result
  }

  def insertRecordObj(proj: Project): Future[Project] = {
    val insertQuery = projectTableQuery returning projectTableQuery.map(_.id) into ((item,id) => item.copy(emp_id = id))
    val action = insertQuery += proj
    db.run(action)
  }
//  val userWithId =
//    (users returning users.map(_.id)
//      into ((user,id) => user.copy(id=Some(id)))
//      ) += User(None, "Stefan", "Zeiger")
  //def createToken(user: UserEntity): Future[TokenEntity] = db.run(tokens returning tokens += TokenEntity(userId = user.id))



}


object ProjectComponent extends ProjectComponent with  MySqlComponent