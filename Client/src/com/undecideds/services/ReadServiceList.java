package com.undecideds.services;

import com.undecideds.services.generic.CUDService;
import com.undecideds.services.generic.ReadService;
import com.undecideds.services.structs.Argument;

public class ReadServiceList {

    public static final ReadService CHRONIC_FROM_PATIENT = new ReadService("ChronicFromPatient", new Argument[]{
            new Argument(Argument.ArgumentType.INT), // PID
    });
}
