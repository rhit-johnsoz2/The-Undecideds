use [SymptomTracker]
go
alter procedure InsertSymptom(@Name varchar(50))
As
Begin
	if(@Name is null)
	Begin
		raiserror('Input Arguments cannot be null', 14,1)
		return 1
	End

	Insert into dbo.Symptom(Name) Values(@Name)
	return 0
End
