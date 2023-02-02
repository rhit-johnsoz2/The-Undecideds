Use SymptomTracker
Go
CREATE PROCEDURE deleteDoctorFor
(@doctorID Integer, @patientID Integer)
As
Begin
	if(@doctorID is null or @patientID is null)
	Begin
		Raiserror('Input Arguments cannot be null', 14, 1)
		Return 1
	End
	if(NOT EXISTS(SELECT * FROM DoctorFor WHERE DoctorFor.doctorID = @doctorID and DoctorFor.patientID = @patientID))
	Begin
		Raiserror('Not a doctor for this patient', 14, 1)
		Return 2
	End
	Delete From DoctorFor
	Where DoctorFor.doctorID = @doctorID and DoctorFor.patientID = @patientID
End
