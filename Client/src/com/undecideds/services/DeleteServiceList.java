package com.undecideds.services;

import com.undecideds.services.generic.CUDService;
import com.undecideds.services.structs.Argument;

import java.sql.Date;
import java.sql.Timestamp;

public class DeleteServiceList {
    public static final CUDService DELETE_PERSON = new CUDService("deletePerson", new Argument[] {
            new Argument(Argument.ArgumentType.STRING, "FNAME"),
            new Argument(Argument.ArgumentType.STRING, "LNAME")
    }, new String[]{
            "Successful",
            "Input Arguments cannot be null",
            "Person not in table"
    });


    public static final CUDService DELETE_HEALTHCAREPROVIDER = new CUDService("deleteHealthCareProvider", new Argument[] {
            new Argument(Argument.ArgumentType.STRING, "NAME")
    }, new String[]{
            "Successful",
            "Input Argument cannot be null",
            "Health Care Provider not in table"
    });


    public static final CUDService DELETE_TREATMENT = new CUDService("deleteTreatment", new Argument[] {
            new Argument(Argument.ArgumentType.STRING, "NAME")
    }, new String[]{
            "Successful",
            "Input Argument cannot be null",
            "Treatment not in table"
    });


    public static final CUDService DELETE_SYMPTOM = new CUDService("deleteSymptom", new Argument[] {
            new Argument(Argument.ArgumentType.STRING, "NAME")
    }, new String[]{
            "Successful",
            "Input Argument cannot be null",
            "Symptom not in table"
    });


    public static final CUDService DELETE_ACUTE = new CUDService("deleteAcute", new Argument[] {
            new Argument(Argument.ArgumentType.INT, "SYMPTOM ID"),
            new Argument(Argument.ArgumentType.INT, "PERSON ID")
    }, new String[]{
            "Successful",
            "Input Arguments cannot be null",
            "Acute relationship does not exist"
    });


    public static final CUDService DELETE_CHRONIC = new CUDService("deleteChronic", new Argument[] {
            new Argument(Argument.ArgumentType.INT, "SYMPTOM ID"),
            new Argument(Argument.ArgumentType.INT, "PERSON ID")
    }, new String[]{
            "Successful",
            "Input Arguments cannot be null",
            "Chronic relationship does not exist"
    });


    public static final CUDService DELETE_DOCTORFOR = new CUDService("deleteDoctorFor", new Argument[] {
            new Argument(Argument.ArgumentType.INT, "DOCTOR ID"),
            new Argument(Argument.ArgumentType.INT, "PATIENT ID")
    }, new String[]{
            "Successful",
            "Input Arguments cannot be null",
            "Not a doctor for this patient"
    });


    public static final CUDService DELETE_INSURES = new CUDService("deleteInsures", new Argument[] {
            new Argument(Argument.ArgumentType.INT, "PERSON ID"),
            new Argument(Argument.ArgumentType.INT, "HCP ID"),
            new Argument(Argument.ArgumentType.INT, "TREATMENT ID")
    }, new String[]{
            "Successful",
            "Input Arguments cannot be null",
            "Insures relationship does not exist"
    });


    public static final CUDService DELETE_NEEDS = new CUDService("deleteNeeds", new Argument[] {
            new Argument(Argument.ArgumentType.INT, "PATIENT ID"),
            new Argument(Argument.ArgumentType.INT, "TREATMENT ID")
    }, new String[]{
            "Successful",
            "Input Arguments cannot be null",
            "Needs relationship does not exist"
    });


    public static final CUDService DELETE_PERFORMS = new CUDService("deletePerforms", new Argument[] {
            new Argument(Argument.ArgumentType.INT, "DOCTOR ID"),
            new Argument(Argument.ArgumentType.INT, "TREATMENT ID")
    }, new String[]{
            "Successful",
            "Input Arguments cannot be null",
            "Performs relationship does not exist"
    });


    public static final CUDService DELETE_SIDEEFFECTOF = new CUDService("deleteSideEffectOf", new Argument[] {
            new Argument(Argument.ArgumentType.INT, "SYMPTOM ID"),
            new Argument(Argument.ArgumentType.INT, "TREATMENT ID")
    }, new String[]{
            "Successful",
            "Input Arguments cannot be null",
            "SideEffectOf relationship does not exist"
    });

}