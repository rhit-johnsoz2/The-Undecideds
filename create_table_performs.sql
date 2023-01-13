Use SymptomTracker
Go
CREATE TABLE Performs (
	doctorID integer,
	treatmentID integer,
	primary key(doctorID, treatmentID),
	foreign key(doctorID) references person(ID),
	foreign key(treatmentID) references treatment(ID),
);
