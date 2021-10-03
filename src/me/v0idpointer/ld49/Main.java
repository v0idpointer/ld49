package me.v0idpointer.ld49;

import java.util.Arrays;
import java.util.HashMap;

public class Main {

    public static final String PROJECT = "Ludum Dare 49";
    public static final String VERSION = "1.0.0.031021.ld_release";

    public static final String[] gameArguments =
            {
                    "/v", // Show FPS / Game performance.
                    "/d", // Enable developer mode
                    "/i", // Insecure Mode
                    "/sv", // Skips the startup animation
                    "/nf", // Disable "focus nagger"
                    "/nt", // Disable Always on Top
                    "/ni", // No fake glitching
                    "/L", // skip Launch options.
            };

    public static void main(String[] args) {
        System.out.println(PROJECT + "\nversion: " + VERSION);

        int flags = 0;
        HashMap<String, String> data = new HashMap<>();

        for(String arg : args) {

            if(arg.contains("=")) {
                String[] tokens = arg.split("=");
                data.put(tokens[0], tokens[1]);
            }
            else {
                int pos = Arrays.asList(gameArguments).indexOf(arg);
                if(pos >= 0) flags |= (int)(Math.pow(2, pos));
            }

        }

        new Game(flags, data);
    }

}
