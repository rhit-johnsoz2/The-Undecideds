CREATE PROCEDURE GetPatientsUnderDoctor 
(@doctorID Integer)
AS
	IF(@doctorID is null)
	BEGIN
		RAISERROR('Doctor ID does not exist.', 14, 1)
		Return 1
	END
	IF(NOT EXISTS(SELECT * FROM DoctorNames WHERE @doctorID = ID))
	BEGIN
		RAISERROR('Doctor does not exist.', 14, 1)
		Return 2
	END
	SELECT * FROM DoctorView WHERE DoctorID = @doctorID
GO

CREATE PROCEDURE GetPatientsNotUnderDoctor
(@doctorID Integer)
AS
	IF(@doctorID is null)
	BEGIN
		RAISERROR('Doctor ID does not exist.', 14, 1)
		Return 1
	END
	IF(NOT EXISTS(SELECT * FROM DoctorNames WHERE @doctorID = ID))
	BEGIN
		RAISERROR('Doctor does not exist.', 14, 1)
		Return 2
	END
	SELECT * FROM DoctorView WHERE DoctorID != @doctorID
GO
