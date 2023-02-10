Use SymptomTracker
Go
CREATE PROCEDURE InsertNeeds
	(@patientID Integer, @treatmentID Integer, 
	 @sDate Date, @eDate Date)
AS
BEGIN
	-- check to see if any of the parameters are null
	IF(@patientID is NULL or @treatmentID is NULL or @sDate is NULL)
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
	-- check to see if the end date is before the start date
	IF(@eDate < @sDate)
	BEGIN
		RAISERROR('End date happens before start date', 14, 1)
		Return 4
	END
INSERT INTO Needs Values(@patientID, @treatmentID, @sDate, @eDate)
Return 0
END

--Testing no errors
--DECLARE @status int
--EXEC @status = dbo.InsertNeeds @patientID = 2, @treatmentID = 2, @sDate = '2020-11-20', @eDate = null
--SELECT @status

--Testing error code 1
--DECLARE @status int
--EXEC @status = dbo.InsertNeeds @patientID = 2, @treatmentID = 2, @sDate = null
--SELECT @status

--Testing error code 2
--DECLARE @status int
--EXEC @status = dbo.InsertNeeds @patientID = 200, @treatmentID = 2, @sDate = '2020-11-20'
--SELECT @status

--Testing error code 3
--DECLARE @status int
--EXEC @status = dbo.InsertNeeds @patientID = 2, @treatmentID = 100, @sDate = '2020-11-20'
--SELECT @status

--Testing error code 4
--DECLARE @status int
--EXEC @status = dbo.InsertNeeds @patientID = 2, @treatmentID = 2, @sDate = '2020-12-20', @eDate = '2019-12-20'
--SELECT @status
