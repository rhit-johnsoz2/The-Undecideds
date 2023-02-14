Use SymptomTracker
Go

Create Trigger DoctorsOnlyForInsures on Insures
AFTER Insert
AS
If (Select PersonID from inserted) != 'DR'
Begin
	RAISERROR('Only doctors can be insured.', 14, 1)
	ROLLBACK TRANSACTION
End
GO

Create Trigger PatientsOnlyForAcuteSymptoms on Acute
AFTER Insert
AS
If (Select PersonID from inserted) != 'PA'
Begin
	RAISERROR('Only patients can be sick.', 14, 1)
	ROLLBACK TRANSACTION
End
GO

Create Trigger PatientsOnlyForChronicSymptoms on Chronic
AFTER Insert
AS
If (Select PersonID from inserted) != 'PA'
Begin
	RAISERROR('Only patients can be sick.', 14, 1)
	ROLLBACK TRANSACTION
End
GO
