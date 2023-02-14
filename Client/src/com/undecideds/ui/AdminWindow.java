package com.undecideds.ui;

import com.undecideds.services.DeleteServiceList;
import com.undecideds.services.InsertServiceList;
import com.undecideds.services.ReadServiceList;
import com.undecideds.services.UpdateServiceList;
import com.undecideds.services.generic.CUDService;
import com.undecideds.services.generic.ReadService;
import com.undecideds.services.structs.SprocContainer;
import com.undecideds.ui.builders.TableBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
                },
                new String[]{},
                true,
                true,
                new String[]{"INSURED BY"},
                new ReadService[]{ReadServiceList.GET_HCP_NAMES}
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
                },
                new String[]{},
                true,
                true,
                new String[]{},
                new ReadService[]{}
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
                },
                new String[]{},
                true,
                true,
                new String[]{},
                new ReadService[]{}
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
                },
                new String[]{},
                true,
                true,
                new String[]{},
                new ReadService[]{}
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
                new String[]{"PERSON ID", "HCP ID", "TREATMENT ID"},
                true,
                true,
                new String[]{"PERSON ID", "HCP ID", "TREATMENT ID"},
                new ReadService[]{ReadServiceList.GET_DOCTOR_NAMES, ReadServiceList.GET_HCP_NAMES, ReadServiceList.GET_TREATMENT_NAMES}
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
                new String[]{},
                false,
                true,
                new String[]{"DOCTOR ID", "TREATMENT ID"},
                new ReadService[]{ReadServiceList.GET_DOCTOR_NAMES, ReadServiceList.GET_TREATMENT_NAMES}
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
                new String[]{"PATIENT ID", "TREATMENT ID"},
                true,
                true,
                new String[]{"PATIENT ID", "TREATMENT ID"},
                new ReadService[]{ReadServiceList.GET_PATIENT_NAMES, ReadServiceList.GET_TREATMENT_NAMES}
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
                new String[]{},
                false,
                true,
                new String[]{"SYMPTOM ID", "TREATMENT ID"},
                new ReadService[]{ReadServiceList.GET_SYMPTOM_NAMES, ReadServiceList.GET_TREATMENT_NAMES}
        ));
        context.addTab("DoctorFor", null, buildTableView(ReadServiceList.GET_DOCTORFOR,
                new CUDService[]{
                        InsertServiceList.INSERT_DOCTORFOR,
                        UpdateServiceList.UPDATE_DOCTORFOR,
                        DeleteServiceList.DELETE_DOCTORFOR
                },
                new String[]{
                        "doctorID", "DOCTOR ID",
                        "patientID",  "PATIENT ID"
                },
                new String[]{},
                false,
                true,
                new String[]{"DOCTOR ID", "PATIENT ID"},
                new ReadService[]{ReadServiceList.GET_DOCTOR_NAMES, ReadServiceList.GET_PATIENT_NAMES}
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
                        "symptomDate", "DATE"
                },
                new String[]{"PERSON ID", "SYMPTOM ID"},
                true,
                true,
                new String[]{"PERSON ID", "SYMPTOM ID"},
                new ReadService[]{ReadServiceList.GET_PATIENT_NAMES, ReadServiceList.GET_SYMPTOM_NAMES}
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
                new String[]{},
                false,
                true,
                new String[]{"PERSON ID", "SYMPTOM ID"},
                new ReadService[]{ReadServiceList.GET_PATIENT_NAMES, ReadServiceList.GET_SYMPTOM_NAMES}
        ));


        context.addTab("Execute S-proc", buildSprocExecute());

        frame.add(context);
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private Container buildTableView(ReadService readService, CUDService[] services, String[] map, String[] fixedOnUpdate, boolean canUpdate, boolean canDelete, String[] args, ReadService[] IDProvider){
        HashMap<String, String> nameMatch = new HashMap<>();
        for(int i = 0; i < map.length; i += 2){
            nameMatch.put(map[i], map[i+1]);
        }
        return TableBuilder.buildTableWithCUD(readService, nameMatch, services[0], services[1], services[2], fixedOnUpdate, canUpdate, canDelete, args, IDProvider);
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

    private Container buildSprocExecute(){
        JPanel panel = new JPanel(new FlowLayout());
        JButton execute = new JButton("Execute S-proc");

        ArrayList<String> sprocNames = new ArrayList<>();
        HashMap<String, SprocContainer> sprocs = new HashMap<>();
        for(ReadService rs : ReadService.READ_SERVICES){
            sprocNames.add(rs.getSprocName());
            sprocs.put(rs.getSprocName(), new SprocContainer(rs));
        }
        for(CUDService cuds : CUDService.CUD_SERVICES){
            sprocNames.add(cuds.getSprocName());
            sprocs.put(cuds.getSprocName(), new SprocContainer(cuds));
        }


        JComboBox dropdown = new JComboBox(sprocNames.toArray());
        panel.add(dropdown);

        execute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sprocs.get(dropdown.getModel().getSelectedItem().toString()).BuildExecuteWindow();
            }
        });

        panel.add(execute);

        return panel;
    }
}
