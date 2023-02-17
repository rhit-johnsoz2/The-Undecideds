:setvar dbName SzmTracker2

USE master
GO

CREATE DATABASE $(dbName) ON (
    NAME = N'$(dbName)',
    FILENAME = 'D:\Database\MSSQL15.MSSQLSERVER\MSSQL\DATA\$(dbName)',
    SIZE = 6MB,  
    MAXSIZE = 30MB,  
    FILEGROWTH = 12%
)
LOG ON (
    NAME = N'$(dbName)_log',
    FILENAME = 'D:\Database\MSSQL15.MSSQLSERVER\MSSQL\DATA\$(dbName).ldf',
    SIZE = 3MB,
    MAXSIZE = 30MB,
    FILEGROWTH = 10%
)
COLLATE SQL_Latin1_General_Cp1_CI_AS
GO

USE $(dbName)
GO

CREATE USER [cerasoml] FROM LOGIN [cerasoml]
EXEC sp_addrolemember 'db_owner', 'cerasoml'

CREATE USER [fogartee] FROM LOGIN [fogartee]
EXEC sp_addrolemember 'db_owner', 'fogartee'
GO

CREATE USER [SymptomTrackerDemoAccount] FROM LOGIN [SymptomTrackerDemoAccount]
GRANT EXEC TO [SymptomTrackerDemoAccount]
GO

USE $(dbName)
GO

CREATE TABLE HealthCareProvider (
	ID int identity(1,1),
	name varchar(50),
	PRIMARY KEY(ID),
	unique(name)
)
GO

CREATE TABLE Person (
	ID Integer Identity(1, 1) PRIMARY KEY,
	fname varchar(30),
	lname varchar(30),
	login varchar(30) UNIQUE,
	password varchar(30),
	role char(2),
	hcpID Integer REFERENCES dbo.HealthCareProvider
)
GO

CREATE TABLE Treatment(
	ID Integer Identity(1,1) Primary Key,
	Cost Integer,
	name varchar(50),
	unique(name)
)
GO

CREATE TABLE Symptom (
	ID int identity(1,1),
	name varchar(40),
	primary key(ID),
	unique(name)
)
GO

CREATE TABLE Insures(
	PersonID Integer REFERENCES dbo.Person(ID),
	HCPID Integer REFERENCES dbo.HealthCareProvider(ID),
	TreatmentID Integer REFERENCES dbo.Treatment(ID),
	Coverage Integer,
	Primary Key (PersonID, HCPID, TreatmentID)
)
GO

--CHECK((SELECT role FROM dbo.Person WHERE ID = doctorID) = 'DR')
CREATE TABLE Needs(
	PatientID Integer REFERENCES dbo.Person(ID),
	TreatmentID Integer REFERENCES dbo.Treatment(ID),
	SDate Date,
	EDate Date,
	Primary key(PatientID, TreatmentID)
)
GO

--CHECK((SELECT role FROM dbo.Person WHERE ID = doctorID) = 'DR')
CREATE TABLE DoctorFor(
	doctorID Integer REFERENCES dbo.Person,
	patientID Integer REFERENCES dbo.Person,
	primary key(doctorID, patientID)
)
GO

--CHECK((SELECT role FROM dbo.Person WHERE ID = doctorID) = 'DR')
CREATE TABLE Performs (
	doctorID integer,
	treatmentID integer,
	primary key(doctorID, treatmentID),
	foreign key(doctorID) references person(ID),
	foreign key(treatmentID) references treatment(ID)
)
GO

CREATE TABLE SideEffectOf (
	symptomID integer,
	treatmentID integer,
	primary key(symptomID, treatmentID),
	foreign key(symptomID) references symptom(ID),
	foreign key(treatmentID) references treatment(ID)
)
GO

CREATE TABLE Acute (
	personID integer,
	symptomID integer,
	severity integer,
	symptomDate date,
	primary key(personID, symptomID, symptomDate),
	foreign key(personID) references person(ID),
	foreign key(symptomID) references symptom(ID)
)
GO

CREATE TABLE Chronic(
	PersonID Integer REFERENCES dbo.Person(ID),
	SymptomID Integer REFERENCES dbo.Symptom(ID),
	primary key (PersonID, SymptomID)
)
GO

CREATE VIEW SymptomNames AS 
(SELECT NAME, ID FROM Symptom)
GO
CREATE PROCEDURE GetSymptomNames AS
SELECT * FROM SymptomNames
GO

CREATE VIEW PersonNames AS
(SELECT CONCAT(FNAME, ' ', LNAME) AS NAME, ID FROM Person)
GO
CREATE PROCEDURE GetPersonNames AS
SELECT * FROM PersonNames
GO

CREATE VIEW PatientNames AS
(SELECT CONCAT(FNAME, ' ', LNAME) AS NAME, ID FROM Person WHERE Person.role = 'PA')
GO
CREATE PROCEDURE GetPatientNames AS
SELECT * FROM PatientNames
GO

CREATE VIEW DoctorNames AS
(SELECT CONCAT(FNAME, ' ', LNAME) AS NAME, ID FROM Person WHERE Person.role = 'DR')
GO
CREATE PROCEDURE GetDoctorNames AS
SELECT * FROM DoctorNames
GO

CREATE VIEW HCPNames AS
(SELECT NAME, ID FROM HealthCareProvider)
GO
CREATE PROCEDURE GetHCPNames AS
SELECT * FROM HCPNames
GO

CREATE VIEW TreatmentNames AS
(SELECT NAME, ID FROM Treatment)
GO
CREATE PROCEDURE GetTreatmentNames AS
SELECT * FROM TreatmentNames
GO

