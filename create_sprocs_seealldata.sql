Use SymptomTracker
Go
Create Procedure ShowAllPatients
As
BEGIN
	SELECT *
	FROM Person
	WHERE Person.role = 'PA'
END

Create Procedure ShowAllDoctors
As
BEGIN
	SELECT *
	FROM Person
	WHERE Person.role = 'DR'
END

Create Procedure ShowAllPeople
As
BEGIN
	SELECT *
	FROM Person
END

Create Procedure ShowAllHealthCareProviders
As
BEGIN
	SELECT *
	FROM HealthCareProvider
END

Create Procedure ShowAllTreatments
As
BEGIN
	SELECT *
	FROM Treatment
END

Create Procedure ShowAllSymptoms
As
BEGIN
	SELECT *
	FROM Symptom
END

Create Procedure ShowAllAcuteSymptoms
As
BEGIN
	SELECT *
	FROM Acute
END

Create Procedure ShowAllChronicSymptoms
As
BEGIN
	SELECT *
	FROM Chronic
END

Create Procedure ShowAllInsures
As
BEGIN
	SELECT *
	FROM Insures
END

Create Procedure ShowAllPerforms
As
BEGIN
	SELECT *
	FROM Performs
END

Create Procedure ShowAllNeeds
As
BEGIN
	SELECT *
	FROM Needs
END

Create Procedure ShowAllSideEffectOf
As
BEGIN
	SELECT *
	FROM SideEffectOf
END
