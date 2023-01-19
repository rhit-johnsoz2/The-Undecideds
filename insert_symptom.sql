use [SymptomTracker]
go
create procedure InsertSymptom(@ID int, @Name varchar(50)
As
Begin
	if(@ID is null or @Name is null)
	Begin
		raiserror('Input Arguments cannot be null', 14,1)
		return 1
	End

	if exists(select * from Symptom where ID = @ID)
	Begin
		raiserror('Input Arguments already exists in Symptom', 14,1)
		return 2
	End 
	Insert into dbo.Symptom(ID, Name) Values(@ID, @Name)
	return 0
End
