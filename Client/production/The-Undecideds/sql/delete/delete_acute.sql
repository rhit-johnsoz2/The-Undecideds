Use SymptomTracker
Go
CREATE PROCEDURE deleteAcute
(@symptomID Integer, @personID Integer)
As
Begin
	if(@symptomID is null or @personID is null)
	Begin
		Raiserror('Input Arguments cannot be null', 14, 1)
		Return 1
	End
	if(NOT EXISTS(SELECT * FROM Acute WHERE Acute.symptomID = @symptomID and Acute.personID = @personID))
	Begin
		Raiserror('Acute relationship does not exist', 14, 1)
		Return 2
	End
	Delete From Acute
	Where Acute.symptomID = @symptomID and Acute.personID = @personID
End
