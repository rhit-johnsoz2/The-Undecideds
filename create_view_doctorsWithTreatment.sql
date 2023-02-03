Use SymptomTracker
Go
CREATE VIEW DoctorsWithTreatment
AS
SELECT T.ID as TreatmentID, Doctor.fname, Doctor.lname, Doctor.ID
FROM Person Doctor JOIN Performs P
On Doctor.ID = P.doctorID
JOIN Treatment T on P.treatmentID = T.ID
WHERE Doctor.role = 'DR'
