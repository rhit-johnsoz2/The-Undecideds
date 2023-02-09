package com.undecideds.services;

import com.undecideds.services.generic.CUDService;
import com.undecideds.services.structs.Argument;

import java.sql.Date;
import java.sql.Timestamp;

public class InsertServiceList {
    public static final CUDService INSERT_PERSON = new CUDService("InsertPerson", new Argument[]{
            //new Argument(Argument.ArgumentType.INT, "D"),
            new Argument(Argument.ArgumentType.STRING, "FNAME"),
            new Argument(Argument.ArgumentType.STRING, "LNAME"),
            new Argument(Argument.ArgumentType.STRING, "LOGIN"),
            new Argument(Argument.ArgumentType.STRING, "PASSWORD"),
            new Argument(Argument.ArgumentType.STRING, "ROLE"),
            new Argument(Argument.ArgumentType.INT, "INSURED BY")
    }, new String[]{
            "Successful",
            "Input Arguments cannot be null"
    });

    public static final CUDService INSERT_HEALTHCAREPROVIDER = new CUDService("InsertHealthCareProviders", new Argument[]{
            new Argument(Argument.ArgumentType.STRING, "NAME")
    }, new String[]{
            "Successful",
            "Input Arguments cannot be null"
    });

    public static final CUDService INSERT_SYMPTOM = new CUDService("InsertSymptom", new Argument[]{
            new Argument(Argument.ArgumentType.STRING, "NAME")
    }, new String[]{
            "Successful",
            "Input Arguments cannot be null"
    });

    public static final CUDService INSERT_CHRONIC = new CUDService("InsertChronic", new Argument[]{
            new Argument(Argument.ArgumentType.INT, "PERSON ID"),
            new Argument(Argument.ArgumentType.INT, "SYMPTOM ID")
    }, new String[]{
            "Successful",
            "Input Arguments cannot be null",
            "PersonID does not exist",
            "SymptomID does not exist"
    });

    public static final CUDService INSERT_TREATMENT = new CUDService("InsertTreatment", new Argument[]{
            new Argument(Argument.ArgumentType.INT, "COST"),
            new Argument(Argument.ArgumentType.STRING, "NAME")
    }, new String[]{
            "Successful",
            "Input Arguments cannot be null"
    });


    public static final CUDService INSERT_INSURES = new CUDService("InsertInsures", new Argument[]{
            new Argument(Argument.ArgumentType.INT, "PERSON ID"),
            new Argument(Argument.ArgumentType.INT, "HCP ID"),
            new Argument(Argument.ArgumentType.INT, "TREATMENT ID"),
            new Argument(Argument.ArgumentType.INT, "COVERAGE")
    }, new String[]{
            "Successful",
            "Input Arguments cannot be null",
            "PersonID does not exist",
            "HCPID does not exist",
            "TreatmentID does not exist"
    });


    public static final CUDService INSERT_DOCTORFOR = new CUDService("InsertDoctorFor", new Argument[]{
            new Argument(Argument.ArgumentType.INT, "DOCTOR ID"),
            new Argument(Argument.ArgumentType.INT, "PATIENT ID")
    }, new String[]{
            "Successful",
            "Input Arguments cannot be null",
            "DoctorID does not exist",
            "PatientID does not exist"
    });


    public static final CUDService INSERT_PERFORMS = new CUDService("InsertPerform", new Argument[]{
            new Argument(Argument.ArgumentType.INT, "DOCTOR ID"),
            new Argument(Argument.ArgumentType.INT, "TREATMENT ID")
    }, new String[]{
            "Successful",
            "Input Arguments cannot be null",
            "Treatment does not exist",
            "Doctor does not exist"
    });


    public static final CUDService INSERT_NEEDS = new CUDService("InsertNeeds", new Argument[]{
            new Argument(Argument.ArgumentType.INT, "PATIENT ID"),
            new Argument(Argument.ArgumentType.INT, "TREATMENT ID"),
            new Argument(Argument.ArgumentType.DATE, "STARTING DATE"),
            new Argument(Argument.ArgumentType.DATE, "ENDING DATE")
    }, new String[]{
            "Successful",
            "Input Arguments cannot be null",
            "PatientID does not exist",
            "TreatmentID does not exist",
            "End Date occurs before Start Date"
    });


    public static final CUDService INSERT_SIDEEFFECTOF = new CUDService("InsertSideEffectOf", new Argument[]{
            new Argument(Argument.ArgumentType.INT, "SYMPTOM ID"),
            new Argument(Argument.ArgumentType.INT, "TREATMENT ID")
    }, new String[]{
            "Successful",
            "Input Arguments cannot be null",
            "Symptom does not exist",
            "Treatment does not exist"
    });


    public static final CUDService INSERT_ACUTE = new CUDService("InsertAcute", new Argument[]{
            new Argument(Argument.ArgumentType.INT, "PERSON ID"),
            new Argument(Argument.ArgumentType.INT, "SYMPTOM ID"),
            new Argument(Argument.ArgumentType.INT, "SEVERITY"),
            new Argument(Argument.ArgumentType.TIMESTAMP, "TIMESTAMP")
    }, new String[]{
            "Successful",
            "Input Arguments cannot be null",
            "PersonID does not exist"
    });

}
