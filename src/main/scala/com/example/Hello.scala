package com.example

import com.example.Components.{DependentComponent, ProjectComponent, EmployeeComponent}
import com.example.Models.{Dependent, Project, Employee}
import slick.dbio.DBIOAction
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

object Hello extends App{



  EmployeeComponent.create
// val res= Await.result(EmployeeComponent.insert(Employee( "mahesh chand",2.5D)),Duration.Inf)
//  EmployeeComponent.insert(Employee( "mahesh ",2.5D))
//  EmployeeComponent.insert(Employee( "mahesh chand kandpal",2.5D))
//  println(res)
//  Thread.sleep(1000)
  ProjectComponent.create
  DependentComponent.create
  Thread.sleep(1000)

//  val pres = Await.result(ProjectComponent.insert(Project("codesquad",5,"vikas")),Duration.Inf)
//  ProjectComponent.insert(Project("scalageek",7,"sahil"))


// val res1 = Await.result(DependentComponent.insert(Dependent("tika chand","father",Some(52),1)),Duration.Inf)
//  Thread.sleep(1000)
//  println(res1)

}
