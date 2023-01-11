use SymptomTracker
go

create table Chronic(
	PersonID Integer REFERENCES dbo.Person(ID),
	SymptomID Integer REFERENCES dbo.Symptom(ID),
	primary key (PersonID, SymptomID)
);