CREATE VIEW AllAcuteSymptomsOnPatient
AS
SELECT Patient.ID as PID, S.name, S.ID
FROM Person Patient JOIN Acute A
On Patient.ID = A.personID
JOIN Symptom S on A.symptomID = S.ID
WHERE Patient.role = 'PA'
GO

CREATE VIEW AllChronicSymptomsOnPatient
AS
SELECT Patient.ID as PID, S.name, S.ID
FROM Person Patient JOIN Chronic C
On Patient.ID = C.personID
JOIN Symptom S on C.symptomID = S.ID
WHERE Patient.role = 'PA'
GO

CREATE VIEW DoctorView
AS
SELECT Doctor.ID AS DoctorID, Patient.fname, Patient.lname, Patient.ID
FROM Person Doctor JOIN DoctorFor DF
On Doctor.ID = DF.doctorID
JOIN Person Patient on DF.patientID = Patient.ID
WHERE Doctor.role = 'DR' AND Patient.role = 'PA'
GO

CREATE VIEW NotDoctorForView
AS
SELECT Doctor.ID AS DoctorID, Patient.fname, Patient.lname, Patient.ID
FROM Person Doctor JOIN Person Patient On NOT EXISTS (SELECT *
													  FROM DoctorView
													  WHERE DoctorID = Doctor.ID and ID = Patient.ID)
WHERE Doctor.role = 'DR' AND Patient.role = 'PA'
GO

CREATE VIEW AllDoctorsInHCP
AS
SELECT HCP.ID as HCPID, Doctor.fname, Doctor.lname, Doctor.ID
FROM Person Doctor JOIN Insures I
on Doctor.ID = I.PersonID
JOIN HealthCareProvider HCP on I.HCPID = HCP.ID
WHERE Doctor.role = 'DR'
GO

CREATE VIEW DoctorsWithTreatment
AS
SELECT T.ID as TreatmentID, Doctor.fname, Doctor.lname, Doctor.ID
FROM Person Doctor JOIN Performs P
On Doctor.ID = P.doctorID
JOIN Treatment T on P.treatmentID = T.ID
WHERE Doctor.role = 'DR'
GO

CREATE VIEW PatientsOnDoctorView
As
SELECT Doctor.ID as DoctorID, Patient.ID, Patient.fname, Patient.lname
FROM Person Doctor JOIN DoctorFor DF
on Doctor.ID = DF.doctorID
JOIN Person Patient on DF.patientID = Patient.ID
WHERE Doctor.role = 'DR'
GO

CREATE VIEW PatientAcuteSymptomView
AS
SELECT Patient.ID AS PatientID, Acute.severity, Acute.symptomDate, Symptom.name
FROM Person Patient JOIN Acute ON Patient.ID = Acute.personID
JOIN Symptom ON Acute.symptomID = Symptom.ID
GO

CREATE VIEW PatientChronicSymptomView
AS
SELECT Patient.ID AS PatientID, Symptom.name
FROM Person Patient JOIN Chronic ON Patient.ID = Chronic.personID
JOIN Symptom ON Chronic.symptomID = Symptom.ID
GO

CREATE VIEW PatientTreatmentView
AS
SELECT Patient.ID AS PatientID, Needs.SDate, Needs.EDate, Treatment.Cost, Treatment.name
FROM Person Patient JOIN Needs ON Patient.ID = Needs.PatientID
JOIN Treatment ON Treatment.ID = Needs.TreatmentID
GO

CREATE PROCEDURE [AcuteFromPatient](
	@PID Integer
) AS BEGIN
	SELECT PatientID, severity, symptomDate, name
	FROM PatientAcuteSymptomView
	WHERE PatientID = @PID
END
GO

CREATE PROCEDURE [ChronicFromPatient](
	@PID Integer
) AS BEGIN
	SELECT PatientID, name
	FROM PatientChronicSymptomView
	WHERE PatientID = @PID
END
GO

CREATE PROCEDURE [TreatmentFromPatient](
	@PID Integer
) AS BEGIN
	SELECT PatientID, SDate, EDate, Cost, name
	FROM PatientTreatmentView
	WHERE PatientID = @PID
END
GO

Create Trigger CheckPersonRole on Person
AFTER Insert
AS
If (Select role from inserted) != 'PA' and (Select role from inserted) != 'DR'
Begin
	RAISERROR('People can only be patients or doctors.', 14, 1)
	ROLLBACK TRANSACTION
End
GO

Create Trigger DoctorsOnlyForInsures on Insures
AFTER Insert
AS
DECLARE @doc Integer
SET @doc = (Select PersonID from inserted)
DECLARE @treatment Integer
SET @treatment = (Select TreatmentID from inserted)
If (NOT EXISTS(SELECT * FROM DoctorNames WHERE ID = @doc))
Begin
	RAISERROR('Only doctors can be insured.', 14, 1)
	ROLLBACK TRANSACTION
End
IF (NOT EXISTS(SELECT * FROM Performs WHERE doctorID = @doc and treatmentID = @treatment))
Begin
	RAISERROR('Doctor must perform treatment.', 14, 1)
	ROLLBACK TRANSACTION
End
GO

Create Trigger PatientsOnlyForAcuteSymptoms on Acute
AFTER Insert
AS
DECLARE @pat Integer
SET @pat = (Select PersonID from inserted)
If (NOT EXISTS(SELECT * FROM PatientNames WHERE ID = @pat))
Begin
	RAISERROR('Only patients can be sick.', 14, 1)
	ROLLBACK TRANSACTION
End
GO

