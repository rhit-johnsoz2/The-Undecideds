use [SymptomTracker]
go
alter procedure InsertPerson(@FName varchar(30), @LName varchar(30), @Login varchar(30), @Password varchar(30), @Role char(2), @InsuredBy Integer)
AS
Begin
	if (@FName is null or @LName is null or @Login is null or @Password is null or @Role is null or @InsuredBy is null)
	Begin
		raiserror('Input Arguments cannot be null', 14,1)
		return 1
	End

	Insert into dbo.Person(FName, LName, Login, Password, role, hcpID) Values(@FName, @LName, @Login, @Password, @Role, @InsuredBy)
	return 0
End
