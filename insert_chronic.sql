Use SymptomTracker
Go
CREATE PROCEDURE InsertChronic
	(@personID integer, @symptomID integer)
AS
BEGIN
	-- check to see if any of the parameters are null
	IF(@personID is NULL or @symptomID is NULL)
	BEGIN
		RAISERROR('Input arguments cannot be null', 14, 1)
		Return 1
	END
	-- check to see if the person exists in the person database
	IF(NOT EXISTS (SELECT * FROM Person WHERE ID = @personID))
	BEGIN
		RAISERROR('Person not in the person database', 14, 1)
		Return 2
	END
	-- check to see if the symptom exists in the symptom database
	IF(NOT EXISTS (SELECT * FROM Symptom WHERE ID = @symptomID))
	BEGIN
		RAISERROR('Symptom not in the symptom database', 14, 1)
		Return 3
	END
INSERT INTO Chronic Values(@personID, @symptomID)
Return 0
END
