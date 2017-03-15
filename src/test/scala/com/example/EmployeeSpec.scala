package com.example

import com.example.Components.EmployeeComponent
import com.example.Models.Employee
import connection.H2Component
import org.scalatest.AsyncFunSuite

class EmployeeTableTest extends AsyncFunSuite{

  object testing extends EmployeeComponent with H2Component

  test("Add new Employee ") {
    testing.insert(Employee("ankit",11111,10D)).map( x =>assert(x == 1))
  }

  test("update Employee record ") {
    testing.updateName(112,"mahesh").map(x=>assert(x == 1))
  }

  test("delete Employee ") {
    testing.delete(2.1).map(x=>assert(x == 1))
  }

  test("get all employees") {
    testing.getAll.map( x => assert( x.size == 2))
  }




}