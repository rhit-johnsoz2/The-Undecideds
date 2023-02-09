Use SymptomTracker
Go
CREATE PROCEDURE deleteNeeds
(@patientID Integer, @treatmentID Integer)
As
Begin
	if(@patientID is null or @treatmentID is null)
	Begin
		Raiserror('Input Arguments cannot be null', 14, 1)
		Return 1
	End
	if(NOT EXISTS(SELECT * FROM Needs WHERE Needs.patientID = @patientID and Needs.treatmentID = @treatmentID))
	Begin
		Raiserror('Needs relationship does not exist', 14, 1)
		Return 2
	End
	Delete From Needs
	Where Needs.patientID = @patientID and Needs.TreatmentID = @treatmentID
End
