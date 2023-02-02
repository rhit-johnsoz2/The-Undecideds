USE [SymptomTracker]
GO
/****** Object:  StoredProcedure [dbo].[UpdateTreatment]    Script Date: 2/2/2023 5:29:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER procedure [dbo].[UpdateTreatment](@treatmentID int,@cost decimal(19,4), @name varchar(40))
As
Begin 

	if(@treatmentID is null or @name is null or @cost is null)
	Begin
		RAISERROR('Inputs cannot be null',14,1)
		return 1;
	End

	if not Exists(select * from Treatment where ID = @treatmentID)
	Begin
		RAISERROR('Treatment does not exisit and cannot be updated',14,1)
		return 2;
	End

	Update Treatment
		Set name = @name,
			Cost = @cost
		where ID = @treatmentID
		return 0;
End
