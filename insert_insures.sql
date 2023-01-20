Use SymptomTracker
Go
CREATE PROCEDURE InsertInsures
	(@personID integer, @hcpID integer, 
	 @treatmentID integer, @coverage integer)
AS
BEGIN
	-- check to see if any of the parameters are null
	IF(@personID is NULL or @hcpID is NULL or @treatmentID is NULL or @coverage is NULL)
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
	-- check to see if the hcp exists in the hcp database
	IF(NOT EXISTS (SELECT * FROM HealthCareProvider WHERE ID = @hcpID))
	BEGIN
		RAISERROR('Health Care Provider not in the HCP database', 14, 1)
		Return 3
	END
	-- check to see if the treatment exists in the treatment database
	IF(NOT EXISTS (SELECT * FROM Treatment WHERE ID = @treatmentID))
	BEGIN
		RAISERROR('Treatment not in the treatment database', 14, 1)
		Return 4
	END
INSERT INTO Insures Values(@personID, @hcpID, @treatmentID, @coverage)
Return 0
END

--Testing no errors
--DECLARE @status int
--EXEC @status = dbo.InsertInsures @personID = 2, @hcpID = 1, @treatmentID = 2, @coverage = 5.03
--SELECT @status

--Testing error code 1
--DECLARE @status int
--EXEC @status = dbo.InsertInsures @personID = 2, @hcpID = null, @treatmentID = 2, @coverage = 5.03
--SELECT @status

--Testing error code 2
--DECLARE @status int
--EXEC @status = dbo.InsertInsures @personID = 50, @hcpID = 1, @treatmentID = 2, @coverage = 5.03
--SELECT @status

--Testing error code 3
--DECLARE @status int
--EXEC @status = dbo.InsertInsures @personID = 2, @hcpID = 20, @treatmentID = 2, @coverage = 5.03
--SELECT @status

--Testing error code 4
--DECLARE @status int
--EXEC @status = dbo.InsertInsures @personID = 2, @hcpID = 1, @treatmentID = 30, @coverage = 5.03
--SELECT @status
