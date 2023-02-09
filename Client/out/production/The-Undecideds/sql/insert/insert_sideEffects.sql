use [SymptomTracker]
go
create procedure InsertSideEffectOf(@SymptomID int, @TreatmentID int)
As
Begin
	if(@SymptomID is null or @TreatmentID is null)
	Begin
		raiserror('Input Arguments cannot be null', 14,1)
		return 1
	End
	Insert into dbo.SideEffectsOf(SymptomID, TreatmentID) Values(@SymptomID, @TreatmentID)
	return 0
End
