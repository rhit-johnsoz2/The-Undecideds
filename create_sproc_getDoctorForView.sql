Use SymptomTracker
Go
Create Procedure GetDoctorForView
(@doctorid int)
As
BEGIN
	if(@doctorid is null)
	BEGIN
		Raiserror('Doctor can not be null', 14, 1)
		Return 1
	END
	if(NOT EXISTS (SELECT P.ID FROM Person P WHERE P.role = 'DR' and P.ID = @doctorid))
	BEGIN
		Raiserror('Doctor is not in table', 14, 1)
		Return 2
	END
	SELECT *
	FROM DoctorView
	WHERE DoctorView.DoctorID = @doctorid
END
