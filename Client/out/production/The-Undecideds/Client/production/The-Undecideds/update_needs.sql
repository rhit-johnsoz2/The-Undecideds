USE [SymptomTracker]
GO
/****** Object:  StoredProcedure [dbo].[UpdateNeeds]    Script Date: 2/2/2023 5:28:42 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER procedure [dbo].[UpdateNeeds](@PersonId int, @Treatment int, @Start date, @end date)
As
Begin

if(@personID is null or @Treatment is null or @Start is null or @end is null)
	Begin
		RAISERROR('Cannot support null attribute', 14, 1)
		return 1;
	End

if(@end < @Start)
	Begin
		RAISERROR('End date is earlier than start date', 14, 1)
		return 2;
	End

Update Needs
	Set SDate = @Start,
		EDate = @end
	Where PatientID = @PersonId and TreatmentID = @Treatment
	return 0;
End
