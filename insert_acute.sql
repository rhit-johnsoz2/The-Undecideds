Use SymptomTracker
Go
CREATE PROCEDURE InsertAcute
	(@personID Integer, @symptomID Integer, @name Varchar(40),
	 @severity Integer, @symptomtimestamp Timestamp)
AS
BEGIN
	-- check to see if any of the parameters are null
	IF(@personID is NULL or @symptomID is NULL or @name is NULL or @severity is NULL or @symptomtimestamp is NULL)
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
		-- insert the new symptom into the symptom database
		INSERT INTO Symptom Values(@symptomID, @name)
	END
INSERT INTO Acute Values(@personID, @symptomID, @name, @severity, @symptomtimestamp)
Return 0
END
