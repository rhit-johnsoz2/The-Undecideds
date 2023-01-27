create procedure UpdateHCP(@OldID int, @Name varchar(50))
As
Begin
Update HealthCareProvider
	Set name = @Name
	Where ID = @OldID
End