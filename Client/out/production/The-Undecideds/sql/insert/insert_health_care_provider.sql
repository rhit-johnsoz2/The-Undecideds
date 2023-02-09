Use SymptomTracker
Go
CREATE PROCEDURE InsertHealthCareProviders
	(@name varchar(50))
AS
BEGIN
	-- check to see if any of the parameters are null
	IF(@name is NULL)
	BEGIN
		RAISERROR('Input arguments cannot be null', 14, 1)
		Return 1
	END
	-- check to see if the hcp already exists in the hcp database
	IF(EXISTS (SELECT * FROM HealthCareProvider WHERE name = @name))
	BEGIN
		RAISERROR('HCP already in HCP table', 14, 1)
		Return 2
	END
INSERT INTO HealthCareProvider Values(@name)
Return 0
END

--Testing no errors
--DECLARE @status int
--EXEC @status = dbo.InsertHealthCareProviders @name = 'Terre Haute Hospital'
--SELECT @status

--Testing error code 1
--DECLARE @status int
--EXEC @status = dbo.InsertHealthCareProviders @name = null
--SELECT @status

--Testing error code 2
--DECLARE @status int
--EXEC @status = dbo.InsertHealthCareProviders @name = 'Terre Haute Hospital'
--SELECT @status
