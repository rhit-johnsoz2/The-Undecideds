create procedure UpdateDoctorFor(@personID int, @doctorID int)
As
Begin
Update DoctorFor
	Set doctorID = @doctorID
	Where patientID = @personID
End