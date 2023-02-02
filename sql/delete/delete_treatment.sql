Use SymptomTracker
Go
CREATE PROCEDURE deleteTreatment
(@name varchar(30))
As
Begin
	if(@name is null)
	Begin
		Raiserror('Input Argument cannot be null', 14, 1)
		Return 1
	End
	if(NOT EXISTS(SELECT * FROM Treatment WHERE Treatment.name = @name))
	Begin
		Raiserror('Treatment not in table', 14, 1)
		Return 2
	End
	Delete From Treatment
	Where Treatment.name = @name
End