Create Trigger PatientsOnlyForChronicSymptoms on Chronic
AFTER Insert
AS
DECLARE @pat Integer
SET @pat = (Select PersonID from inserted)
If (NOT EXISTS(SELECT * FROM PatientNames WHERE ID = @pat))
Begin
	RAISERROR('Only patients can be sick.', 14, 1)
	ROLLBACK TRANSACTION
End
GO

CREATE PROCEDURE [dbo].[InsertHealthCareProviders]
	(@name varchar(50))
AS
BEGIN
	-- check to see if any of the parameters are null
	IF(@name is NULL or @name = '')
	BEGIN
		RAISERROR('Input arguments cannot be null', 14, 1)
		Return 1
	END
INSERT INTO HealthCareProvider Values(@name)
Return 0
END
GO

CREATE PROCEDURE InsertPerson(@FName varchar(30), @LName varchar(30), 
@Login varchar(30), @Password varchar(30), @Role char(2), @InsuredBy Integer)
AS
Begin
	if (@FName is null or @LName is null or @Login is null or @Password is null or @Role is null or @InsuredBy is null or @FName = '' or @LName = '' or @Login = '' or @Password = '' or @Role = '')
	Begin
		raiserror('Input Arguments cannot be null', 14,1)
		return 1
	End

	Insert into dbo.Person(FName, LName, Login, Password, role, hcpID) 
				Values(@FName, @LName, @Login, @Password, @Role, @InsuredBy)
	return 0
End
GO

CREATE PROCEDURE InsertSymptom(@Name varchar(50))
As
Begin
	if(@Name is null or @Name = '')
	Begin
		raiserror('Input Arguments cannot be null', 14,1)
		return 1
	End

	Insert into dbo.Symptom(Name) Values(@Name)
	return 0
End
GO

CREATE PROCEDURE [dbo].[InsertTreatment](@Cost int, @name varchar(30))
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
GO

CREATE PROCEDURE InsertAcute
	(@personID Integer, @symptomID Integer,
	 @severity Integer, @symptomDate date = GETDATE)
AS
BEGIN
	-- check to see if any of the parameters are null
	IF(@personID is NULL or @symptomID is NULL or @severity is NULL or @symptomDate is NULL)
	BEGIN
		RAISERROR('Input arguments cannot be null', 14, 1)
		Return 1
	END
	-- check to see if the person exists in the person database
	IF(NOT EXISTS (SELECT * FROM Person WHERE Person.ID = @personID))
	BEGIN
		RAISERROR('Person not in the person database', 14, 1)
		Return 2
	END
	-- check to see if the symptom exists in the symptom database
	IF(NOT EXISTS (SELECT * FROM Symptom WHERE Symptom.ID = @symptomID))
	BEGIN
		RAISERROR('Symptom not in the person database', 14, 1)
		Return 3
	END
INSERT INTO Acute Values(@personID, @symptomID, @severity, @symptomDate)
Return 0
END
GO

CREATE PROCEDURE InsertChronic
	(@personID integer, @symptomID integer)
AS
BEGIN
	-- check to see if any of the parameters are null
	IF(@personID is NULL or @symptomID is NULL)
	BEGIN
		RAISERROR('Input arguments cannot be null', 14, 1)
		Return 1
	END
	-- check to see if the person exists in the person database
	IF(NOT EXISTS (SELECT * FROM Person WHERE ID = @personID))
	BEGIN
		RAISERROR('Person not in the person database', 14, 1)
		Return 2
	END
	-- check to see if the symptom exists in the symptom database
	IF(NOT EXISTS (SELECT * FROM Symptom WHERE ID = @symptomID))
	BEGIN
		RAISERROR('Symptom not in the symptom database', 14, 1)
		Return 3
	END
INSERT INTO Chronic Values(@personID, @symptomID)
Return 0
END
GO

CREATE PROCEDURE [dbo].[InsertDoctorFor]
	(@doctorID Integer, @patientID Integer)
AS
BEGIN
	-- check to see if any of the parameters are null
	IF(@doctorID is NULL or @patientID is NULL)
	BEGIN
		RAISERROR('Input arguments cannot be null', 14, 1)
		Return 1
	END
	-- check to see if the doctor exists in the patient database
	IF(NOT EXISTS (SELECT * FROM Person WHERE Person.ID = @doctorID and Person.role ='DR'))
	BEGIN
		RAISERROR('Doctor not in the person database', 14, 1)
		Return 2
	END
	-- check to see if the patient exists in the person database
	IF(NOT EXISTS (SELECT * FROM Person WHERE Person.ID = @patientID and Person.role = 'PA'))
	BEGIN
		RAISERROR('Patient not in the HCP database', 14, 1)
		Return 3
	END
	-- duplicate value
	IF (EXISTS (SELECT * FROM DoctorFor WHERE doctorID = @doctorID and patientID = @patientID))
	BEGIN
		RAISERROR('Relationship already exists', 14, 1)
		Return 4
	END
INSERT INTO DoctorFor Values(@doctorID, @patientID)
Return 0
END
GO

CREATE PROCEDURE InsertInsures
	(@personID integer, @hcpID integer, 
	 @treatmentID integer, @coverage integer)
AS
BEGIN
	-- check to see if any of the parameters are null
	IF(@personID is NULL or @hcpID is NULL or @treatmentID is NULL or @coverage is NULL)
	BEGIN
		RAISERROR('Input arguments cannot be null', 14, 1)
		Return 1
	END
	-- check to see if the person exists in the person database
	IF(NOT EXISTS (SELECT * FROM Person WHERE ID = @personID))
	BEGIN
		RAISERROR('Person not in the person database', 14, 1)
		Return 2
	END
	-- check to see if the hcp exists in the hcp database
	IF(NOT EXISTS (SELECT * FROM HealthCareProvider WHERE ID = @hcpID))
	BEGIN
		RAISERROR('Health Care Provider not in the HCP database', 14, 1)
		Return 3
	END
	-- check to see if the treatment exists in the treatment database
	IF(NOT EXISTS (SELECT * FROM Treatment WHERE ID = @treatmentID))
	BEGIN
		RAISERROR('Treatment not in the treatment database', 14, 1)
		Return 4
	END
