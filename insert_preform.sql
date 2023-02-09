USE [SymptomTracker]
GO
/****** Object:  StoredProcedure [dbo].[InsertPreform]    Script Date: 2/9/2023 2:18:02 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER procedure [dbo].[InsertPreform](@doctorID Integer, @treatmentID Integer)
AS
Begin
	if(@doctorID is null or @treatmentID is null)
	Begin
		raiserror('Input Arguments cannot be null', 14,1)
		return 1
	End

	if not exists(select * from Treatment where Treatment.ID = @treatmentID)
	Begin
		raiserror('Treatment does not exist', 14,1)
		return 2
	End 

	if not exists(select * from Person where Person.ID = @doctorID and Person.role = 'DR')
	Begin
		raiserror('Doctor does not exist', 14,1)
		return 2
	End

	Insert into dbo.Preforms(doctorID, treatmentID) Values(@doctorID, @treatmentID)
	return 0
End
