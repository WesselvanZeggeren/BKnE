package server;

import server.controller.ServerController;

public class Main {

    public static void main(String[] args) {

        ServerController serverController = new ServerController(1000);
        serverController.startup();
    }
}
