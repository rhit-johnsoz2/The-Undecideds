USE [SymptomTracker]
GO
/****** Object:  StoredProcedure [dbo].[UpdatePerforms]    Script Date: 2/2/2023 5:29:49 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER procedure [dbo].[UpdatePerforms](@doctorID int, @patientID int)
As 
Begin 

if(@doctorID is null or @patientID is null)
Begin
	RAISERROR('Input Arguments cannot be null',14,1)
	return 1;
End

if not exists(select * from Person where ID = @doctorID)
Begin
	RAISERROR('Doctor does not exisit',14,1)
	return 2;	
End

Update DoctorFor
	set doctorID = @doctorID
	where patientID = @patientID
	return 0;

End
