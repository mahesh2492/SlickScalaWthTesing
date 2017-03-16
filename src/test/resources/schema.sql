DROP TABLE IF EXISTS experienced_employee;

CREATE TABLE IF NOT EXISTS experienced_employee(name varchar(200),
                            experience double,
                            id INT  NOT NULL AUTO_INCREMENT,
                            PRIMARY KEY (id));

DROP TABLE IF EXISTS project;

CREATE TABLE IF NOT EXISTS project(
                          pname varchar(200) ,
                          team_members int,
                          lead varchar(200),
                          id int NOT NULL AUTO_INCREMENT,
                          PRIMARY KEY(id),
                          FOREIGN KEY(id) REFERENCES experienced_employee(id)
                         );




