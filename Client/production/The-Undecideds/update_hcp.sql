USE [SymptomTracker]
GO
/****** Object:  StoredProcedure [dbo].[UpdateHCP]    Script Date: 2/2/2023 5:27:22 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER procedure [dbo].[UpdateHCP](@OldID int, @Name varchar(50))
As
Begin

if(@OldID is null or @Name is null)
	Begin
		RAISERROR('Cannot support null attribute', 14, 1)
		return 1;
	End

Update HealthCareProvider
	Set name = @Name
	Where ID = @OldID
	return 0;
End
