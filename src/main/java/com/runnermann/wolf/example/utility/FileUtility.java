package com.runnermann.wolf.example.utility;

import java.io.File;

public class FileUtility {

    public static final File HOME_DIRECTORY = new File(System.getProperty("user.home"));
    public static final File DESKTOP = new File(System.getProperty("user.home"), "/Desktop");

    public static String getWorkingDirectory() {
        String OS = (System.getProperty("os.name")).toUpperCase();
        if (OS.contains("WIN")) {
            return System.getenv("AppData");
        } else {
            //System.out.println("\n\n **** NOT A WINDOWS SYSTEM? ***\n\n");
            String home = System.getProperty("user.home");
            return home + "/Library/Application Support";
        }
    }
}
