Use SymptomTracker
Go
CREATE VIEW AllAcuteSymptomsOnPatient
AS
SELECT S.name, S.ID
FROM Person Patient JOIN Acute A
On Patient.ID = A.personID
JOIN Symptom S on A.symptomID = S.ID
WHERE Patient.role = 'PA'
GROUP BY A.severity
