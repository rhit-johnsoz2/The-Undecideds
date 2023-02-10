Use SymptomTracker
Go
Create Procedure GetDoctorsFromHCP
(@hcpID int)
As
BEGIN
	if(@hcpID is null)
	BEGIN
		Raiserror('HealthCareProvider can not be null', 14, 1)
		Return 1
	END
	if(NOT EXISTS (SELECT H.ID FROM HealthCareProvider H WHERE H.ID = @hcpID))
	BEGIN
		Raiserror('HealthCareProvider is not in table', 14, 1)
		Return 2
	END
	SELECT *
	FROM AllDoctorsInHCP
	WHERE AllDoctorsInHCP.HCPID = @hcpID
END