INSERT INTO Insures Values(@personID, @hcpID, @treatmentID, @coverage)
Return 0
END
GO

CREATE PROCEDURE InsertNeeds
	(@patientID Integer, @treatmentID Integer, 
	 @sDate Date, @eDate Date)
AS
BEGIN
	-- check to see if any of the parameters are null
	IF(@patientID is NULL or @treatmentID is NULL or @sDate is NULL)
	BEGIN
		RAISERROR('Input arguments cannot be null', 14, 1)
		Return 1
	END
	-- check to see if the person exists in the person database
	IF(NOT EXISTS (SELECT * FROM Person WHERE ID = @patientID))
	BEGIN
		RAISERROR('Patient not in the person database', 14, 1)
		Return 2
	END
	-- check to see if the treatment exists in the treatment database
	IF(NOT EXISTS (SELECT * FROM Treatment WHERE ID = @treatmentID))
	BEGIN
		RAISERROR('Treatment not in the treatment database', 14, 1)
		Return 3
	END
	-- check to see if the end date is before the start date
	IF(@eDate < @sDate)
	BEGIN
		RAISERROR('End date happens before start date', 14, 1)
		Return 4
	END
INSERT INTO Needs Values(@patientID, @treatmentID, @sDate, @eDate)
Return 0
END
GO

CREATE PROCEDURE [dbo].[InsertPerforms](@doctorID Integer, @treatmentID Integer)
AS
Begin
	if(@doctorID is null or @treatmentID is null)
	Begin
		raiserror('Input Arguments cannot be null', 14,1)
		return 1
	End

	if not exists(select * from Treatment where Treatment.ID = @treatmentID)
	Begin
		raiserror('Treatment does not exist', 14,1)
		return 2
	End 

	if not exists(select * from Person where Person.ID = @doctorID and Person.role = 'DR')
	Begin
		raiserror('Doctor does not exist', 14,1)
		return 2
	End

	Insert into dbo.Performs(doctorID, treatmentID) Values(@doctorID, @treatmentID)
	return 0
End
GO

CREATE PROCEDURE [dbo].[InsertSideEffectOf](@SymptomID int, @TreatmentID int)
As
Begin
	if(@SymptomID is null or @TreatmentID is null)
	Begin
		raiserror('Input Arguments cannot be null', 14,1)
		return 1
	End
	if not exists(select * from Symptom where Symptom.ID = @SymptomID)
	Begin
		raiserror('Symptom does not exist', 14,1)
		return 2
	End
	if not exists(select * from Treatment where Treatment.ID = @TreatmentID)
	Begin
		raiserror('Treatment does not exist', 14,1)
		return 3
	End 
	Insert into dbo.SideEffectOf(SymptomID, TreatmentID) Values(@SymptomID, @TreatmentID)
	return 0
End
GO

CREATE PROCEDURE [dbo].[UpdateHCP](@OldID int, @Name varchar(50))
As
Begin

if(@OldID is null or @Name is null or @Name = '')
	Begin
		RAISERROR('Cannot support null attribute', 14, 1)
		return 1;
	End

Update HealthCareProvider
	Set name = @Name
	Where ID = @OldID
	return 0;
End
GO

CREATE PROCEDURE [dbo].[UpdatePerson](@ID int, @fn varchar(30), @ln varchar(30),
					@login varchar(30), @password varchar(30), @role char(2),
					@hcpID int)
As
Begin 

	if(@ID is null or @fn is null or @ln is null or
					@login is null or @password is null or @role is null or
					@hcpID is null or @fn = '' or @ln = '' or
					@login = '' or @password = '' or @role = '')
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
			login = @login,
			password = @password,
			role = @role,
			hcpID = @hcpID
		where ID = @ID
		return 0;
End
GO

CREATE PROCEDURE [dbo].[UpdateSymptom](@treatmentID int, @name varchar(40))
As
Begin 

	if(@treatmentID is null or @name is null)
	Begin
		RAISERROR('Inputs cannot be null',14,1)
		return 1;
	End

	if not Exists(select * from Symptom where ID = @treatmentID)
	Begin
		RAISERROR('Symptom does not exisit and cannot be updated',14,1)
		return 2;
	End

	Update Symptom
		Set name = @name
		where ID = @treatmentID
		return 0;
End
GO

CREATE PROCEDURE [dbo].[UpdateTreatment](@treatmentID int, @cost int, @name varchar(40))
As
Begin 

	if(@treatmentID is null or @name is null or @cost is null or @name = '')
	Begin
		RAISERROR('Inputs cannot be null',14,1)
		return 1;
	End

	if not Exists(select * from Treatment where ID = @treatmentID)
	Begin
		RAISERROR('Treatment does not exist and cannot be updated',14,1)
		return 2;
	End

	Update Treatment
		Set	Cost = @cost,
		    name = @name
		where ID = @treatmentID
		return 0;
End
GO

CREATE PROCEDURE [dbo].[UpdateAcuteSymptom](@personID int, @symptomID int, @severity int, @date date)
As
Begin
if(@personID is null or @symptomID is null or @severity is null or @date is null)
	Begin
		raiserror('Input Arguments cannot be null', 14,1)
		return 1;
	End
