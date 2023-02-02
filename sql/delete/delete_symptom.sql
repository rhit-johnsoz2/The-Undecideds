Use SymptomTracker
Go
CREATE PROCEDURE deleteSymptom
(@name varchar(40))
As
Begin
	if(@name is null)
	Begin
		Raiserror('Input Argument cannot be null', 14, 1)
		Return 1
	End
	if(NOT EXISTS(SELECT * FROM Symptom WHERE Symptom.name = @name))
	Begin
		Raiserror('Symptom not in table', 14, 1)
		Return 2
	End
	Delete From Symptom
	Where Symptom.name = @name
End
