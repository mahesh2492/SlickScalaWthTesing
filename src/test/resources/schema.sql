DROP TABLE IF EXISTS experienced_employee;

CREATE TABLE IF NOT EXISTS experienced_employee(name varchar(200),id int PRIMARY KEY,experience double);

DROP TABLE IF EXISTS project;

  CREATE TABLE IF NOT EXISTS project(id int NOT NULL,
                          pname varchar(200) ,
                          team_members int,
                          lead varchar(200),
                          PRIMARY KEY(pname),
                          FOREIGN KEY(id) REFERENCES experienced_employee(id)
                         );