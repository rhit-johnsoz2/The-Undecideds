use SymptomTracker
GO

CREATE TABLE Person (
	ID Integer Identity(1, 1) PRIMARY KEY,
	fname varchar(30),
	lname varchar(30),
	login varchar(30) UNIQUE,
	password varchar(30),
	role char(2),
	hcpID Integer REFERENCES dbo.HealthCareProvider
)