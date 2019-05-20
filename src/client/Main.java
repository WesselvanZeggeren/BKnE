package client;

import client.controller.ClientController;

public class Main {

    public static void main(String[] args) {

        ClientController clientController = new ClientController();
        clientController.startup();
    }
}
