Use SymptomTracker
Go
CREATE TABLE SideEffectOf (
	symptomID integer,
	treatmentID integer,
	primary key(symptomID, treatmentID),
	foreign key(symptomID) references symptom(ID),
	foreign key(treatmentID) references treatment(ID)
);
