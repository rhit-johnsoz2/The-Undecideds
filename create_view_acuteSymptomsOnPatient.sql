Use SymptomTracker
Go
CREATE VIEW AllAcuteSymptomsOnPatient
AS
SELECT Patient.ID as PID, S.name, S.ID
FROM Person Patient JOIN Acute A
On Patient.ID = A.personID
JOIN Symptom S on A.symptomID = S.ID
WHERE Patient.role = 'PA'