if(@severity > 10)
	Begin
		raiserror('Severity is outside scope',14,1)
		return 2;
	End
if(@date < GETDATE())
	Begin
		raiserror('Time frame is not avaliable',14,1)
		return 3;
	End

Update Acute
	Set severity = @severity,
	    symptomDate = @date
	Where personID = @personID and symptomID = @symptomID
	return 0;
End
GO

CREATE PROCEDURE [dbo].[UpdateInsures](@PersonId int, @HCPID int, @Treatment int, @newCost int)
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
GO

CREATE PROCEDURE [dbo].[UpdateNeeds](@PersonId int, @Treatment int, @Start date, @end date)
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
GO

CREATE PROCEDURE deleteHealthCareProvider
(@name varchar(50))
As
Begin
	if(@name is null)
	Begin
		Raiserror('Input Argument cannot be null', 14, 1)
		Return 1
	End
	if(NOT EXISTS(SELECT * FROM HealthCareProvider WHERE HealthCareProvider.name = @name))
	Begin
		Raiserror('Health Care Provider not in table', 14, 1)
		Return 2
	End
	Delete From HealthCareProvider
	Where HealthCareProvider.name = @name
End
GO

CREATE PROCEDURE deletePerson
(@FName varchar(30), @LName varchar(30))
As
Begin
	if(@FName is null or @LName is null)
	Begin
		Raiserror('Input Arguments cannot be null', 14, 1)
		Return 1
	End
	if(NOT EXISTS(SELECT * FROM Person WHERE Person.fname = @FName and Person.lname = @LName))
	Begin
		Raiserror('Person not in table', 14, 1)
		Return 2
	End
	Delete From Person
	Where Person.fname = @FName and Person.lname = @LName
End
GO

CREATE PROCEDURE deleteSymptom
(@name varchar(40))
As
Begin
	if(@name is null)
	Begin
		Raiserror('Input Argument cannot be null', 14, 1)
		Return 1
	End
	if(NOT EXISTS(SELECT * FROM Symptom WHERE Symptom.name = @name))
	Begin
		Raiserror('Symptom not in table', 14, 1)
		Return 2
	End
	Delete From Symptom
	Where Symptom.name = @name
End
GO

CREATE PROCEDURE deleteTreatment
(@name varchar(30))
As
Begin
	if(@name is null)
	Begin
		Raiserror('Input Argument cannot be null', 14, 1)
		Return 1
	End
	if(NOT EXISTS(SELECT * FROM Treatment WHERE Treatment.name = @name))
	Begin
		Raiserror('Treatment not in table', 14, 1)
		Return 2
	End
	Delete From Treatment
	Where Treatment.name = @name
End
GO

CREATE PROCEDURE deleteAcute
(@symptomID Integer, @personID Integer)
As
Begin
	if(@symptomID is null or @personID is null)
	Begin
		Raiserror('Input Arguments cannot be null', 14, 1)
		Return 1
	End
	if(NOT EXISTS(SELECT * FROM Acute WHERE Acute.symptomID = @symptomID and Acute.personID = @personID))
	Begin
		Raiserror('Acute relationship does not exist', 14, 1)
		Return 2
	End
	Delete From Acute
	Where Acute.symptomID = @symptomID and Acute.personID = @personID
End
GO

CREATE PROCEDURE deleteChronic
(@symptomID Integer, @personID Integer)
As
Begin
	if(@symptomID is null or @personID is null)
	Begin
		Raiserror('Input Arguments cannot be null', 14, 1)
		Return 1
	End
	if(NOT EXISTS(SELECT * FROM Chronic WHERE Chronic.symptomID = @symptomID and Chronic.personID = @personID))
	Begin
		Raiserror('Chronic relationship does not exist', 14, 1)
		Return 2
	End
	Delete From Chronic
	Where Chronic.symptomID = @symptomID and Chronic.personID = @personID
End
GO

CREATE PROCEDURE deleteDoctorFor
(@doctorID Integer, @patientID Integer)
As
Begin
	if(@doctorID is null or @patientID is null)
	Begin
		Raiserror('Input Arguments cannot be null', 14, 1)
		Return 1
	End
	if(NOT EXISTS(SELECT * FROM DoctorFor WHERE DoctorFor.doctorID = @doctorID and DoctorFor.patientID = @patientID))
	Begin
		Raiserror('Not a doctor for this patient', 14, 1)
		Return 2
	End
	Delete From DoctorFor
	Where DoctorFor.doctorID = @doctorID and DoctorFor.patientID = @patientID
End
GO

CREATE PROCEDURE deleteInsures
(@personID Integer, @hcpID Integer, @treatmentID Integer)
As
Begin
	if(@personID is null or @hcpID is null or @treatmentID is null)
	Begin
		Raiserror('Input Arguments cannot be null', 14, 1)
		Return 1
	End
	if(NOT EXISTS(SELECT * FROM Insures WHERE Insures.PersonID = @personID and Insures.HCPID = @hcpID and Insures.TreatmentID = @treatmentID))
	Begin
		Raiserror('Insures relationship does not exist', 14, 1)
		Return 2
	End
	Delete From Insures
	Where Insures.personID = @personID and Insures.HCPID = @hcpID and Insures.TreatmentID = @treatmentID
End
GO

CREATE PROCEDURE deleteNeeds
(@patientID Integer, @treatmentID Integer)
As
Begin
	if(@patientID is null or @treatmentID is null)
	Begin
		Raiserror('Input Arguments cannot be null', 14, 1)
		Return 1
	End
	if(NOT EXISTS(SELECT * FROM Needs WHERE Needs.patientID = @patientID and Needs.treatmentID = @treatmentID))
	Begin
		Raiserror('Needs relationship does not exist', 14, 1)
		Return 2
	End
	Delete From Needs
	Where Needs.patientID = @patientID and Needs.TreatmentID = @treatmentID
