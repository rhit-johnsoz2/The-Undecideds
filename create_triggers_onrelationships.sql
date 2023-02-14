Use SymptomTracker
Go

Create Trigger DoctorsOnlyForInsures on Insures
AFTER Insert
AS
DECLARE @doc Integer
SET @doc = (Select PersonID from inserted)
If (NOT EXISTS(SELECT * FROM DoctorNames WHERE ID = @doc))
Begin
	RAISERROR('Only doctors can be insured.', 14, 1)
	ROLLBACK TRANSACTION
End
GO

Create Trigger PatientsOnlyForAcuteSymptoms on Acute
AFTER Insert
AS
DECLARE @pat Integer
SET @pat = (Select PersonID from inserted)
If (NOT EXISTS(SELECT * FROM PatientNames WHERE ID = @pat))
Begin
	RAISERROR('Only patients can be sick.', 14, 1)
	ROLLBACK TRANSACTION
End
GO

Create Trigger PatientsOnlyForChronicSymptoms on Chronic
AFTER Insert
AS
DECLARE @pat Integer
SET @pat = (Select PersonID from inserted)
If (NOT EXISTS(SELECT * FROM PatientNames WHERE ID = @pat))
Begin
	RAISERROR('Only patients can be sick.', 14, 1)
	ROLLBACK TRANSACTION
End
GO
