package com.undecideds.services;

import com.undecideds.services.generic.CUDService;
import com.undecideds.services.generic.ReadService;
import com.undecideds.services.structs.Argument;

import java.util.ArrayList;

public class ReadServiceList {
    public static final ReadService CHRONIC_FROM_PATIENT = new ReadService("ChronicFromPatient", new Argument[]{
            new Argument(Argument.ArgumentType.INT, "PID")
    });

    public static final ReadService GET_SIDEEFFECT_OF_TREATMENT = new ReadService("GetSideEffectsOfTreatment", new Argument[]{
            new Argument(Argument.ArgumentType.INT, "TreatmentID")
    });

    public static final ReadService SYMPTOM_GET_ID_FROM_NAME = new ReadService("SymptomGetIDFromName", new Argument[]{
            new Argument(Argument.ArgumentType.STRING, "name")
    });

    public static final ReadService ACUTE_FROM_PATIENT = new ReadService("AcuteFromPatient", new Argument[]{
            new Argument(Argument.ArgumentType.INT, "PID")
    });
    public static final ReadService GET_TREATMENTS_FROM_DOCTOR = new ReadService("GetTreatmentsFromDoctor", new Argument[]{
            new Argument(Argument.ArgumentType.INT, "ID")
    });
    public static final ReadService GET_PAST_TREATMENTS = new ReadService("GetPastTreatments", new Argument[]{
            new Argument(Argument.ArgumentType.INT, "PID")
    });

    public static final ReadService GET_CURRENT_TREATMENTS = new ReadService("GetCurrentTreatments", new Argument[]{
            new Argument(Argument.ArgumentType.INT, "PID")
    });

    public static final ReadService DOCTORS_FROM_PATIENT = new ReadService("GetDoctorsFromPatient", new Argument[]{
            new Argument(Argument.ArgumentType.INT, "patientID")
    });

    public static final ReadService PATIENTS_FROM_DOCTOR = new ReadService("GetPatientsFromDoctor", new Argument[]{
            new Argument(Argument.ArgumentType.INT, "DoctorID")
    });

    public static final ReadService DATE_FROM_SYMPTOM = new ReadService("GetDateFromSymptom", new Argument[]{
            new Argument(Argument.ArgumentType.INT, "patientID")
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

    public static final ReadService IMPORT_SYMPTOM = new ReadService("ImportSymptom", new Argument[]{
            new Argument(Argument.ArgumentType.STRING, "NAME")
    });
    public static final ReadService IMPORT_PERSON = new ReadService("ImportPerson", new Argument[]{
            new Argument(Argument.ArgumentType.STRING, "FNAME"),
            new Argument(Argument.ArgumentType.STRING, "LNAME"),
            new Argument(Argument.ArgumentType.STRING, "LOGIN"),
            new Argument(Argument.ArgumentType.STRING, "PASSWORD"),
            new Argument(Argument.ArgumentType.STRING, "ROLE"),
            new Argument(Argument.ArgumentType.INT, "INSURED BY")
    });
    public static final ReadService IMPORT_HCP = new ReadService("ImportHealthCareProvider", new Argument[]{
            new Argument(Argument.ArgumentType.STRING, "NAME")
    });
    public static final ReadService IMPORT_TREATMENT = new ReadService("ImportTreatment", new Argument[]{
            new Argument(Argument.ArgumentType.INT, "COST"),
            new Argument(Argument.ArgumentType.STRING, "NAME")
    });
    public static final ReadService PASSWORD_FROM_LOGIN = new ReadService("getPasswordByLogin", new Argument[]{
            new Argument(Argument.ArgumentType.STRING, "LOGIN")
    });
    public static final ReadService ID_FROM_LOGIN = new ReadService("getIDByLogin", new Argument[]{
            new Argument(Argument.ArgumentType.STRING, "LOGIN")
    });
    public static final ReadService PERSON_NAME_FROM_ID = new ReadService("personGetNameByID", new Argument[]{
            new Argument(Argument.ArgumentType.INT, "ID")
    });
    public static final ReadService PERSON_ROLE_FROM_ID = new ReadService("personGetRoleByID", new Argument[]{
            new Argument(Argument.ArgumentType.INT, "ID")
    });

    public static final ReadService GET_PATIENTS_FROM_DOCTOR = new ReadService("GetPatientsFromDoctor", new Argument[]{
            new Argument(Argument.ArgumentType.INT, "ID")
    });

    public static final ReadService GET_PATIENTS_NOT_FROM_DOCTOR = new ReadService("GetPatientsNotFromDoctor", new Argument[]{
            new Argument(Argument.ArgumentType.INT, "ID")
    });
}
