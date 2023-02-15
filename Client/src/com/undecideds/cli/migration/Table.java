package com.undecideds.cli.migration;

import com.undecideds.services.generic.CUDService;
import com.undecideds.services.structs.SprocContainer;

import java.util.ArrayList;
import java.util.HashMap;

public class Table {
    String ID;
    HashMap<Integer, Integer> idTable;
    public Table(ArrayList<Table> tables, String path, SprocContainer sproc, String idSpecs){
        idTable = new HashMap<>();
        ID = idSpecs.split(":")[1];
        String oldID = idSpecs.split(":")[0];

    }
}
