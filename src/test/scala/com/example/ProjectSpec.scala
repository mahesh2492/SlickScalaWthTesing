package com.example

import com.example.Components.ProjectComponent
import com.example.Models.Project
import connection.H2Component

import org.scalatest.AsyncFunSuite


class ProjectSpec extends AsyncFunSuite {

  object proj extends ProjectComponent with H2Component

  test("Adding new Project ") {

    proj.insert(Project("snake game",4,"mahesh",113)).map(x=>assert(x == 113))
  }

  test("Delete Project ") {
    proj.delete("angry bird").map(x => assert(x == 1))
  }

  test("update Project record ") {
    proj.updateName(112,"codesquad").map(x=>assert(x == 1))
  }

  test("upserting projects"){
    proj.upsert(Project("scalageek",6,"bharat",113)).map(x => assert(x == 1))
  }

  test("get all projects") {
    proj.getAll.map( x => assert( x.size == 2))
  }



 test("adding multiple projects") {
   proj.addMultipleProjects(Project("my cell was stolen",7,"sonu",112),Project("royal revolt2",6,"vikas",113)).map(x => assert(x == 1))
 }


   test("get project by id") {
     proj.getProjectById(112).map(x => assert(x == List(Project("angry bird",7,"shivangi",112))))
   }

  test("join ") {
    proj.getProjectsByEmployeeName.map(x => assert(x.size === 2))
  }
  test("testing Plain sql"){
    proj.insertPlainSql.map(x=>assert(x==1))
  }

  test("max team_members") {
    proj.teamCount.flatMap(x => assert(x == Some(7)))
  }
  test("return inserted object") {
    proj.insertRecordObj(Project("todo list",3,"sahil",112)).map( x => assert( x === Project("todo list",3,"sahil",113)))
  }






}