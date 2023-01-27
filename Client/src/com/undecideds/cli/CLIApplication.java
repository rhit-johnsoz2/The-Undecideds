package com.undecideds.cli;

import com.undecideds.services.DatabaseConnectionService;
import com.undecideds.services.InsertServiceList;
import com.undecideds.services.ReadServiceList;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

public class CLIApplication {
    Scanner sc;
    public void Launch(String[] args){
        sc = new Scanner(System.in);
        DatabaseConnectionService.InitDatabaseConnectionService("titan.csse.rose-hulman.edu", "SymptomTracker");
        boolean connected = DatabaseConnectionService.connect("johnsoz2", "");
        if(!connected){
            System.out.println("Connection failed!");
            System.exit(401);
        }
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
    }

    public String QueryUser(String query){
        return sc.nextLine();
    }

    static abstract class ArgumentParser {
        String tag;
        public ArgumentParser(String tag){
            this.tag = tag;
        }
        public boolean match(String tag, String[] args){
            return tag.equals(this.tag);
        }
        public abstract void execute();
    }

}
