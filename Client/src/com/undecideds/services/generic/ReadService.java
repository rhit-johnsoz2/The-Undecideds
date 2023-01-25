package com.undecideds.services.generic;

import com.undecideds.services.DatabaseConnectionService;
import com.undecideds.services.structs.Argument;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Types;

public class ReadService {

    StringBuilder template;
    Argument[] arguments;
    public ReadService(String sprocName, Argument[] arguments){
        template = new StringBuilder("{? = call " + sprocName + "(");
        this.arguments = arguments;
        for(int i = 0; i < arguments.length; i++){
            if(i == arguments.length - 1){
                template.append("?)}");
            }else{
                template.append("?, ");
            }
        }
    }

    public ResultSet ExecuteQuery(Object[] params) {
        try{
            CallableStatement statement = DatabaseConnectionService.getConnection().prepareCall(template.toString());
            statement.registerOutParameter(1, Types.INTEGER);
            int paramNumber = 2;
            for(Argument a : arguments){
                a.prepare(statement, paramNumber, params[paramNumber - 2]);
                paramNumber++;
            }
            return statement.executeQuery();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        return template.toString();
    }

}
