package server;

import server.controller.ServerApplication;

public class Main {

    public static void main(String[] args) {

        ServerApplication serverApplication = new ServerApplication();
        serverApplication.startup();
    }
}
