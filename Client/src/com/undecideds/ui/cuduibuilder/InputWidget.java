package com.undecideds.ui.cuduibuilder;

import java.awt.*;

public abstract class InputWidget {
    public abstract Container generateWidget();
    public abstract Object getValue();
    String argumentID;

    public InputWidget(String argumentID) {
        this.argumentID = argumentID;
    }

    public String getArgumentID() {
        return argumentID;
    }
}
