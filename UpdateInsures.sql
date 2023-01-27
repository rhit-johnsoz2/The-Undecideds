create procedure UpdateInsures(@PersonId int, @HCPID int, @Treatment int, @newCost int)
As
Begin
	Update Insures
	Set Coverage = @newCost
	Where PersonID = @PersonId and HCPID = @HCPID and TreatmentID = @Treatment
End