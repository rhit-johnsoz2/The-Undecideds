Use SymptomTracker
Go
Create View PatientsOnDoctorView
As
SELECT Doctor.ID as DoctorID, Patient.ID, Patient.fname, Patient.lname
FROM Person Doctor JOIN DoctorFor DF
on Doctor.ID = DF.doctorID
JOIN Person Patient on DF.patientID = Patient.ID
WHERE Doctor.role = 'DR'
Go
