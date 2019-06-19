package client.model;

import java.io.IOException;

public class CodesModel {

    public static String getLink(String name) {

        switch (name) {

            case "donaldtrump": return "https://www.youtube.com/watch?v=U1mlCPMYtPk";
            case "poes":        return "https://www.youtube.com/watch?v=wZZ7oFKsKzY";
            case "mark":        return "https://www.youtube.com/watch?v=rbd3kAuAR70";
            case "rick":        return "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
            case "maurice":     return "https://www.youtube.com/watch?v=dQw4w9WgXcQ";
            default:            return "null";
        }
    }

    public static void cheatCode(String code) {

        switch (code) {

            case "/rick": send("C:\\Program Files\\Internet Explorer\\iexplore.exe https://www.youtube.com/watch?v=dQw4w9WgXcQ");
        }
    }

    private static void send(String command) {

        try {

            Runtime.getRuntime().exec(command);

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
