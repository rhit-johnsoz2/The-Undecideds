Use SymptomTracker
Go
CREATE PROCEDURE deletePerson
(@FName varchar(30), @LName varchar(30))
As
Begin
	if(@FName is null or @LName is null)
	Begin
		Raiserror('Input Arguments cannot be null', 14, 1)
		Return 1
	End
	if(NOT EXISTS(SELECT * FROM Person WHERE Person.fname = @FName and Person.lname = @LName))
	Begin
		Raiserror('Person not in table', 14, 1)
		Return 2
	End
	Delete From Person
	Where Person.fname = @FName and Person.lname = @LName
End
