use [SymptomTracker]
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
