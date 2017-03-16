package com.example.Components

import com.example.Connection.{DbProvider, MySqlComponent, PostgresComponent}
import com.example.Models.Dependent
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}



trait DependentTable extends EmployeeTable   {

  this: DbProvider =>

  import driver.api._

  class DependentTable(tag: Tag) extends Table[Dependent](tag, "employee_dependents"){
    val emp_id = column[Int]("emp_id")
    val name = column[String]("name",O.PrimaryKey)
    val relationship = column[String]("relationship")
    val age = column[Option[Int]]("age")
    def dependentEmployeeFK = foreignKey("dependent_employee_fk",emp_id,employeeTableQuery)(_.id)
    def * =(name,relationship,age,emp_id) <> (Dependent.tupled, Dependent.unapply)

  }

  val dependentTableQuery = TableQuery[DependentTable]


}

trait DependentComponent extends DependentTable{

  this: DbProvider =>


  import driver.api._

   def create = db.run(dependentTableQuery.schema.create)

   def insert(dep: Dependent) = db.run {
    dependentTableQuery += dep
   }

  def delete(relationStatus: String ) {
    val query = dependentTableQuery.filter((x => x.relationship === relationStatus)).delete
    db.run(query)
  }


  def update(id: Int,relationshipStatus: String) = {
    val query = dependentTableQuery.filter(x => x.emp_id === id).map(_.relationship).update(relationshipStatus)
    db.run(query)
  }

  def getAll : Future[List[Dependent]] = db run {
    dependentTableQuery.to[List].result
  }

  def upsert(dep : Dependent) {
    val res: List[Dependent] = Await.result(DependentComponent.getAll,Duration.Inf)
    val flag = res.map(x => if(x.name == dep.name) true else false)
    if(flag.contains(true)){
      val query = dependentTableQuery.filter(_.name === dep.name).map(x => (x.relationship,x.age)).update((dep.relationship,dep.age))
      db.run(query)
    }
    else{
      db run{
        dependentTableQuery += dep
      }
    }

  }
  def sortByDependentName() =  {
    dependentTableQuery.sortBy(_.name)
  }



}
object DependentComponent extends DependentComponent with MySqlComponent