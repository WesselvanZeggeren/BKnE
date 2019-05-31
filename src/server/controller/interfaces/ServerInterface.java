package server.controller.interfaces;

import server.model.ClientModel;

import java.util.ArrayList;

public interface ServerInterface {

    // connection
    void receiveObject(ClientModel clientModel, Object object);

    // game
    void writeObject(ArrayList<ClientModel> clientModels, Object object);

    // Client
    void addToGame(ClientModel clientModel);
}
