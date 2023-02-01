use [SymptomTracker]
go
create procedure InsertTreatment(@ID int, @Cost int, @name varchar(30))
As
Begin
	if(@ID is null or @Cost is null or @name is null)
	Begin
		raiserror('Input Arguments cannot be null', 14,1)
		return 1
	End

	if exists(select * from Treatment where ID = @ID)
	Begin
		raiserror('Input Arguments already exists in Treatment', 14,1)
		return 2
	End 
	Insert into dbo.Treatment(ID, Cost, name) Values(@ID, @Cost, @name)
	return 0
End
