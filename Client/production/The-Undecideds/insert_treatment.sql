use [SymptomTracker]
go
alter procedure InsertTreatment(@ID int, @Cost int, @name varchar(30))
As
Begin
	if(@ID is null or @Cost is null or @name is null)
	Begin
		raiserror('Input Arguments cannot be null', 14,1)
		return 1
	End
	Insert into dbo.Treatment(ID, Cost, name) Values(@ID, @Cost, @name)
	return 0
End
