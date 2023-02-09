Use SymptomTracker
Go
CREATE PROCEDURE deletePerforms
(@doctorID Integer, @treatmentID Integer)
As
Begin
	if(@doctorID is null or @treatmentID is null)
	Begin
		Raiserror('Input Arguments cannot be null', 14, 1)
		Return 1
	End
	if(NOT EXISTS(SELECT * FROM Performs WHERE Performs.doctorID = @doctorID and Performs.treatmentID = @treatmentID))
	Begin
		Raiserror('Performs relationship does not exist', 14, 1)
		Return 2
	End
	Delete From Performs
	Where Performs.doctorID = @doctorID and Performs.TreatmentID = @treatmentID
End
