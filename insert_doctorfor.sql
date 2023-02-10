USE [SymptomTracker]
GO
/****** Object:  StoredProcedure [dbo].[InsertDoctorFor]    Script Date: 2/10/2023 2:07:04 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[InsertDoctorFor]
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
	-- duplicate value
	IF (EXISTS (SELECT * FROM DoctorFor WHERE doctorID = @doctorID and patientID = @patientID))
	BEGIN
		RAISERROR('Relationship already exists', 14, 1)
		Return 4
	END
INSERT INTO DoctorFor Values(@doctorID, @patientID)
Return 0
END
