Use SymptomTracker
Go
CREATE PROCEDURE deleteInsures
(@personID Integer, @hcpID Integer, @treatmentID Integer)
As
Begin
	if(@personID is null or @hcpID is null or @treatmentID is null)
	Begin
		Raiserror('Input Arguments cannot be null', 14, 1)
		Return 1
	End
	if(NOT EXISTS(SELECT * FROM Insures WHERE Insures.PersonID = @personID and Insures.HCPID = @hcpID and Insures.TreatmentID = @treatmentID))
	Begin
		Raiserror('Insures relationship does not exist', 14, 1)
		Return 2
	End
	Delete From Insures
	Where Insures.personID = @personID and Insures.HCPID = @hcpID and Insures.TreatmentID = @treatmentID
End
