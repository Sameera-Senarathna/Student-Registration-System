CREATE TABLE course (
  c_id VARCHAR (10) NOT NULL UNIQUE ,
  name VARCHAR (100) NOT NULL,
  description VARCHAR (255) NOT NULL,
  duration INT,
  PRIMARY KEY (c_id)
)

CREATE TABLE batch (
  b_id VARCHAR (10) NOT NULL UNIQUE ,
  c_id VARCHAR(10) NOT NULL,
  description VARCHAR (255) NOT NULL,
  start_date DATE NOT NULL,
  capacity INT,
  PRIMARY KEY (b_id),
  FOREIGN KEY (c_id) REFERENCES course (c_id)
)

CREATE  TABLE student(
  s_id VARCHAR (10) NOT NULL UNIQUE,
  full_name VARCHAR (255) NOT NULL,
  name_with_initials VARCHAR (255) NOT NULL,
  sex VARCHAR (6) NOT NULL,
  s_address VARCHAR (255) NOT NULL,
  s_phone VARCHAR (11) NOT NULL,
  s_mail VARCHAR (255) NOT NULL,
  birthDay DATE NOT NULL,
  school VARCHAR (255) NOT NULL,
  university VARCHAR (255),
  faculty VARCHAR (255),
  nic VARCHAR (10),
  PRIMARY KEY (s_id)
)

CREATE TABLE guardian(
  g_id INT NOT NULL AUTO_INCREMENT,
  g_name VARCHAR (100) NOT NULL,
  g_phone VARCHAR (11) NOT NULL,
  g_mail VARCHAR (100),
  g_address VARCHAR (255),
  work_place VARCHAR (50),
  designation VARCHAR (50),
  s_id VARCHAR (10) NOT NULL,
  PRIMARY KEY (g_id),
  FOREIGN KEY (s_id) REFERENCES student(s_id)
)

CREATE TABLE register(
  s_id VARCHAR (10) NOT NULL,
  b_id VARCHAR (10) NOT NULL,
  PRIMARY KEY (s_id,b_id),
  FOREIGN KEY (s_id) REFERENCES student(s_id),
  FOREIGN KEY (b_id) REFERENCES student(b_id)
)

CREATE TABLE qualification(
  q_id VARCHAR (100) NOT NULL,
  qualification VARCHAR (10) NOT NUlL,
  PRIMARY KEY (q_id)
)

INSERT INTO qualification VALUES ("Q1","Masters");
INSERT INTO qualification VALUES ("Q2","Degree");
INSERT INTO qualification VALUES ("Q3","Diploma");
INSERT INTO qualification VALUES ("Q4","A/L");
INSERT INTO qualification VALUES ("Q5","O/L");
INSERT INTO qualification VALUES ("Q6","Other");

CREATE TABLE qualified(
  q_id VARCHAR (100) NOT NULL,
  s_id VARCHAR (10) NOT NULL,
  PRIMARY KEY (q_id,s_id),
  FOREIGN KEY (q_id) REFERENCES qualification (q_id),
  FOREIGN KEY (s_id) REFERENCES student(s_id)
)