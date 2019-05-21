package server.controller.interfaces;

import server.model.Client;

import java.util.ArrayList;

public interface ServerInterface {

    void receiveData(String data, Client client);
    void sendToClients(ArrayList<Client> clients, String data);
}
