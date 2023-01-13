USE master
GO

CREATE DATABASE [SymptomTracker] ON (
    NAME = N'SymptomTracker',
    FILENAME = 'D:\Database\MSSQL15.MSSQLSERVER\MSSQL\DATA\SymptomTracker',
    SIZE = 6MB,  
    MAXSIZE = 30MB,  
    FILEGROWTH = 12%
)
LOG ON (
    NAME = N'SymptomTracker_log',
    FILENAME = 'D:\Database\MSSQL15.MSSQLSERVER\MSSQL\DATA\SymptomTracker.ldf',
    SIZE = 3MB,
    MAXSIZE = 30MB,
    FILEGROWTH = 10%
)
COLLATE SQL_Latin1_General_Cp1_CI_AS
GO

CREATE USER [cerasoml] FROM LOGIN [cerasoml]
EXEC sp_addrolemember 'db_owner', 'cerasoml'

CREATE USER [johnsoz2] FROM LOGIN [johnsoz2]
EXEC sp_addrolemember 'db_owner', 'johnsoz2'
GO

USE SymptomTracker
GO

CREATE TABLE HealthCareProvider (
	ID int identity(1,1),
	name varchar(50),
	PRIMARY KEY(ID)
)

CREATE TABLE Person (
	ID Integer Identity(1, 1) PRIMARY KEY,
	fname varchar(30),
	lname varchar(30),
	login varchar(30) UNIQUE,
	password varchar(30),
	role char(2),
	hcpID Integer REFERENCES dbo.HealthCareProvider
		On Update Cascade
)

create table Treatment(
	ID Integer Identity(1,1) Primary Key,
	Cost decimal(19,4)
)

CREATE TABLE Symptom (
	ID int identity(1,1),
	name varchar(40),
	primary key(ID),
	unique(name)
)

create table Insures(
	DoctorID Integer REFERENCES dbo.Person(ID)
		On Update Cascade,--CHECK((SELECT role FROM dbo.Person WHERE ID = doctorID) = 'DR'),
	HCPID Integer REFERENCES dbo.HealthCareProvider(ID)
		On Update Cascade,
	TreatmentID Integer REFERENCES dbo.Treatment(ID)
		On Update Cascade,
	Coverage Integer,
	Primary Key (DoctorID, HCPID, TreatmentID)
)


create table Needs(
	PatientID Integer REFERENCES dbo.Person(ID)
		On Update Cascade,
	TreatmentID Integer REFERENCES dbo.Treatment(ID)
		On Update Cascade,
	SDate Date,
	EDate Date,
	Primary key(PatientID, TreatmentID)
)

CREATE TABLE DoctorFor(
	doctorID Integer REFERENCES dbo.Person 
		On Update Cascade, --CHECK((SELECT role FROM dbo.Person WHERE ID = doctorID) = 'DR'),
	patientID Integer REFERENCES dbo.Person
		On Update Cascade
)

CREATE TABLE Performs (
	doctorID integer, --CHECK((SELECT role FROM dbo.Person WHERE ID = doctorID) = 'DR'),
	treatmentID integer,
	primary key(doctorID, treatmentID),
	foreign key(doctorID) references person(ID)
		On Update Cascade,
	foreign key(treatmentID) references treatment(ID)
		On Update Cascade,
)

CREATE TABLE SideEffectOf (
	symptomID integer,
	treatmentID integer,
	primary key(symptomID, treatmentID),
	foreign key(symptomID) references symptom(ID)
		On Update Cascade,
	foreign key(treatmentID) references treatment(ID)
		On Update Cascade
)

CREATE TABLE Acute (
	personID integer,
	symptomID integer,
	severity integer,
	symptomtimestamp timestamp,
	primary key(personID, symptomID),
	foreign key(personID) references person(ID)
		On Update Cascade,
	foreign key(symptomID) references symptom(ID)
		On Update Cascade,
)

create table Chronic(
	PersonID Integer REFERENCES dbo.Person(ID)
		On Update Cascade,
	SymptomID Integer REFERENCES dbo.Symptom(ID)
		On Update Cascade,
	primary key (PersonID, SymptomID)
)