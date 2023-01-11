Use SymptomTracker
Go
CREATE TABLE Acute (
	personID integer,
	symptomID integer,
	severity integer,
	symptomtimestamp timestamp,
	primary key(personID, symptomID),
	foreign key(personID) references person(ID),
	foreign key(symptomID) references symptom(ID)
);
