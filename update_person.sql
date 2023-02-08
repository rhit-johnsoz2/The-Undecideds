USE [SymptomTracker]
GO
/****** Object:  StoredProcedure [dbo].[UpdatePerson]    Script Date: 2/2/2023 5:29:52 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

ALTER procedure [dbo].[UpdatePerson](@ID int, @fn varchar(30), @ln varchar(30),
					@login varchar(30), @password varchar(30), @role char(2),
					@hcpID int)
As
Begin 

	if(@ID is null or @fn is null or @ln is null or
					@login is null or @password is null or @role is null or
					@hcpID is null)
	Begin
		RAISERROR('Inputs cannot be null',14,1)
		return 1;
	End

	if not Exists(select * from Person where ID = @ID)
	Begin
		RAISERROR('Person does not exist and cannot be updated',14,1)
		return 2;
	End

	Update Person
		Set lname = @ln,
			fname = @fn,
			password = @password,
			role = @role,
			hcpID = @hcpID
		where ID = @ID
		return 0;
End
