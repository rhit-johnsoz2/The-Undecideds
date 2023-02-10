USE [SymptomTracker]
GO
/****** Object:  StoredProcedure [dbo].[UpdateInsures]    Script Date: 2/2/2023 5:27:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER procedure [dbo].[UpdateInsures](@PersonId int, @HCPID int, @Treatment int, @newCost int)
As
Begin

if(@personID is null or @HCPID is null or @Treatment is null or @newCost is null)
	Begin
		RAISERROR('Cannot support null attribute', 14, 1)
		return 1;
	End

Update Insures
	Set Coverage = @newCost
	Where PersonID = @PersonId and HCPID = @HCPID and TreatmentID = @Treatment
	return 0;
End
