create procedure UpdateAcute(@personID int, @severity int, @time timestamp)
As
Begin
Update Acute
	Set severity = @severity
	Where personID = @personID
End