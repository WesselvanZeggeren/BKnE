package server;

import client.controller.ClientApplication;
import server.controller.ServerApplication;

public class Main {

    public static void main(String[] args) {

//        ServerApplication serverApplication = new ServerApplication();
//        serverApplication.startup();

        // testing reasons
        ClientApplication clientApplication = new ClientApplication();
        clientApplication.startup();
    }
}
