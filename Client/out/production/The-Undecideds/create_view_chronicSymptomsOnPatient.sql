Use SymptomTracker
Go
CREATE VIEW AllChronicSymptomsOnPatient
AS
SELECT Patient.ID as PID, S.name, S.ID
FROM Person Patient JOIN Chronic C
On Patient.ID = C.personID
JOIN Symptom S on C.symptomID = S.ID
WHERE Patient.role = 'PA'
