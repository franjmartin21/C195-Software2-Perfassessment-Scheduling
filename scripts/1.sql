drop table if EXISTS Appointment;
DROP TABLE IF EXISTS User;
drop table if EXISTS Customer;
drop table if EXISTS Address;
drop table if EXISTS City;
drop table if EXISTS Country;


CREATE TABLE User(
  userId int auto_increment,
  userName varchar(50),
  password varchar(50),
  active tinyint(1),
  createdBy varchar(40),
  createDate datetime,
  lastUpdate datetime,
  lastUpdateBy VARCHAR(50),

  PRIMARY KEY(userId)
);

insert into User (userName, password, active, createdBy, createDate, lastUpdate, lastUpdateBy)
    values('user', 'user', 1, null, sysdate(), sysdate(), null);

CREATE TABLE Country(
  countryId int AUTO_INCREMENT,
  country varchar(50),
  createDate datetime,
  createdBy varchar(40),
  lastUpdate TIMESTAMP,
  lastUpdateBy varchar(40),

  PRIMARY KEY(countryId)
);

CREATE TABLE City(
  cityId int AUTO_INCREMENT,
  city VARCHAR(50),
  countryId INT,
  createDate datetime,
  createdBy varchar(40),
  lastUpdate TIMESTAMP,
  lastUpdateBy varchar(40),

  PRIMARY KEY(cityId),
  FOREIGN KEY (countryId) references Country(countryId)
);

CREATE TABLE Address(
  addressId int AUTO_INCREMENT,
  address varchar(50),
  address2 varchar(50),
  cityId int,
  postalCode varchar(10),
  phone varchar(20),
  createDate datetime,
  createdBy varchar(40),
  lastUpdate TIMESTAMP,
  lastUpdateBy varchar(40),

  PRIMARY KEY(addressId),
  FOREIGN KEY (cityId) references City(cityId)
);


CREATE TABLE Customer(
  customerId int AUTO_INCREMENT,
  customerName varchar(45),
  addressId int(10),
  active tinyint(1),
  createDate datetime,
  createdBy varchar(40),
  lastUpdate TIMESTAMP,
  lastUpdateBy varchar(40),

  PRIMARY KEY(customerId),
  FOREIGN KEY (addressId) references Address(addressId)
);


CREATE TABLE Appointment(
  appointmentId int AUTO_INCREMENT,
  customerId int,
  title varchar(255),
  description text,
  location text,
  contact text,
  url varchar(255),
  start datetime,
  end datetime,
  createDate datetime,
  createdBy varchar(40),
  lastUpdate TIMESTAMP,
  lastUpdateBy varchar(40),

  PRIMARY KEY(appointmentId),
  FOREIGN KEY (customerId) references Customer(customerId)
);




