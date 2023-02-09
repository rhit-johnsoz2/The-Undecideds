Use SymptomTracker
Go
CREATE PROCEDURE deleteChronic
(@symptomID Integer, @personID Integer)
As
Begin
	if(@symptomID is null or @personID is null)
	Begin
		Raiserror('Input Arguments cannot be null', 14, 1)
		Return 1
	End
	if(NOT EXISTS(SELECT * FROM Chronic WHERE Chronic.symptomID = @symptomID and Chronic.personID = @personID))
	Begin
		Raiserror('Chronic relationship does not exist', 14, 1)
		Return 2
	End
	Delete From Chronic
	Where Chronic.symptomID = @symptomID and Chronic.personID = @personID
End
