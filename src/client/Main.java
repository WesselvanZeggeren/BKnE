package client;

import client.controller.ClientController;
import config.Config;

public class Main {

    public static void main(String[] args) {

        ClientController clientController = new ClientController(Config.SERVER_HOST, Config.SERVER_PORT);
        clientController.startup();
    }
}
