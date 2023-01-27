Use SymptomTracker
Go
CREATE VIEW PatientAcuteSymptomView
AS
SELECT Patient.ID AS PatientID, Acute.severity, Acute.symptomtimestamp, Symptom.name
FROM Person Patient JOIN Acute ON Patient.ID = Acute.personID
JOIN Symptom ON Acute.symptomID = Symptom.ID
Go

CREATE VIEW PatientChronicSymptomView
AS
SELECT Patient.ID AS PatientID, Symptom.name
FROM Person Patient JOIN Chronic ON Patient.ID = Chronic.personID
JOIN Symptom ON Chronic.symptomID = Symptom.ID
Go

CREATE VIEW PatientTreatmentView
AS
SELECT Patient.ID AS PatientID, Needs.SDate, Needs.EDate, Treatment.Cost, Treatment.name
FROM Person Patient JOIN Needs ON Patient.ID = Needs.PatientID
JOIN Treatment ON Treatment.ID = Needs.TreatmentID
