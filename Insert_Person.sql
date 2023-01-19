use [SymptomTracker]
go
create procedure InsertPerson(@ID int, @Name varchar(30), @Login varchar(30), @Password varchar(30), @IsDoctor char(2),@InsuredBy varchar(30))
AS
Begin
	if(@ID is null or @Name is null or @Login is null or @Password is null or @IsDocotor is null or @InsuredBy is null)
	Begin
		raiserror('Input Arguments cannot be null', 14,1)
		return 1
	End

	if exists(select * from Person where ID = @ID)
	Begin
		raiserror('Input Arguments already exists in Person', 14,1)
		return 2
	End 

	Insert into dbo.Person(ID, Name, Login, Password, IsDoctor, InsuredBy) Values(@ID, @Name, @Login, @Password, @IsDoctor, @InsuredBy)
	return 0
End


