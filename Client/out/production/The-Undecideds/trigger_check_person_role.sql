use SymptomTracker
GO
Create Trigger CheckRole on Person
After Insert
As
DECLARE @currentRole varchar(2) = (SELECT role FROM inserted)
IF (@currentRole != 'DR' and @currentRole != 'PA')
BEGIN
	Raiserror('Person must be a doctor or a patient', 14, 1)
	ROLLBACK TRANSACTION
END
