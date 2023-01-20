use [SymptomTracker]
go

--Testing Person Insert
DECLARE @status int
Exec @status = dbo.InsertPerson @ID = 2, @fname ='Liz', @lname = 'Fogarty', @Login = 'lfogarty', @password = 'password', @role = 'PA', @InsuredBy = 'HCP'
SELECT @status

--Testing Preform Insert
DECLARE @status2 int
Exec @status2 = dbo.InsertPreform @doctorID = 13, @treatmentID = 13
SELECT @status2

--Testing SideEffectOf Insert
DECLARE @status3 int
Exec @status3 = dbo.InsertSideEffectOf @SymptomID = 2, @TreatmentID = 13
SELECT @status3

--Testing Symptom Insert
DECLARE @status4 int
Exec @status4 = dbo.InsertSymptom @ID = 1, @Name = 'Cough'
SELECT @status4

--Testing Treatment Insert 
DECLARE @status5 int
Exec @status5 = dbo.InsertTreatment @ID = 1, @Cost = 100
SELECT @status5