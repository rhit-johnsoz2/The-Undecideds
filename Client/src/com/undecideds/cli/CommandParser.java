package com.undecideds.cli;

import java.util.Locale;

public abstract class CommandParser {
    String tag;
    public CommandParser(String tag){
        this.tag = tag.toLowerCase();
        Commands.COMMANDS.add(this);
    }
    public boolean match(String tag){
        return tag.toLowerCase().equals(this.tag);
    }
    public abstract void execute(String[] args);
}