End
GO

CREATE PROCEDURE deletePerforms
(@doctorID Integer, @treatmentID Integer)
As
Begin
	if(@doctorID is null or @treatmentID is null)
	Begin
		Raiserror('Input Arguments cannot be null', 14, 1)
		Return 1
	End
	if(NOT EXISTS(SELECT * FROM Performs WHERE Performs.doctorID = @doctorID and Performs.treatmentID = @treatmentID))
	Begin
		Raiserror('Performs relationship does not exist', 14, 1)
		Return 2
	End
	Delete From Performs
	Where Performs.doctorID = @doctorID and Performs.TreatmentID = @treatmentID
End
GO

CREATE PROCEDURE deleteSideEffectOf
(@symptomID Integer, @treatmentID Integer)
As
Begin
	if(@symptomID is null or @treatmentID is null)
	Begin
		Raiserror('Input Arguments cannot be null', 14, 1)
		Return 1
	End
	if(NOT EXISTS(SELECT * FROM SideEffectOf WHERE SideEffectOf.symptomID = @symptomID and SideEffectOf.treatmentID = @treatmentID))
	Begin
		Raiserror('SideEffectOf relationship does not exist', 14, 1)
		Return 2
	End
	Delete From SideEffectOf
	Where SideEffectOf.symptomID = @symptomID and SideEffectOf.treatmentID = @treatmentID
End
GO

CREATE PROCEDURE [dbo].[ImportHealthCareProvider]
	(@name varchar(50))
AS
BEGIN
	-- check to see if any of the parameters are null
	IF(@name is NULL or @name = '')
	BEGIN
		RAISERROR('Input arguments cannot be null', 14, 1)
		Return 1
	END
INSERT INTO HealthCareProvider Values(@name)
Select ID From HealthCareProvider Where ID = @@IDENTITY
END
GO

CREATE PROCEDURE ImportPerson(@FName varchar(30), @LName varchar(30), @Login varchar(30), @Password varchar(30), @Role char(2), @InsuredBy Integer)
AS
Begin
	if (@FName is null or @LName is null or @Login is null or @Password is null or @Role is null or @InsuredBy is null or
		@FName = '' or @LName = '' or @Login = '' or @Password = '' or @Role = '')
	Begin
		raiserror('Input Arguments cannot be null', 14,1)
		return 1
	End

	Insert into dbo.Person(FName, LName, Login, Password, role, hcpID) Values(@FName, @LName, @Login, @Password, @Role, @InsuredBy)
	Select ID From Person Where ID = @@IDENTITY
End
GO

CREATE PROCEDURE ImportSymptom(@Name varchar(50))
As
Begin
	if(@Name is null or @Name = '')
	Begin
		raiserror('Input Arguments cannot be null', 14,1)
		return 1
	End

	Insert into dbo.Symptom(Name) Values(@Name)
	Select ID From Symptom Where ID = @@IDENTITY
End
GO

CREATE PROCEDURE ImportTreatment(@Cost int, @name varchar(30))
As
Begin
	if(@Cost is null or @name is null or @name = '')
	Begin
		raiserror('Input Arguments cannot be null', 14,1)
		return 1
	End
	Insert into dbo.Treatment(Cost, name) Values(@Cost, @name)
	Select ID From Treatment Where ID = @@IDENTITY
End
GO

CREATE PROCEDURE getPasswordByLogin
(@login varchar(50))
As
BEGIN
	if(@login is null)
	BEGIN
		Raiserror('Login can not be null', 14, 1)
		Return 1
	END
	if(NOT EXISTS (SELECT * FROM Person WHERE login = @login))
	BEGIN
		Raiserror('Login is not in table', 14, 1)
		Return 2
	END
	SELECT password
	FROM Person
	WHERE Person.login = @login
END
GO

CREATE PROCEDURE getIDByLogin
(@login varchar(50))
As
BEGIN
	if(@login is null)
	BEGIN
		Raiserror('Login can not be null', 14, 1)
		Return 1
	END
	if(NOT EXISTS (SELECT * FROM Person WHERE login = @login))
	BEGIN
		Raiserror('Login is not in table', 14, 1)
		Return 2
	END
	SELECT ID
	FROM Person
	WHERE Person.login = @login
END
GO

CREATE PROCEDURE personGetNameByID
(@id Integer)
As
BEGIN
	if(@id is null)
	BEGIN
		Raiserror('ID can not be null', 14, 1)
		Return 1
	END
	if(NOT EXISTS (SELECT * FROM Person WHERE ID = @id))
	BEGIN
		Raiserror('ID is not in table', 14, 1)
		Return 2
	END
	SELECT CONCAT(FName, ' ', LName) as Name
	FROM Person
	WHERE Person.ID = @id
END
GO

CREATE PROCEDURE personGetRoleByID
(@id Integer)
As
BEGIN
	if(@id is null)
	BEGIN
		Raiserror('ID can not be null', 14, 1)
		Return 1
	END
	if(NOT EXISTS (SELECT * FROM Person WHERE ID = @id))
	BEGIN
		Raiserror('ID is not in table', 14, 1)
		Return 2
	END
	SELECT role as Name
	FROM Person
	WHERE Person.ID = @id
END
GO

