package com.undecideds.cli;

import com.undecideds.services.DeleteServiceList;
import com.undecideds.services.InsertServiceList;
import com.undecideds.services.ReadServiceList;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

public class SprocTests {
    public static void execute(){
        int result = InsertServiceList.INSERT_PERSON.ExecuteQuery(new Object[]{
                "Zachary", "Johnson", "johnsoz2", "Password123", "PA", 1
        });
        System.out.println(InsertServiceList.INSERT_PERSON.codeMeaning(result));

        result = InsertServiceList.INSERT_HEALTHCAREPROVIDER.ExecuteQuery(new Object[]{
                "Medics for Hire"
        });
        System.out.println(InsertServiceList.INSERT_HEALTHCAREPROVIDER.codeMeaning(result));

        result = InsertServiceList.INSERT_SYMPTOM.ExecuteQuery(new Object[]{
                "Headache"
        });
        System.out.println(InsertServiceList.INSERT_SYMPTOM.codeMeaning(result));

        result = InsertServiceList.INSERT_CHRONIC.ExecuteQuery(new Object[]{
                5, 2
        });
        System.out.println(InsertServiceList.INSERT_CHRONIC.codeMeaning(result));

        result = InsertServiceList.INSERT_TREATMENT.ExecuteQuery(new Object[]{
                "Advil", 6
        });
        System.out.println(InsertServiceList.INSERT_TREATMENT.codeMeaning(result));

        result = InsertServiceList.INSERT_INSURES.ExecuteQuery(new Object[]{
                2, 2, 2, 5
        });
        System.out.println(InsertServiceList.INSERT_INSURES.codeMeaning(result));

        result = InsertServiceList.INSERT_DOCTORFOR.ExecuteQuery(new Object[]{
                4, 5
        });
        System.out.println(InsertServiceList.INSERT_DOCTORFOR.codeMeaning(result));

        result = InsertServiceList.INSERT_PERFORMS.ExecuteQuery(new Object[]{
                4, 2
        });
        System.out.println(InsertServiceList.INSERT_PERFORMS.codeMeaning(result));

        result = InsertServiceList.INSERT_NEEDS.ExecuteQuery(new Object[]{
                5, 2, Date.valueOf("2021-03-15"), Date.valueOf("2021-05-15")
        });
        System.out.println(InsertServiceList.INSERT_NEEDS.codeMeaning(result));

        result = InsertServiceList.INSERT_SIDEEFFECTOF.ExecuteQuery(new Object[]{
                2, 2
        });
        System.out.println(InsertServiceList.INSERT_SIDEEFFECTOF.codeMeaning(result));

        result = InsertServiceList.INSERT_ACUTE.ExecuteQuery(new Object[]{
                4, 1, 4, Timestamp.valueOf("2021-04-01 09:11:17")
        });
        System.out.println(InsertServiceList.INSERT_ACUTE.codeMeaning(result));

        ResultSet rs = ReadServiceList.CHRONIC_FROM_PATIENT.ExecuteQuery(new Object[]{
                5
        });
        try {
            ArrayList<String> results = new ArrayList<>();
            int pidIndex = rs.findColumn("PatientID");
            int nameIndex = rs.findColumn("name");
            while(rs.next()) {
                results.add(rs.getInt(pidIndex) + ": " + rs.getString(nameIndex));
            }
            for(String s : results) {
                System.out.println(s);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        result = InsertServiceList.INSERT_PERSON.ExecuteQuery(new Object[]{
                "Illegal", "Patient", "illpat123", "Password123", "PA", 1
        });
        result = DeleteServiceList.DELETE_PERSON.ExecuteQuery(new Object[]{
                "Illegal", "Patient"
        });
        System.out.println(DeleteServiceList.DELETE_PERSON.codeMeaning(result));


        result = InsertServiceList.INSERT_HEALTHCAREPROVIDER.ExecuteQuery(new Object[]{
                "Imposter Doctors"
        });
        result = DeleteServiceList.DELETE_HEALTHCAREPROVIDER.ExecuteQuery(new Object[]{
                "Imposter Doctors"
        });
        System.out.println(DeleteServiceList.DELETE_HEALTHCAREPROVIDER.codeMeaning(result));


        result = InsertServiceList.INSERT_SYMPTOM.ExecuteQuery(new Object[]{
                "Headbake"
        });
        result = DeleteServiceList.DELETE_SYMPTOM.ExecuteQuery(new Object[]{
                "Headbake"
        });
        System.out.println(DeleteServiceList.DELETE_SYMPTOM.codeMeaning(result));


        result = InsertServiceList.INSERT_CHRONIC.ExecuteQuery(new Object[]{
                4, 2
        });
        result = DeleteServiceList.DELETE_CHRONIC.ExecuteQuery(new Object[]{
                4, 2
        });
        System.out.println(DeleteServiceList.DELETE_CHRONIC.codeMeaning(result));


        result = InsertServiceList.INSERT_TREATMENT.ExecuteQuery(new Object[]{
                "Badvil", 6
        });
        result = DeleteServiceList.DELETE_TREATMENT.ExecuteQuery(new Object[]{
                "Badvil"
        });
        System.out.println(DeleteServiceList.DELETE_TREATMENT.codeMeaning(result));


        result = InsertServiceList.INSERT_INSURES.ExecuteQuery(new Object[]{
                2, 2, 1, 500
        });
        result = DeleteServiceList.DELETE_INSURES.ExecuteQuery(new Object[]{
                2, 2, 1
        });
        System.out.println(DeleteServiceList.DELETE_INSURES.codeMeaning(result));


        result = InsertServiceList.INSERT_DOCTORFOR.ExecuteQuery(new Object[]{
                4, 2
        });
        result = DeleteServiceList.DELETE_DOCTORFOR.ExecuteQuery(new Object[]{
                4, 2
        });
        System.out.println(DeleteServiceList.DELETE_DOCTORFOR.codeMeaning(result));


        result = InsertServiceList.INSERT_PERFORMS.ExecuteQuery(new Object[]{
                4, 1
        });
        result = DeleteServiceList.DELETE_PERFORMS.ExecuteQuery(new Object[]{
                4, 1
        });
        System.out.println(DeleteServiceList.DELETE_PERFORMS.codeMeaning(result));


        result = InsertServiceList.INSERT_NEEDS.ExecuteQuery(new Object[]{
                2, 2, Date.valueOf("2015-03-15"), Date.valueOf("2015-05-15")
        });
        result = DeleteServiceList.DELETE_NEEDS.ExecuteQuery(new Object[]{
                2, 2
        });
        System.out.println(DeleteServiceList.DELETE_NEEDS.codeMeaning(result));


        result = InsertServiceList.INSERT_SIDEEFFECTOF.ExecuteQuery(new Object[]{
                1, 2
        });
        result = DeleteServiceList.DELETE_SIDEEFFECTOF.ExecuteQuery(new Object[]{
                1, 2
        });
        System.out.println(DeleteServiceList.DELETE_SIDEEFFECTOF.codeMeaning(result));


        result = InsertServiceList.INSERT_ACUTE.ExecuteQuery(new Object[]{
                5, 2, 4, Timestamp.valueOf("2019-04-01 10:11:17")
        });
        result = DeleteServiceList.DELETE_ACUTE.ExecuteQuery(new Object[]{
                5, 2
        });
        System.out.println(DeleteServiceList.DELETE_ACUTE.codeMeaning(result));
    }
}
