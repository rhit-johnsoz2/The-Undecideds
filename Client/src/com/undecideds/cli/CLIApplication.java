package com.undecideds.cli;

import com.undecideds.services.DeleteServiceList;
import com.undecideds.services.InsertServiceList;
import com.undecideds.services.UpdateServiceList;

import java.util.Scanner;

public class CLIApplication {
    static Scanner sc;
    public static void Launch(String[] args){
        sc = new Scanner(System.in);
        // These force initialization of sprocs which doesn't happen otherwise (???)
        InsertServiceList.INSERT_PERSON.toString();
        UpdateServiceList.UPDATE_PERSON.toString();
        DeleteServiceList.DELETE_PERSON.toString();
        while(true){
            parse(QueryUser("$ "));
        }
    }

    public static String QueryUser(String query){
        System.out.print(query);
        return sc.nextLine();
    }

    public static void parse(String s){
        String cmd = s.split(" ")[0];
        String[] args = s.substring(s.indexOf(" ") + 1).split(" ");
        for(CommandParser commandParser : Commands.COMMANDS){
            if(commandParser.match(cmd)){
                commandParser.execute(args);
                return;
            }
        }
        System.out.println("Unknown command: " + cmd);
    }

}
