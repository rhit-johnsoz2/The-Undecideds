Use SymptomTracker
Go
CREATE PROCEDURE InsertNeeds
	(@patientID Integer, @treatmentID Integer, 
	 @sDate Date, @eDate Date)
AS
BEGIN
	-- check to see if any of the parameters are null
	IF(@patientID is NULL or @treatmentID is NULL or @sDate is NULL or @eDate is NULL)
	BEGIN
		RAISERROR('Input arguments cannot be null', 14, 1)
		Return 1
	END
	-- check to see if the person exists in the person database
	IF(NOT EXISTS (SELECT * FROM Person WHERE ID = @patientID))
	BEGIN
		RAISERROR('Patient not in the person database', 14, 1)
		Return 2
	END
	-- check to see if the treatment exists in the treatment database
	IF(NOT EXISTS (SELECT * FROM Treatment WHERE ID = @treatmentID))
	BEGIN
		RAISERROR('Treatment not in the treatment database', 14, 1)
		Return 3
	END
INSERT INTO Needs Values(@patientID, @treatmentID, @sDate, @eDate)
Return 0
END
