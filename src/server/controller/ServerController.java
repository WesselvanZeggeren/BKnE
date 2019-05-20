package server.controller;

import server.model.ServerConnection;
import server.model.entity.Client;

import java.util.ArrayList;

public class ServerController {

    private ServerConnection serverConnection;
    private ArrayList<Client> clients;
    private ArrayList<Thread> threads;

    public ServerController(int port) {

        this.serverConnection = new ServerConnection(this, port);
        this.clients = new ArrayList<>();
        this.threads = new ArrayList<>();
    }

    public void startup() {

        if (this.serverConnection.start(this.clients, this.threads)) {


        }
    }

    public void sendToAllClients (String text) {

        for (Client client : this.clients) {

            client.writeUTF(text);
        }
    }
}
