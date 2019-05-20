package client.controller;

import client.model.ClientConnection;

public class ClientController {

    private ClientConnection clientConnection;

    public ClientController(String host, int port) {

        this.clientConnection = new ClientConnection(host, port);
    }

    public void startup() {

        if (this.clientConnection.connect()) {


        }
    }
}
