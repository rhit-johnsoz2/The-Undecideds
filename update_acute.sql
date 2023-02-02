USE [SymptomTracker]
GO
/****** Object:  StoredProcedure [dbo].[UpdateAcuteSymptom]    Script Date: 2/2/2023 5:25:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER procedure [dbo].[UpdateAcuteSymptom](@personID int, @severity int, @time timestamp)
As
Begin
if(@personID is null or @severity is null or @time is null)
	Begin
		raiserror('Input Arguments cannot be null', 14,1)
		return 1;
	End
if(@severity > 10)
	Begin
		raiserror('Severity is outside scope',14,1)
		return 2;
	End
if(@time < GETDATE())
	Begin
		raiserror('Time frame is not avaliable',14,1)
		return 3;
	End

Update Acute
	Set severity = @severity
	Where personID = @personID
	return 0;
End
