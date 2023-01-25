package com.undecideds.cli;

import com.undecideds.services.DatabaseConnectionService;
import com.undecideds.services.InsertServiceList;

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
        result = InsertServiceList.INSERT_PERSON.ExecuteQuery(new Object[]{
                "Medics for Hire"
        });
        System.out.println(InsertServiceList.INSERT_HEALTHCAREPROVIDER.codeMeaning(result));
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
