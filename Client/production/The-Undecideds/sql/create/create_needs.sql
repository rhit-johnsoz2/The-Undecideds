use SymptomTracker
go

create table Needs(
	PatientID Integer REFERENCES dbo.Person(ID),
	TreatmentID Integer REFERENCES dbo.Treatment(ID),
	SDate Date,
	EDate Date,
	Primary key(PatientID, TreatmentID)
);