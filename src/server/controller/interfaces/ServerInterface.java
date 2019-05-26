package server.controller.interfaces;

import org.json.simple.JSONObject;
import server.model.Client;

import java.util.ArrayList;

public interface ServerInterface {

    // connection
    void receiveData(JSONObject json, Client client);
    void sendToClients(ArrayList<Client> clients, String data);

    // Client
    void addToGame(Client client);
}
