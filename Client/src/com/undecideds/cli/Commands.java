package com.undecideds.cli;

import com.undecideds.cli.migration.Table;
import com.undecideds.services.generic.CUDService;
import com.undecideds.services.generic.ReadService;
import com.undecideds.services.structs.Argument;
import com.undecideds.services.structs.SprocContainer;

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
    public static final CommandParser LOAD_CSV = new CommandParser("load") {
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
    public static final CommandParser IMPORT_DATABASE = new CommandParser("import") {
        @Override
        public void execute(String[] args) {
            for(CUDService cud : CUDService.CUD_SERVICES){
                System.out.println(cud.toString());
            }
            String path = args[0];
            ArrayList<Table> tables = new ArrayList<>();
            for(int i = 1; i < args.length; i++){
                String[] subArgs = args[i].split("\\|");
                SprocContainer SC = getSproc(subArgs[0]);
                if(SC == null){
                    System.out.println("Unknown S-Proc: " + subArgs[0]);
                }
                System.out.println("Adding table for " + subArgs[1]);
                tables.add(new Table(tables, path + subArgs[1], SC, subArgs[2]));
            }
        }
        private SprocContainer getSproc(String name){
            ReadService readService = ReadService.getServiceFromName(name);
            if(readService != null){
                return new SprocContainer(readService);
            }
            CUDService cudService = CUDService.getServiceFromName(name);
            if(cudService != null){
                return new SprocContainer(cudService);
            }
            return null;
        }
    };
}
