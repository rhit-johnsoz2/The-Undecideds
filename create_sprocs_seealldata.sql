Use SymptomTracker
Go
Create Procedure ShowAllPatients
As
BEGIN
	SELECT *
	FROM Person
	WHERE Person.role = 'PA'
END
Go

Create Procedure ShowAllDoctors
As
BEGIN
	SELECT *
	FROM Person
	WHERE Person.role = 'DR'
END
Go

Create Procedure ShowAllPeople
As
BEGIN
	SELECT *
	FROM Person
END
Go

Create Procedure ShowAllHealthCareProviders
As
BEGIN
	SELECT *
	FROM HealthCareProvider
END
Go

Create Procedure ShowAllTreatments
As
BEGIN
	SELECT *
	FROM Treatment
END
Go

Create Procedure ShowAllSymptoms
As
BEGIN
	SELECT *
	FROM Symptom
END
Go

Create Procedure ShowAllAcuteSymptoms
As
BEGIN
	SELECT *
	FROM Acute
END
Go

Create Procedure ShowAllChronicSymptoms
As
BEGIN
	SELECT *
	FROM Chronic
END
Go

Create Procedure ShowAllInsures
As
BEGIN
	SELECT *
	FROM Insures
END
Go

Create Procedure ShowAllPerforms
As
BEGIN
	SELECT *
	FROM Performs
END
Go

Create Procedure ShowAllNeeds
As
BEGIN
	SELECT *
	FROM Needs
END
Go

Create Procedure ShowAllSideEffectOf
As
BEGIN
	SELECT *
	FROM SideEffectOf
END
Go
