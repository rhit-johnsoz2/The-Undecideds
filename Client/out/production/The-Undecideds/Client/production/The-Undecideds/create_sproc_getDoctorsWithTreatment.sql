Use SymptomTracker
Go
Create Procedure GetDoctorsWithTreatment
(@treatmentID int)
As
BEGIN
	if(@treatmentID is null)
	BEGIN
		Raiserror('Treatment can not be null', 14, 1)
		Return 1
	END
	if(NOT EXISTS (SELECT * FROM Treatment T WHERE T.ID = @treatmentID))
	BEGIN
		Raiserror('Treatment is not in table', 14, 1)
		Return 2
	END
	SELECT *
	FROM DoctorsWithTreatment
	WHERE DoctorsWithTreatment.TreatmentID = @treatmentID
END
