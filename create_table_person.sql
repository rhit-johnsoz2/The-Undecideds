use SymptomTracker
GO

CREATE TABLE Person (
	ID Integer Identity(1, 1),
	fname varchar(30),
	lname varchar(30),
	login varchar(30),
	password varchar(30),
	role char(2),
	CONSTRAINT primary_key PRIMARY KEY (ID, login)
)
