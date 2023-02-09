Use SymptomTracker
Go
CREATE PROCEDURE deleteSideEffectOf
(@symptomID Integer, @treatmentID Integer)
As
Begin
	if(@symptomID is null or @treatmentID is null)
	Begin
		Raiserror('Input Arguments cannot be null', 14, 1)
		Return 1
	End
	if(NOT EXISTS(SELECT * FROM SideEffectOf WHERE SideEffectOf.symptomID = @symptomID and SideEffectOf.treatmentID = @treatmentID))
	Begin
		Raiserror('SideEffectOf relationship does not exist', 14, 1)
		Return 2
	End
	Delete From SideEffectOf
	Where SideEffectOf.symptomID = @symptomID and SideEffectOf.treatmentID = @treatmentID
End
