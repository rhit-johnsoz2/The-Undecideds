use SymptomTracker
go

create table Insures(
	
	PersonID Integer REFERENCES dbo.Person(ID),
	HCPID Integer REFERENCES dbo.HealthCareProvider(ID),
	TreatmentID Integer REFERENCES dbo.Treatment(ID),
	Coverage Integer,
	Primary Key (PersonID, HCPID, TreatmentID)
);