Use SymptomTracker
Go
Create Procedure GetAllPatientsDoctorHas
(@doctorID int)
As
BEGIN
	if(@doctorID is null)
	BEGIN
		Raiserror('Doctor can not be null', 14, 1)
		Return 1
	END
	if(NOT EXISTS (SELECT * FROM Person D WHERE D.ID = @doctorID and D.role = 'DR'))
	BEGIN
		Raiserror('Doctor is not in table', 14, 1)
		Return 2
	END
	SELECT *
	FROM PatientsOnDoctorView
	WHERE PatientsOnDoctorView.DoctorID = @doctorID
END
