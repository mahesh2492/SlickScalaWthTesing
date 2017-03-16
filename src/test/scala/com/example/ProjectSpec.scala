package com.example

import com.example.Components.ProjectComponent
import com.example.Models.Project
import connection.H2Component

import org.scalatest.AsyncFunSuite


class ProjectSpec extends AsyncFunSuite {

  object proj extends ProjectComponent with H2Component

  test("Adding new Project ") {

    proj.insert(Project(1,"snake game",4,"mahesh")).map(x=>assert(x == 1))
  }

  test("Delete Project ") {
    proj.delete("angry bird").map(x => assert(x == 1))
  }

  test("update Project record ") {
    proj.updateName(112,"codesquad").map(x=>assert(x == 1))
  }

  test("upserting projects"){
    proj.upsert(Project(3,"scalageek",6,"bharat")).map(x => assert(x == 1))
  }

  test("get all projects") {
    proj.getAll.map( x => assert( x.size == 2))
  }


 test("adding multiple projects") {
   proj.addMultipleProjects(Project(4,"my cell was stolen",7,"sonu"),Project(5,"royal revolt",6,"vikas")).map(x => assert(x == 1))
 }

   test("get project by id") {
     proj.getProjectById(112).map(x => assert(x == List(Project(112,"angry bird",7,"shivangi"))))
   }

  test("join ") {
    proj.getProjectsByEmployeeName.map(x => assert(x.size === 2))
  }
  test("testing Plain sql"){
    proj.insertPlainSql.map(x=>assert(x==1))
  }


}