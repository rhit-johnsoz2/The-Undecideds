Use SymptomTracker
Go
CREATE VIEW DoctorView
AS
SELECT Doctor.ID AS DoctorID, Patient.fname, Patient.lname, Patient.ID
FROM Person Doctor JOIN DoctorFor DF
On Doctor.ID = DF.doctorID
JOIN Person Patient on DF.patientID = Patient.ID
WHERE Doctor.role = 'DR' AND Patient.role = 'PA'
