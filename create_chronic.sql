use SymptomTracker
go
create table Chronic(
	PersonID Integer REFERENCES dbo.Person(ID)
		On Delete Cascade
		On Update Cascade,
	SymptomID Integer REFERENCES dbo.Symptom(ID)
		On Delete Cascade
		On Update Cascade,
	primary key (PersonID, SymptomID)
);
