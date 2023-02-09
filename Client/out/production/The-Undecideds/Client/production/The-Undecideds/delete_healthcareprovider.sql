Use SymptomTracker
Go
CREATE PROCEDURE deleteHealthCareProvider
(@name varchar(50))
As
Begin
	if(@name is null)
	Begin
		Raiserror('Input Argument cannot be null', 14, 1)
		Return 1
	End
	if(NOT EXISTS(SELECT * FROM HealthCareProvider WHERE HealthCareProvider.name = @name))
	Begin
		Raiserror('Health Care Provider not in table', 14, 1)
		Return 2
	End
	Delete From HealthCareProvider
	Where HealthCareProvider.name = @name
End
