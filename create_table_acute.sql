Use SymptomTracker
Go
CREATE TABLE Acute (
	personID integer,
	symptomID integer,
	severity integer,
	symptomDate date,
	primary key(personID, symptomID),
	foreign key(personID) references person(ID)
		On Delete Cascade
		On Update Cascade,
	foreign key(symptomID) references symptom(ID)
		On Delete Cascade
		On Update Cascade,
);
