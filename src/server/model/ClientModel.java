package server.model;

import server.controller.interfaces.ServerInterface;
import server.entity.ClientEntity;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientModel implements Runnable {

    // attributes
    private Socket socket;
    private GameModel gameModel;
    private ServerInterface observer;
    private ClientEntity clientEntity;
    private ArrayList<PinModel> pinModels;

    private ObjectInputStream objectIn;
    private ObjectOutputStream objectOut;

    private boolean isRunning = true;

    // constructor
    public ClientModel(Socket socket, ServerInterface observer) {

        this.socket = socket;
        this.observer = observer;
        this.pinModels = new ArrayList<>();
        this.clientEntity = new ClientEntity();
    }

    // connection
    @Override
    public void run() {

        try {

            this.objectOut = new ObjectOutputStream(this.socket.getOutputStream());
            this.objectIn  = new ObjectInputStream(this.socket.getInputStream());

            this.manageObjectInput();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    private void manageObjectInput() {

        new Thread(() -> { while (this.isRunning) {

            try {

                Object object = this.objectIn.readObject();

                if (this.clientEntity.getName().length() == 0 && object instanceof String) {

                    this.clientEntity.setName((String) object);
                    this.observer.addToGame(this);
                } else {

                    this.observer.receiveObject(this, object);
                }
            } catch (Exception e) {

                e.printStackTrace();
            }
        }}).start();
    }

    public void writeObject(Object object) {

        try {

            this.objectOut.writeObject(object);
            this.objectOut.reset();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    // methods
    public boolean containsPin(int x, int y) {

        for (PinModel pinModel : this.pinModels)
            if (pinModel.getX() == x && pinModel.getY() == y)
                return true;

        return false;
    }

    // getters
    public String getName() {

        return this.clientEntity.getName();
    }

    public Color getColor() {

        return this.clientEntity.getColor();
    }

    public GameModel getGameModel() {

        return this.gameModel;
    }

    public boolean isInGame() {

        return (this.gameModel != null);
    }

    public ClientEntity getClientEntity() {

        this.clientEntity.setPinEntities(this.pinModels);

        return this.clientEntity;
    }

    // setters
    public void setGameModel(GameModel gameModel) {

        this.gameModel = gameModel;
    }

    public void setPinModels(ArrayList<PinModel> pinModels) {

        this.pinModels = pinModels;
    }

    public void addPin(PinModel pinModel) {

        this.pinModels.add(pinModel);
    }
}