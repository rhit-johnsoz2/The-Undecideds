package com.undecideds.ui.cuduibuilder;

import java.awt.*;

public abstract class InputWidget {
    abstract Container generateWidget();
    abstract Object getValue();
    String argumentID;
}
