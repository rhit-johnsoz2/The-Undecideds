Use SymptomTracker
Go
CREATE PROCEDURE InsertAcute
	(@personID Integer, @symptomID Integer,
	 @severity Integer, @symptomtimestamp Timestamp = GETDATE)
AS
BEGIN
	-- check to see if any of the parameters are null
	IF(@personID is NULL or @symptomID is NULL or @severity is NULL or @symptomtimestamp is NULL)
	BEGIN
		RAISERROR('Input arguments cannot be null', 14, 1)
		Return 1
	END
	-- check to see if the person exists in the person database
	IF(NOT EXISTS (SELECT * FROM Person WHERE Person.ID = @personID))
	BEGIN
		RAISERROR('Person not in the person database', 14, 1)
		Return 2
	END
	-- check to see if the symptom exists in the symptom database
	IF(NOT EXISTS (SELECT * FROM Symptom WHERE Symptom.ID = @symptomID))
	BEGIN
		RAISERROR('Symptom not in the person database', 14, 1)
		Return 3
	END
INSERT INTO Acute Values(@personID, @symptomID, @severity, @symptomtimestamp)
Return 0
END
