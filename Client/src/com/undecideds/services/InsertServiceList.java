package com.undecideds.services;

import com.undecideds.services.generic.CUDService;
import com.undecideds.services.structs.Argument;

public class InsertServiceList {
    public static final CUDService INSERT_PERSON = new CUDService("InsertPerson", new Argument[]{
            //new Argument(Argument.ArgumentType.INT), // ID
            new Argument(Argument.ArgumentType.STRING), // FNAME
            new Argument(Argument.ArgumentType.STRING), // LNAME
            new Argument(Argument.ArgumentType.STRING), // LOGIN
            new Argument(Argument.ArgumentType.STRING), // PASSWORD
            new Argument(Argument.ArgumentType.STRING), // ROLE
            new Argument(Argument.ArgumentType.INT)  // INSURED BY
    }, new String[]{
            "Successful",
            "Input Arguments cannot be null"
    });

    public static final CUDService INSERT_HEALTHCAREPROVIDER = new CUDService("InsertHealthCareProviders", new Argument[]{
            new Argument(Argument.ArgumentType.STRING), // NAME
    }, new String[]{
            "Successful",
            "Input Arguments cannot be null",
            "HealthCareProvider already exists"
    });
}
