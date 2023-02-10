package com.undecideds.cli;

import com.undecideds.services.generic.CUDService;
import com.undecideds.services.structs.Argument;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Commands {
    public static final ArrayList<CommandParser> COMMANDS = new ArrayList<>();
    public static final CommandParser EXECUTE_CUD = new CommandParser("exec") {
        @Override
        public void execute(String[] args) {
            for(CUDService cuds : CUDService.CUD_SERVICES){
                if(cuds.getSprocName().equalsIgnoreCase(args[0])){
                    cuds.executeCLI();
                    return;
                }
            }
            System.out.println("Unknown CUD sproc: " + args[0]);
        }
    };
    public static final CommandParser IMPORT_CSV = new CommandParser("load") {
        @Override
        public void execute(String[] args) {
            CUDService service = null;
            for(CUDService cuds : CUDService.CUD_SERVICES){
                if(cuds.getSprocName().equalsIgnoreCase(args[0])){
                    service = cuds;
                    break;
                }
            }
            if(service == null){
                System.out.println("Unknown CUD sproc: " + args[0]);
                return;
            }
            try {
                File f = new File(args[1]);
                BufferedReader br = new BufferedReader(new FileReader(f));
                String line = br.readLine();
                while((line = br.readLine()) != null){
                    String[] elements = line.split(",");
                    System.out.println(service.codeMeaning(service.executeFromStrings(elements)));
                }
            }catch (Exception e){
                System.out.println("Couldn't open file " + args[1]);
            }
        }
    };
}
