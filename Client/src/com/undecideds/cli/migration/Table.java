package com.undecideds.cli.migration;

import com.undecideds.services.generic.CUDService;
import com.undecideds.services.generic.ReadService;
import com.undecideds.services.structs.SprocContainer;
import com.undecideds.ui.builders.TableBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class Table {
    String ID;
    HashMap<Integer, Integer> idTable;
    public Table(ArrayList<Table> tables, String path, SprocContainer sproc, String idSpecs){
        idTable = new HashMap<>();
        ID = "";
        String oldID = "";
        if(idSpecs.length() != 0 && !idSpecs.equals("none")) {
            ID = idSpecs.split(":")[1];
            oldID = idSpecs.split(":")[0];
        }

        try {
            File f = new File(path);
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line = br.readLine();
            String[] columnHeaders = line.split(",");
            int argNum = columnHeaders.length;
            for(String s : columnHeaders){
                if(s.equals(oldID)){
                    argNum--;
                }
            }
            while((line = br.readLine()) != null){
                String[] elements = line.split(",");
                String[] args = new String[argNum];
                int oldIDVal = -1;
                int c = 0;
                for(int i = 0; i < elements.length; i++){
                    if(columnHeaders[i].substring(1).equals(oldID.trim())){
                        oldIDVal = Integer.parseInt(elements[i]);
                    }else{
                        args[c] = checkIDMatch(elements[i], columnHeaders[i], tables);
                        c++;
                    }
                }
                if(sproc.isReadService()){
                    ResultSet rs = sproc.getRs().executeFromStrings(args);
                    if(oldIDVal != -1){
                        idTable.put(oldIDVal, (int)ReadService.getSingleton(rs));
                    }
                }else{
                    int status = sproc.getCuds().executeFromStrings(args);
                    if(status != 0){
                        //System.out.println(sproc.getCuds().codeMeaning(status));
                    }
                }
                //System.out.println(service.codeMeaning(service.executeFromStrings(elements)));
            }
            br.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private String checkIDMatch(String src, String key, ArrayList<Table> tables){
        for(Table t : tables){
            if(t.ID.equals(key)){
                return t.idTable.get(Integer.parseInt(src)).toString();
            }
        }
        return src;
    }
}
