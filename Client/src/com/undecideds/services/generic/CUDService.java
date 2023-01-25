package com.undecideds.services.generic;

import com.undecideds.services.DatabaseConnectionService;
import com.undecideds.services.structs.Argument;

import java.sql.CallableStatement;
import java.sql.Types;

public class CUDService {
    StringBuilder template;
    Argument[] arguments;
    String[] resultCodes;
    public CUDService(String sprocName, Argument[] arguments, String[] resultCodes){
        template = new StringBuilder("{? = call " + sprocName + "(");
        this.arguments = arguments;
        this.resultCodes = resultCodes;
        for(int i = 0; i < arguments.length; i++){
            if(i == arguments.length - 1){
                template.append("?)}");
            }else{
                template.append("?, ");
            }
        }
    }

    public int ExecuteQuery(Object[] params) {
        try{
            CallableStatement statement = DatabaseConnectionService.getConnection().prepareCall(template.toString());
            statement.registerOutParameter(1, Types.INTEGER);
            int paramNumber = 2;
            for(Argument a : arguments){
                a.prepare(statement, paramNumber, params[paramNumber - 2]);
                paramNumber++;
            }
            statement.execute();
            return statement.getInt(1);
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public String toString() {
        return template.toString();
    }

    public String codeMeaning(int code){
        if(code == -1){
            return "Failed to parse params, check stacktrace";
        }
        return resultCodes[code];
    }
}
