package com.undecideds.services;

import com.undecideds.services.generic.CUDService;
import com.undecideds.services.generic.ReadService;
import com.undecideds.services.structs.Argument;

public class ReadServiceList {

    public static final ReadService CHRONIC_FROM_PATIENT = new ReadService("ChronicFromPatient", new Argument[]{
            new Argument(Argument.ArgumentType.INT, "PID")
    });

    public static final ReadService GET_PERSONS = new ReadService("ShowAllPeople", new Argument[]{
    });
    public static final ReadService GET_PATIENTS = new ReadService("ShowAllPatients", new Argument[]{
    });
    public static final ReadService GET_DOCTORS = new ReadService("ShowAllDoctors", new Argument[]{
    });


}
