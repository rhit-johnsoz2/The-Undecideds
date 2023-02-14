package com.undecideds.services;

import com.undecideds.services.generic.CUDService;
import com.undecideds.services.generic.ReadService;
import com.undecideds.services.structs.Argument;

import java.util.ArrayList;

public class ReadServiceList {
    public static final ReadService CHRONIC_FROM_PATIENT = new ReadService("ChronicFromPatient", new Argument[]{
            new Argument(Argument.ArgumentType.INT, "PID")
    });

    public static final ReadService ACUTE_FROM_PATIENT = new ReadService("ChronicFromPatient", new Argument[]{
            new Argument(Argument.ArgumentType.INT, "PID")
    });

    public static final ReadService GET_PERSONS = new ReadService("ShowAllPeople", new Argument[]{
    });
    public static final ReadService GET_PATIENTS = new ReadService("ShowAllPatients", new Argument[]{
    });
    public static final ReadService GET_DOCTORS = new ReadService("ShowAllDoctors", new Argument[]{
    });

    public static final ReadService GET_HEALTHCAREPROVIDERS = new ReadService("ShowAllHealthCareProviders", new Argument[]{
    });

    public static final ReadService GET_SYMPTOMS = new ReadService("ShowAllSymptoms", new Argument[]{
    });

    public static final ReadService GET_TREATMENTS = new ReadService("ShowAllTreatments", new Argument[]{
    });

    public static final ReadService GET_INSURES = new ReadService("ShowAllInsures", new Argument[]{
    });

    public static final ReadService GET_PERFORMS = new ReadService("ShowAllPerforms", new Argument[]{
    });

    public static final ReadService GET_NEEDS = new ReadService("ShowAllNeeds", new Argument[]{
    });

    public static final ReadService GET_SIDEEFFECTOF = new ReadService("ShowAllSideEffectOf", new Argument[]{
    });

    public static final ReadService GET_DOCTORFOR = new ReadService("ShowAllDoctorFor", new Argument[]{
    });

    public static final ReadService GET_ACUTE = new ReadService("ShowAllAcuteSymptoms", new Argument[]{
    });

    public static final ReadService GET_CHRONIC = new ReadService("ShowAllChronicSymptoms", new Argument[]{
    });

    public static final ReadService GET_SYMPTOM_NAMES = new ReadService("GetSymptomNames", new Argument[]{});
    public static final ReadService GET_PERSON_NAMES = new ReadService("GetPersonNames", new Argument[]{});
    public static final ReadService GET_DOCTOR_NAMES = new ReadService("GetDoctorNames", new Argument[]{});
    public static final ReadService GET_PATIENT_NAMES = new ReadService("GetPatientNames", new Argument[]{});
    public static final ReadService GET_HCP_NAMES = new ReadService("GetHCPNames", new Argument[]{});
    public static final ReadService GET_TREATMENT_NAMES = new ReadService("GetTreatmentNames", new Argument[]{});
}
