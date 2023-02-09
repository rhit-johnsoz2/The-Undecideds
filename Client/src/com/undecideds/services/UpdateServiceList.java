package com.undecideds.services;

import com.undecideds.services.generic.CUDService;
import com.undecideds.services.structs.Argument;

public class UpdateServiceList {
    public static final CUDService UPDATE_PERSON = new CUDService("UpdatePerson", new Argument[]{
            new Argument(Argument.ArgumentType.INT, "PERSON ID"),
            new Argument(Argument.ArgumentType.STRING, "FNAME"),
            new Argument(Argument.ArgumentType.STRING, "LNAME"),
            new Argument(Argument.ArgumentType.STRING, "LOGIN"),
            new Argument(Argument.ArgumentType.STRING, "PASSWORD"),
            new Argument(Argument.ArgumentType.STRING, "ROLE"),
            new Argument(Argument.ArgumentType.INT, "INSURED BY")
    }, new String[]{
            "Successful",
            "Inputs cannot be null",
            "Person does not exist and cannot be updated"
    });

    public static final CUDService UPDATE_HEALTHCAREPROVIDER = new CUDService("UpdateHCP", new Argument[]{
            new Argument(Argument.ArgumentType.INT, "HEALTHCAREPROVIDER ID"),
            new Argument(Argument.ArgumentType.STRING, "NAME")
    }, new String[]{
            "Successful",
            "Input Arguments cannot be null"
    });

    public static final CUDService UPDATE_SYMPTOM = new CUDService("UpdateSymptom", new Argument[]{
            new Argument(Argument.ArgumentType.INT, "SYMPTOM ID"),
            new Argument(Argument.ArgumentType.STRING, "NAME")
    }, new String[]{
            "Successful",
            "Input Arguments cannot be null"
    });

    public static final CUDService UPDATE_TREATMENT = new CUDService("UpdateTreatment", new Argument[]{
            new Argument(Argument.ArgumentType.INT, "TREATMENT ID"),
            new Argument(Argument.ArgumentType.INT, "COST"),
            new Argument(Argument.ArgumentType.STRING, "NAME")
    }, new String[]{
            "Successful",
            "Input Arguments cannot be null",
            "Input Arguments already exist"
    });


    public static final CUDService UPDATE_INSURES = new CUDService("UpdateInsures", new Argument[]{
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


    public static final CUDService UPDATE_DOCTORFOR = new CUDService("UpdateDoctorFor", new Argument[]{
            new Argument(Argument.ArgumentType.INT, "DOCTOR ID"),
            new Argument(Argument.ArgumentType.INT, "PATIENT ID")
    }, new String[]{
            "Successful",
            "Input Arguments cannot be null",
            "DoctorID does not exist",
            "PatientID does not exist"
    });


    public static final CUDService UPDATE_PERFORMS = new CUDService("UpdatePerforms", new Argument[]{
            new Argument(Argument.ArgumentType.INT, "DOCTOR ID"),
            new Argument(Argument.ArgumentType.INT, "TREATMENT ID")
    }, new String[]{
            "Successful",
            "Input Arguments cannot be null",
            "DoctorID does not exist",
            "TreatmentID does not exist"
    });


    public static final CUDService UPDATE_NEEDS = new CUDService("UpdateNeeds", new Argument[]{
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


    public static final CUDService UPDATE_SIDEEFFECTOF = new CUDService("UpdateSideEffectOf", new Argument[]{
            new Argument(Argument.ArgumentType.INT, "SYMPTOM ID"),
            new Argument(Argument.ArgumentType.INT, "TREATMENT ID")
    }, new String[]{
            "Successful",
            "Input Arguments cannot be null",
            "SymptomID does not exist",
            "TreatmentID does not exist"
    });


    public static final CUDService UPDATE_ACUTE = new CUDService("UpdateAcute", new Argument[]{
            new Argument(Argument.ArgumentType.INT, "PERSON ID"),
            new Argument(Argument.ArgumentType.INT, "SYMPTOM ID"),
            new Argument(Argument.ArgumentType.INT, "SEVERITY"),
            new Argument(Argument.ArgumentType.TIMESTAMP, "TIMESTAMP")
    }, new String[]{
            "Successful",
            "Input Arguments cannot be null",
            "PersonID does not exist",
            "SymptomID does not exist"
    });

    public static final CUDService UPDATE_CHRONIC = new CUDService("UpdateChronic", new Argument[]{
            new Argument(Argument.ArgumentType.INT, "PERSON ID"),
            new Argument(Argument.ArgumentType.INT, "SYMPTOM ID")
    }, new String[]{
            "Successful",
            "Input Arguments cannot be null",
            "PersonID does not exist",
            "SymptomID does not exist"
    });

}
