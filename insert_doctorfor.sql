USE [SymptomTracker]
GO
/****** Object:  StoredProcedure [dbo].[InsertDoctorFor]    Script Date: 2/9/2023 5:52:03 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[InsertDoctorFor]
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
	IF(NOT EXISTS (SELECT * FROM Person WHERE Person.ID = @doctorID and Person.role ='DR'))
	BEGIN
		RAISERROR('Doctor not in the person database', 14, 1)
		Return 2
	END
	-- check to see if the patient exists in the person database
	IF(NOT EXISTS (SELECT * FROM Person WHERE Person.ID = @patientID and Person.role = 'PA'))
	BEGIN
		RAISERROR('Patient not in the HCP database', 14, 1)
		Return 3
	END
INSERT INTO DoctorFor Values(@doctorID, @patientID)
Return 0
END

--DECLARE @status int
--EXEC @status = InsertAcuteSymptoms('clifton', 'Curt', 'Clifton')
--SELECT @status
