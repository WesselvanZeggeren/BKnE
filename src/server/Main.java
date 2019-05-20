package server;

import config.Config;
import server.controller.ServerController;

public class Main {

    public static void main(String[] args) {

        ServerController serverController = new ServerController(Config.SERVER_PORT);
        serverController.startup();
    }
}
