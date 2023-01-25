package com.undecideds.services;

import com.undecideds.services.generic.CUDService;
import com.undecideds.services.generic.ReadService;
import com.undecideds.services.structs.Argument;

public class ReadServiceList {

    public static final ReadService INSERT_PERSON = new ReadService("InsertPerson", new Argument[]{
            //new Argument(Argument.ArgumentType.INT), // ID
            new Argument(Argument.ArgumentType.STRING), // FNAME
            new Argument(Argument.ArgumentType.STRING), // LNAME
            new Argument(Argument.ArgumentType.STRING), // LOGIN
            new Argument(Argument.ArgumentType.STRING), // PASSWORD
            new Argument(Argument.ArgumentType.STRING), // ROLE
            new Argument(Argument.ArgumentType.INT)  // INSURED BY
    });
}
