package com.undecideds;

import com.undecideds.cli.CLIApplication;
import com.undecideds.ui.ClientWindow;

//Authors: johnsoz2

public class Main {
    public static void main(String[] args){
        if(args.length == 0){
            ClientWindow window = new ClientWindow();
            window.launch();
        }else{
            // CML-interface + csv upload
            if(args[0].equals("-nogui")){
                CLIApplication clip = new CLIApplication();
                clip.Launch(args);
            }else{
                // ...
            }

        }
    }
}