CREATE PROCEDURE GetPatientsFromDoctor 
(@doctorID Integer)
AS
	IF(@doctorID is null)
	BEGIN
		RAISERROR('Doctor ID does not exist.', 14, 1)
		Return 1
	END
	IF(NOT EXISTS(SELECT * FROM DoctorNames WHERE @doctorID = ID))
	BEGIN
		RAISERROR('Doctor does not exist.', 14, 1)
		Return 2
	END
	SELECT CONCAT(FName, ' ', LName) as Name, ID FROM DoctorView WHERE DoctorID != @doctorID
GO

CREATE PROCEDURE GetPatientsNotFromDoctor
(@doctorID Integer)
AS
	IF(@doctorID is null)
	BEGIN
		RAISERROR('Doctor ID does not exist.', 14, 1)
		Return 1
	END
	IF(NOT EXISTS(SELECT * FROM DoctorNames WHERE @doctorID = ID))
	BEGIN
		RAISERROR('Doctor does not exist.', 14, 1)
		Return 2
	END
	SELECT CONCAT(FName, ' ', LName) as Name, ID FROM NotDoctorForView WHERE DoctorID = @doctorID
GO

CREATE PROCEDURE GetPastTreatments
(@pID Integer)
AS
SELECT T.name as Name, Needs.SDate as [Start Date], Needs.EDate as [End Date]
FROM Needs JOIN Treatment T on Needs.TreatmentID = T.ID
WHERE PatientID = @pID and EDate < GETDATE()
GO

CREATE PROCEDURE GetCurrentTreatments
(@pID Integer)
AS
SELECT T.ID as ID, T.name as Name, Needs.SDate as [Start Date]
FROM Needs JOIN Treatment T on Needs.TreatmentID = T.ID
WHERE PatientID = @pID and (EDate >= GETDATE() or EDate is null)
GO

CREATE PROCEDURE GetTreatmentsFromID
(@tID Integer)
AS
SELECT * FROM Treatment WHERE ID = @tID
GO

CREATE VIEW DoctorsUnderPatientView
As
SELECT Patient.ID as PatientID, Doctor.ID, Doctor.fname, Doctor.lname
FROM Person Patient JOIN DoctorFor DF
on Patient.ID = DF.patientID
JOIN Person Doctor on DF.doctorID = Doctor.ID
WHERE Patient.role = 'PA'
GO

CREATE PROCEDURE GetDoctorsFromPatient
(@patientID Integer)
AS
	IF(@patientID is null)
	BEGIN
		RAISERROR('Patient ID does not exist.', 14, 1)
		Return 1
	END
	IF(NOT EXISTS(SELECT * FROM PatientNames WHERE @patientID = ID))
	BEGIN
		RAISERROR('Patient does not exist.', 14, 1)
		Return 2
	END
	SELECT CONCAT(FName, ' ', lname) as Name, ID FROM DoctorsUnderPatientView WHERE PatientID = @patientID
	Return 0
GO

CREATE VIEW SideEffectsOfTreatment
As
SELECT T.ID as TreatmentID, S.ID, S.name
FROM Treatment T JOIN SideEffectOf SE
on T.ID = SE.treatmentID
JOIN Symptom S on SE.symptomID = S.ID
GO

CREATE PROCEDURE GetSideEffectsOfTreatment
(@treatmentID Integer)
AS
	IF(@treatmentID is null)
	BEGIN
		RAISERROR('Treatment ID does not exist.', 14, 1)
		Return 1
	END
	IF(NOT EXISTS(SELECT * FROM TreatmentNames WHERE @treatmentID = ID))
	BEGIN
		RAISERROR('Treatment does not exist.', 14, 1)
		Return 2
	END
	SELECT * FROM SideEffectsOfTreatment WHERE TreatmentID = @treatmentID
	Return 0
GO

CREATE VIEW DoctorsForTreatment
As
SELECT T.ID as TreatmentID, D.ID as DoctorID, D.fname as DFirstName, D.lname as DLastName, T.Cost as TreatmentCost, T.name as TreatmentName
FROM Treatment T JOIN Performs P
on T.ID = P.treatmentID
JOIN Person D on P.doctorID = D.ID
WHERE D.role = 'DR'
GO

CREATE VIEW InsuredDoctorsForTreatmentSubtraction
As
SELECT T.ID as TreatmentID, D.ID, D.fname as DFirstName, D.lname as DLastName, T.Cost as TreatmentCost
FROM Treatment T JOIN Insures I
on T.ID = I.treatmentID
JOIN Person D on I.personID = D.ID
JOIN HealthCareProvider HCP on I.HCPID = HCP.ID
JOIN Person P on HCP.ID = P.hcpID
WHERE D.role = 'DR' and P.role = 'PA'
GO

CREATE VIEW InsuredDoctorsForTreatment
As
SELECT T.ID as TreatmentID, D.ID, D.fname as DFirstName, D.lname as DLastName, I.HCPID, P.ID as PatientID, I.Coverage as Cost
FROM Treatment T JOIN Insures I
on T.ID = I.treatmentID
JOIN Person D on I.personID = D.ID
JOIN HealthCareProvider HCP on I.HCPID = HCP.ID
JOIN Person P on HCP.ID = P.hcpID
WHERE D.role = 'DR' and P.role = 'PA'
GO

