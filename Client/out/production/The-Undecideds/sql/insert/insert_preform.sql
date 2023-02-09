use [SymptomTracker]
go

create procedure InsertPreform(@doctorID Integer,@treatmentID Integer)
AS
Begin
	if(@doctorID is null or @TreatmentID is null)
	Begin
		raiserror('Input Arguments cannot be null', 14,1)
		return 1
	End

	if exists(select * from Performs where TreatmentID = @TreatmentID)
	Begin
		raiserror('Input Arguments already exists in Preform', 14,1)
		return 2
	End 
	Insert into dbo.Preforms(doctorID, TreatmentID) Values(@doctorID,@TreatmentID)
	return 0
End


