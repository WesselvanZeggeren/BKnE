package server.controller.interfaces;

import server.model.Client;
import server.model.ClientData;

import java.util.ArrayList;

public interface ServerInterface {

    // connection
    void receiveData(ClientData clientData);
    void sendToClients(ArrayList<Client> clients, String data);

    // Client
    void addToGame(Client client);
}
