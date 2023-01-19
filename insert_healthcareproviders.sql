Use SymptomTracker
Go
CREATE PROCEDURE InsertHealthCareProviders
	(@ID int, @name varchar(50))
AS
BEGIN
	-- check to see if any of the parameters are null
	IF(@ID is NULL or @name is NULL)
	BEGIN
		RAISERROR('Input arguments cannot be null', 14, 1)
		Return 1
	END
	-- check to see if the hcp already exists in the hcp database
	IF(EXISTS (SELECT * FROM HealthCareProvider WHERE ID = @ID))
	BEGIN
		RAISERROR('HCP already in HCP database', 14, 1)
		Return 2
	END
INSERT INTO HealthCareProvider Values(@ID, @name)
Return 0
END
