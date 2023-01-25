package com.undecideds.services.structs;

import java.sql.CallableStatement;

public class Argument {
    ArgumentType type;
    public Argument(ArgumentType type){
        this.type = type;
    }

    public boolean prepare(CallableStatement statement, int index, Object o){
        try{
            switch (type){
                case STRING -> {statement.setString(index, (String) o); return true;}
                case INT -> {statement.setInt(index, (int) o); return true;}
                case FLOAT -> {statement.setFloat(index, (float) o); return true;}
                default -> {System.out.println("Error parsing argument, no type " + type.name());}
            }
        }catch (Exception e){
            System.out.println("Error parsing argument " + o.toString() + " doesn't match the object type " + type.name());
        }
        return false;
    }

    public enum ArgumentType{
        STRING,
        INT,
        FLOAT
    }
}
