package client.controller;

import client.model.ClientConnection;

public class ClientController {

    private ClientConnection clientConnection;

    public ClientController() {

        this.clientConnection = new ClientConnection();
    }

    public void startup() {

        if (this.clientConnection.connect()) {

            System.out.println("connected");
        }
    }
}
