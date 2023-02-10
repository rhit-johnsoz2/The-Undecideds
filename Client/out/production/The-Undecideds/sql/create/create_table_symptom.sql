Use SymptomTracker
Go
CREATE TABLE Symptom (
	ID int identity(1,1),
	name varchar(40),
	primary key(ID),
	unique(name)
);
