use SymptomTracker
GO

CREATE TABLE DoctorFor(
	doctorID Integer REFERENCES dbo.Person, --CHECK((SELECT role FROM dbo.Person WHERE ID = doctorID) = 'DR'),
	patientID Integer REFERENCES dbo.Person
)