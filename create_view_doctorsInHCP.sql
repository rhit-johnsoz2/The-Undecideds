Use SymptomTracker
Go
CREATE VIEW AllDoctorsInHCP
AS
SELECT HCP.ID as HCPID, Doctor.fname, Doctor.lname, Doctor.ID
FROM Person Doctor JOIN Insures I
on Doctor.ID = I.PersonID
JOIN HealthCareProvider HCP on I.HCPID = HCP.ID
WHERE Doctor.role = 'DR'
