USE [SymptomTracker]
GO
/****** Object:  StoredProcedure [dbo].[InsertHealthCareProviders]    Script Date: 2/9/2023 2:24:20 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [dbo].[InsertHealthCareProviders]
	(@name varchar(50))
AS
BEGIN
	-- check to see if any of the parameters are null
	IF(@name is NULL)
	BEGIN
		RAISERROR('Input arguments cannot be null', 14, 1)
		Return 1
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
