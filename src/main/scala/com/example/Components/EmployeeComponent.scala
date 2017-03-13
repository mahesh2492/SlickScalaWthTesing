package com.example.Components

import com.example.Connection.{PostgresComponent, MySqlComponent,DbProvider}
import com.example.Models.Employee

import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}


      trait EmployeeTable extends  MySqlComponent{
        this: DbProvider =>

        import driver.api._

        class EmployeeTable(tag: Tag) extends Table[Employee](tag, "experienced_employee") {
         val id = column[Int]("id",O.PrimaryKey)

         val name = column[String]("name")

         val experience = column[Double]("experience")

         def * = (name,id,experience) <> (Employee.tupled, Employee.unapply)

      }
       val employeeTableQuery = TableQuery[EmployeeTable]
   }
      object EmployeeComponent extends EmployeeTable {


        this: DbProvider =>

        import driver.api._


        def create = db.run(employeeTableQuery.schema.create)

        def insert(emp : Employee): Future[Int] = db.run {
              employeeTableQuery += emp
        }


         def delete(exp: Double): Future[Int] = {
          val query = employeeTableQuery.filter(x => x.experience === exp)
           val action = query.delete
            db.run(action)
         }

         def updateName(id:Int,name:String):Future[Int] = {
           val query = employeeTableQuery.filter(_.id === id).map(_.name).update(name)
             db.run(query)
        }


        def getAll: Future[List[Employee]] = { db.run { employeeTableQuery.to[List].result}}

           def upsert(emp : Employee) = {
             //  removin it EmployeeComponent.
            val data: List[Employee] = Await.result(getAll,Duration.Inf)

           val flag: List[Boolean] = data.map(x => if(x.id == emp.id) true else false)

         if(flag.contains(true))
             {


            val action = employeeTableQuery.filter(_.id === emp.id).map(x => (x.name,x.experience)).update((emp.name,emp.experience))
            db.run(action)
          }
         else {
               val action: Future[Int] = insert(emp)
       }


        }
     }


