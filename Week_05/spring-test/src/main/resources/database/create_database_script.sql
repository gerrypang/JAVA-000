CREATE DATABASE IF NOT EXISTS test
DEFAULT CHARACTER SET utf8
DEFAULT COLLATE utf8_general_ci;

use test;﻿

CREATE TABLE tb_student (
		id INT NOT NULL AUTO_INCREMENT,
    age INT NOT NULL,
    gender VARCHAR( 10 ) NOT NULL,
    name VARCHAR( 50 ) NOT NULL,
    address VARCHAR( 100 ) NULL,
    klass_id INT NULL,
    school_id INT NULL,
    PRIMARY KEY( id )
)
ENGINE = InnoDB DEFAULT CHARSET = utf8;


CREATE TABLE tb_klass (
		id INT NOT NULL AUTO_INCREMENT,
    code VARCHAR( 50 ) NOT NULL,
    name VARCHAR( 50 ) NOT NULL,
    PRIMARY KEY( id )
)
ENGINE = InnoDB DEFAULT CHARSET = utf8;

CREATE TABLE tb_school (
		id INT NOT NULL AUTO_INCREMENT,
    code VARCHAR( 50 ) NOT NULL,
    name VARCHAR( 50 ) NOT NULL,
    PRIMARY KEY( id )
)
ENGINE = InnoDB DEFAULT CHARSET = utf8;