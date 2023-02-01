package com.undecideds.cli;

import com.undecideds.services.DatabaseConnectionService;
import com.undecideds.services.InsertServiceList;
import com.undecideds.services.ReadServiceList;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

public class CLIApplication {
    Scanner sc;
    public void Launch(String[] args){
        sc = new Scanner(System.in);
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
