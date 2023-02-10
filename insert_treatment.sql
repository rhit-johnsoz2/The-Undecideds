USE [SymptomTracker]
GO
/****** Object:  StoredProcedure [dbo].[InsertTreatment]    Script Date: 2/9/2023 5:38:25 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER procedure [dbo].[InsertTreatment](@Cost int, @name varchar(30))
As
Begin
	if(@Cost is null or @name is null)
	Begin
		raiserror('Input Arguments cannot be null', 14,1)
		return 1
	End
	Insert into dbo.Treatment(Cost, name) Values(@Cost, @name)
	return 0
End
