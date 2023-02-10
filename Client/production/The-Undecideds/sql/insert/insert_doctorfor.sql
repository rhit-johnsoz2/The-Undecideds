Use SymptomTracker
Go
CREATE PROCEDURE InsertDoctorFor
	(@doctorID Integer, @patientID Integer)
AS
BEGIN
	-- check to see if any of the parameters are null
	IF(@doctorID is NULL or @patientID is NULL)
	BEGIN
		RAISERROR('Input arguments cannot be null', 14, 1)
		Return 1
	END
	-- check to see if the doctor exists in the patient database
	IF(NOT EXISTS (SELECT * FROM Person WHERE ID = @doctorID))
	BEGIN
		RAISERROR('Doctor not in the person database', 14, 1)
		Return 2
	END
	-- check to see if the patient exists in the person database
	IF(NOT EXISTS (SELECT * FROM Person WHERE ID = @patientID))
	BEGIN
		RAISERROR('Patient not in the person database', 14, 1)
		Return 3
	END
INSERT INTO DoctorFor Values(@doctorID, @patientID)
Return 0
END

--Testing no errors
--DECLARE @status int
--EXEC @status = dbo.InsertDoctorFor @doctorID = 3, @patientID = 2
--SELECT @status

--Testing error code 1
--DECLARE @status int
--EXEC @status = dbo.InsertDoctorFor @doctorID = null, @patientID = 2
--SELECT @status

--Testing error code 2
--DECLARE @status int
--EXEC @status = dbo.InsertDoctorFor @doctorID = 40, @patientID = 2
--SELECT @status

--Testing error code 3
--DECLARE @status int
--EXEC @status = dbo.InsertDoctorFor @doctorID = 3, @patientID = 30
--SELECT @status
