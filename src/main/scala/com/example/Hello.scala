package com.example

import com.example.Components.{DependentComponent, ProjectComponent, EmployeeComponent}
import com.example.Models.{Dependent, Project, Employee}
import slick.dbio.DBIOAction
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

object Hello extends App{

  EmployeeComponent.create

  EmployeeComponent.insert(Employee( "mahesh chand",1,2.5D))
  EmployeeComponent.insert(Employee( "shivangi gupta",2,2.5D))
  EmployeeComponent.insert(Employee( "vinod kandpal",3,3.5D))
  EmployeeComponent.insert(Employee( "manoj joshi",4,3.5D))
  EmployeeComponent.sortByExperience()


// val ins1 = Employee("shg",5,1.5D)
////
//  val ins2 = Employee("ankitkr",6,2.3D)

//  println(Await.result(ins1 zip ins2,Duration.Inf))
 // println(Await.result(EmployeeComponent.addMultiple(ins1,ins2),Duration.Inf))

  val employeeList = Await.result(EmployeeComponent.getAll,Duration.Inf)
 // println(employeeList)
  Thread.sleep(1000)

  ProjectComponent.create

  ProjectComponent.insert(Project( 1,"my cell was stolen",10, "Prabhat"))
  ProjectComponent.insert(Project( 2,"scalageek",7, "Sonu"))
  ProjectComponent.insert(Project( 3,"codesquad",6, "sahil"))
  ProjectComponent.insert(Project( 4,"royal revolt",7, "bharat"))
  ProjectComponent.insert(Project(5,"subway surfer",4,"mahesh"))
  ProjectComponent.sortByProjectName()

// val projectList =  Await.result(ProjectComponent.getAll,Duration.Inf)
//  println(projectList)
  val ins1 =Project(5,"subway surfer5",4,"mahesh")
  val ins2= Project(5,"subway surfer6",4,"mahesh")
  println(Await.result(ProjectComponent.addMultipleProjects(ins1,ins2),Duration.Inf))
  println(Await.result(ProjectComponent.getProjectById(5),Duration.Inf))
  Thread.sleep(1000)

  DependentComponent.create

  DependentComponent.insert(Dependent(1,"Bhawani Devi","mother",None))
  DependentComponent.insert(Dependent(1,"Tika","father",Some(50)))
  DependentComponent.sortByDependentName()
  val dependentList =  Await.result(DependentComponent.getAll,Duration.Inf)

  println(dependentList)
  Thread.sleep(1000)
}
