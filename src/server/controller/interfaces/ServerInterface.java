package server.controller.interfaces;

import org.json.simple.JSONObject;
import server.model.Client;
import server.model.Game;
import server.model.Pin;

import java.util.ArrayList;

public interface ServerInterface {

    // connection
    void receiveObject(Client client, Object object);

    // game
    void writeObject(ArrayList<Client> clients, Object object);

    // Client
    void addToGame(Client client);
}
