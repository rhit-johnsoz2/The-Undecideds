USE [SymptomTracker]
GO
/****** Object:  StoredProcedure [dbo].[UpdateTreatment]    Script Date: 2/9/2023 12:59:48 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER procedure [dbo].[UpdateTreatment](@treatmentID int, @cost int, @name varchar(40))
As
Begin 

	if(@treatmentID is null or @name is null or @cost is null)
	Begin
		RAISERROR('Inputs cannot be null',14,1)
		return 1;
	End

	if not Exists(select * from Treatment where ID = @treatmentID)
	Begin
		RAISERROR('Treatment does not exist and cannot be updated',14,1)
		return 2;
	End

	Update Treatment
		Set	Cost = @cost,
		    name = @name
		where ID = @treatmentID
		return 0;
End
