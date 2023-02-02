USE [SymptomTracker]
GO
/****** Object:  StoredProcedure [dbo].[UpdateDoctorFor]    Script Date: 2/2/2023 5:26:51 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER procedure [dbo].[UpdateDoctorFor](@personID int, @doctorID int)
As
Begin

if(@personID is null or @doctorID is null)
	Begin
		RAISERROR('Cannot support null attribute', 14, 1)
		return 1;
	End

Update DoctorFor
	Set doctorID = @doctorID
	Where patientID = @personID
	return 0;
End
