use SymptomTracker
GO

CREATE PROCEDURE [AcuteFromPatient](
	@PID Integer
) AS BEGIN
	SELECT PatientID, severity, symptomtimestamp, name
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