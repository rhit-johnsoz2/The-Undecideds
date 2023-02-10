USE [SymptomTracker]
GO
/****** Object:  StoredProcedure [dbo].[UpdateSymptom]    Script Date: 2/2/2023 5:29:55 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER procedure [dbo].[UpdateSymptom](@treatmentID int, @name varchar(40))
As
Begin 

	if(@treatmentID is null or @name is null)
	Begin
		RAISERROR('Inputs cannot be null',14,1)
		return 1;
	End

	if not Exists(select * from Symptom where ID = @treatmentID)
	Begin
		RAISERROR('Symptom does not exisit and cannot be updated',14,1)
		return 2;
	End

	Update Symptom
		Set name = @name
		where ID = @treatmentID
		return 0;
End