CREATE PROCEDURE GetDoctorExpensesForTreatment
(@treatmentID Integer, @patientID Integer)
AS
	IF(@treatmentID is null or @patientID is null)
	BEGIN
		RAISERROR('Arguments are null.', 14, 1)
		Return 1
	END
	IF(NOT EXISTS(SELECT * FROM TreatmentNames WHERE @treatmentID = ID))
	BEGIN
		RAISERROR('Treatment does not exist.', 14, 1)
		Return 2
	END
	IF(NOT EXISTS(SELECT * FROM PatientNames WHERE @patientID = ID))
	BEGIN
		RAISERROR('Patient does not exist.', 14, 1)
		Return 2
	END

	DECLARE @treatmentCost Integer
	SET @treatmentCost = (SELECT Cost
						  FROM Treatment
						  WHERE Treatment.ID = @treatmentID)

	(SELECT CONCAT(DFirstName, ' ', DLastName) as [Doctor Name], TreatmentCost as Cost FROM DoctorsForTreatment WHERE TreatmentID = @treatmentID
	EXCEPT
	SELECT CONCAT(DFirstName, ' ', DLastName) as [Doctor Name], TreatmentCost as Cost FROM InsuredDoctorsForTreatmentSubtraction WHERE TreatmentID = @treatmentID)
	UNION
	SELECT CONCAT(DFirstName, ' ', DLastName) as [Doctor Name], @treatmentCost - Cost as Cost FROM InsuredDoctorsForTreatment WHERE TreatmentID = @treatmentID and PatientID = @patientID

	Return 0
GO

CREATE PROCEDURE SymptomGetIDFromName
(@name varchar(50))
AS
	IF(@name is null or @name = '')
	BEGIN
		RAISERROR('Symptom name is empty.', 14, 1)
		Return 1
	END
	IF(NOT EXISTS(SELECT * FROM SymptomNames WHERE SymptomNames.NAME = @name))
	BEGIN
		RAISERROR('Symptom does not exist.', 14, 1)
		Return 2
	END
	SELECT ID FROM SymptomNames WHERE SymptomNames.NAME = @name
	Return 0
GO

Create Index IX_PERSONLASTNAME
on Person(LNAME)
With FILLFACTOR = 80
GO

CREATE PROCEDURE GetTreatmentsFromDoctor
(@doctorID Integer)
AS
	IF(@doctorID is null)
	BEGIN
		RAISERROR('Arguments are null.', 14, 1)
		Return 1
	END
	IF(NOT EXISTS(SELECT * FROM DoctorNames WHERE @doctorID = ID))
	BEGIN
		RAISERROR('Doctor does not exist.', 14, 1)
		Return 2
	END
	SELECT TreatmentName FROM DoctorsForTreatment WHERE DoctorID = @doctorID
	Return 0
GO

Create Procedure ShowAllPatients
As
BEGIN
	SELECT *
	FROM Person
	WHERE Person.role = 'PA'
END
Go

Create Procedure ShowAllDoctors
As
BEGIN
	SELECT *
	FROM Person
	WHERE Person.role = 'DR'
END
Go

Create Procedure ShowAllPeople
As
BEGIN
	SELECT *
	FROM Person
END
Go

Create Procedure ShowAllHealthCareProviders
As
BEGIN
	SELECT *
	FROM HealthCareProvider
END
Go

Create Procedure ShowAllTreatments
As
BEGIN
	SELECT *
	FROM Treatment
END
Go

Create Procedure ShowAllSymptoms
As
BEGIN
	SELECT *
	FROM Symptom
END
Go

Create Procedure ShowAllAcuteSymptoms
As
BEGIN
	SELECT *
	FROM Acute
END
Go

Create Procedure ShowAllChronicSymptoms
As
BEGIN
	SELECT *
	FROM Chronic
END
Go

Create Procedure ShowAllInsures
As
BEGIN
	SELECT *
	FROM Insures
END
Go

Create Procedure ShowAllPerforms
As
BEGIN
	SELECT *
	FROM Performs
END
Go

Create Procedure ShowAllNeeds
As
BEGIN
	SELECT *
	FROM Needs
END
Go

Create Procedure ShowAllSideEffectOf
As
BEGIN
	SELECT *
	FROM SideEffectOf
END
Go

Create Procedure ShowAllDoctorFor
As
BEGIN
	SELECT *
	FROM DoctorFor
END
Go

CREATE PROCEDURE GetDateFromSymptom
(@patientID Integer)
as
BEGIN
	SELECT symptomDate
	FROM dbo.PatientAcuteSymptomView
	WHERE PatientID = @patientID 
	ORDER BY symptomDate DESC
END
Go

CREATE PROCEDURE getHCPFromPatientID
(@pID varchar(50))
As
BEGIN
	if(@pID is null)
	BEGIN
		Raiserror('Patient ID can not be null', 14, 1)
		Return 1
	END
	if(NOT EXISTS (SELECT * FROM Person WHERE ID = @pID and role = 'PA'))
	BEGIN
		Raiserror('Patient is not in table', 14, 1)
		Return 2
	END
	SELECT HCP.name
	FROM Person P Join HealthCareProvider HCP on P.hcpID = HCP.ID
	WHERE P.ID = @pID and P.role = 'PA'
END
GO

CREATE VIEW NotTreatmentForView
AS
SELECT Doc.ID AS DoctorID, T.ID as ID, T.name as Name
FROM Person Doc JOIN Treatment T On NOT EXISTS (SELECT * FROM Performs WHERE doctorID = Doc.ID and treatmentID = T.ID)
WHERE Doc.role = 'DR'
GO

CREATE PROCEDURE GetTreatmentsNotFromDoctor
(@doctorID Integer)
AS
	IF(@doctorID is null)
	BEGIN
		RAISERROR('Doctor ID does not exist.', 14, 1)
		Return 1
	END
	IF(NOT EXISTS(SELECT * FROM DoctorNames WHERE @doctorID = ID))
	BEGIN
		RAISERROR('Doctor does not exist.', 14, 1)
		Return 2
	END
	SELECT ID, Name FROM NotTreatmentForView WHERE DoctorID = @doctorID
GO
