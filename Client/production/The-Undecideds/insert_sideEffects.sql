USE [SymptomTracker]
GO
/****** Object:  StoredProcedure [dbo].[InsertSideEffectOf]    Script Date: 2/9/2023 2:26:13 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER procedure [dbo].[InsertSideEffectOf](@SymptomID int, @TreatmentID int)
As
Begin
	if(@SymptomID is null or @TreatmentID is null)
	Begin
		raiserror('Input Arguments cannot be null', 14,1)
		return 1
	End
	if not exists(select * from Symptom where Symptom.ID = @SymptomID)
	Begin
		raiserror('Symptom does not exist', 14,1)
		return 2
	End
	if not exists(select * from Treatment where Treatment.ID = @TreatmentID)
	Begin
		raiserror('Treatment does not exist', 14,1)
		return 3
	End 
	Insert into dbo.SideEffectOf(SymptomID, TreatmentID) Values(@SymptomID, @TreatmentID)
	return 0
End
