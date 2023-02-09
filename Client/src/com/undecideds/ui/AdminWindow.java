package com.undecideds.ui;

import com.undecideds.services.DeleteServiceList;
import com.undecideds.services.InsertServiceList;
import com.undecideds.services.ReadServiceList;
import com.undecideds.services.UpdateServiceList;
import com.undecideds.services.generic.CUDService;
import com.undecideds.services.generic.ReadService;
import com.undecideds.ui.builders.TableBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class AdminWindow {

    public void launch(){
        JFrame frame = new JFrame();

        JTabbedPane context = new JTabbedPane();
        context.addTab("People", null, buildTableView(ReadServiceList.GET_PERSONS,
                new CUDService[]{
                        InsertServiceList.INSERT_PERSON,
                        UpdateServiceList.UPDATE_PERSON,
                        DeleteServiceList.DELETE_PERSON
                },
                new String[]{
                    "ID", "PERSON ID",
                    "fname",  "FNAME",
                    "lname", "LNAME",
                    "login", "LOGIN",
                    "password", "PASSWORD",
                    "role", "ROLE",
                    "hcpID", "INSURED BY"
                }
        ));
        context.addTab("Health Care Providers", null, buildTableView(ReadServiceList.GET_HEALTHCAREPROVIDERS,
                new CUDService[]{
                        InsertServiceList.INSERT_HEALTHCAREPROVIDER,
                        UpdateServiceList.UPDATE_HEALTHCAREPROVIDER,
                        DeleteServiceList.DELETE_HEALTHCAREPROVIDER
                },
                new String[]{
                        "ID", "HEALTHCAREPROVIDER ID",
                        "name",  "NAME",
                }
        ));
        context.addTab("Symptoms", null, buildTableView(ReadServiceList.GET_SYMPTOMS,
                new CUDService[]{
                        InsertServiceList.INSERT_SYMPTOM,
                        UpdateServiceList.UPDATE_SYMPTOM,
                        DeleteServiceList.DELETE_SYMPTOM
                },
                new String[]{
                        "ID", "SYMPTOM ID",
                        "name",  "NAME"
                }
        ));
        context.addTab("Treatments", null, buildTableView(ReadServiceList.GET_TREATMENTS,
                new CUDService[]{
                        InsertServiceList.INSERT_TREATMENT,
                        UpdateServiceList.UPDATE_TREATMENT,
                        DeleteServiceList.DELETE_TREATMENT
                },
                new String[]{
                        "ID", "TREATMENT ID",
                        "Cost",  "COST",
                        "name", "NAME"
                }
        ));
        context.addTab("Insures", null, buildTableView(ReadServiceList.GET_INSURES,
                new CUDService[]{
                        InsertServiceList.INSERT_INSURES,
                        UpdateServiceList.UPDATE_INSURES,
                        DeleteServiceList.DELETE_INSURES
                },
                new String[]{
                        "PersonID", "PERSON ID",
                        "HCPID",  "HCP ID",
                        "TreatmentID", "TREATMENT ID",
                        "Coverage", "COVERAGE"
                },
                new String[]{"PERSON ID", "HCP ID", "TREATMENT ID"}
        ));
        context.addTab("Performs", null, buildTableView(ReadServiceList.GET_PERFORMS,
                new CUDService[]{
                        InsertServiceList.INSERT_PERFORMS,
                        UpdateServiceList.UPDATE_PERFORMS,
                        DeleteServiceList.DELETE_PERFORMS
                },
                new String[]{
                        "doctorID", "DOCTOR ID",
                        "treatmentID",  "TREATMENT ID"
                },
                false
        ));
        context.addTab("Needs", null, buildTableView(ReadServiceList.GET_NEEDS,
                new CUDService[]{
                        InsertServiceList.INSERT_NEEDS,
                        UpdateServiceList.UPDATE_NEEDS,
                        DeleteServiceList.DELETE_NEEDS
                },
                new String[]{
                        "PatientID", "PATIENT ID",
                        "TreatmentID",  "TREATMENT ID",
                        "SDate", "STARTING DATE",
                        "EDate", "ENDING DATE"
                },
                new String[]{"PATIENT ID", "TREATMENT ID"}
        ));
        context.addTab("SideEffectOf", null, buildTableView(ReadServiceList.GET_SIDEEFFECTOF,
                new CUDService[]{
                        InsertServiceList.INSERT_SIDEEFFECTOF,
                        UpdateServiceList.UPDATE_SIDEEFFECTOF,
                        DeleteServiceList.DELETE_SIDEEFFECTOF
                },
                new String[]{
                        "symptomID", "SYMPTOM ID",
                        "treatmentID",  "TREATMENT ID"
                },
                false
        ));
        context.addTab("AcuteSymptoms", null, buildTableView(ReadServiceList.GET_ACUTE,
                new CUDService[]{
                        InsertServiceList.INSERT_ACUTE,
                        UpdateServiceList.UPDATE_ACUTE,
                        DeleteServiceList.DELETE_ACUTE
                },
                new String[]{
                        "personID", "PERSON ID",
                        "symptomID",  "SYMPTOM ID",
                        "severity", "SEVERITY",
                        "symptomtimestamp", "TIMESTAMP"
                },
                new String[]{"PERSON ID", "SYMPTOM ID"}
        ));
        context.addTab("ChronicSymptoms", null, buildTableView(ReadServiceList.GET_CHRONIC,
                new CUDService[]{
                        InsertServiceList.INSERT_CHRONIC,
                        UpdateServiceList.UPDATE_CHRONIC,
                        DeleteServiceList.DELETE_CHRONIC
                },
                new String[]{
                        "PersonID", "PERSON ID",
                        "SymptomID",  "SYMPTOM ID"
                },
                false
        ));



        frame.add(context);
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private Container buildTableView(ReadService readService, CUDService[] services, String[] map, String[] fixedOnUpdate, boolean canUpdate, boolean canDelete){
        HashMap<String, String> nameMatch = new HashMap<>();
        for(int i = 0; i < map.length; i += 2){
            nameMatch.put(map[i], map[i+1]);
        }
        return TableBuilder.buildTableWithCUD(readService, nameMatch, services[0], services[1], services[2], fixedOnUpdate, canUpdate, canDelete);
    }

    private Container buildTableView(ReadService readService, CUDService[] services, String[] map, String[] fixedOnUpdate){
        return buildTableView(readService, services, map, fixedOnUpdate, true, true);
    }

    private Container buildTableView(ReadService readService, CUDService[] services, String[] map, boolean canUpdate, boolean canDelete){
        return buildTableView(readService, services, map, new String[]{}, canUpdate, canDelete);
    }

    private Container buildTableView(ReadService readService, CUDService[] services, String[] map, boolean canUpdate){
        return buildTableView(readService, services, map, new String[]{}, canUpdate, true);
    }

    private Container buildTableView(ReadService readService, CUDService[] services, String[] map){
        return buildTableView(readService, services, map,true, true);
    }
}
