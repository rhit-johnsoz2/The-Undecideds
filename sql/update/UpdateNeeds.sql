create procedure UpdateNeeds(@PersonId int, @Treatment int, @Start date, @end date)
As
Begin
	Update Needs
	Set SDate = @Start,
		EDate = @end
	Where PatientID = @PersonId and TreatmentID = @Treatment
End