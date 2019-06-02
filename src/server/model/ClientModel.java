package server.model;

import both.Config;
import server.controller.interfaces.ServerInterface;
import server.entity.ClientEntity;
import server.entity.PinEntity;

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

        new Thread(() -> { while (true) {

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
    public boolean hasThreeInARow() {

        for (PinModel p : this.pinModels)
            if (this.containsPin(p.getX() - 1, p.getY() + 1) && this.containsPin(p.getX() - 2, p.getY() + 2) ||
                this.containsPin(p.getX()    , p.getY() + 1) && this.containsPin(p.getX()    , p.getY() + 2) ||
                this.containsPin(p.getX() + 1, p.getY() + 1) && this.containsPin(p.getX() + 2, p.getY() + 2) ||
                this.containsPin(p.getX() + 1, p.getY()    ) && this.containsPin(p.getX() + 2, p.getY()    ))
                return true;

        return false;
    }

    public boolean containsPin(int x, int y) {

        for (PinModel pinModel : this.pinModels)
            if (pinModel.getX() == x && pinModel.getY() == y)
                return true;

        return false;
    }

    public void cleanClient() {

        this.pinModels = new ArrayList<>();
        this.gameModel = null;

        this.isFinished(false);
        this.isPlaying(true);
    }

    // setters
    public void setGameModel(GameModel gameModel)           { this.gameModel = gameModel;               }
    public void setPinModels(ArrayList<PinModel> pinModels) { this.pinModels = pinModels;               }
    public void isPlaying(boolean isPlaying)                { this.clientEntity.isPlaying(isPlaying);   }
    public void isFinished(boolean isFinished)              { this.clientEntity.isFinished(isFinished); }

    public void addPin(PinEntity pinEntity) {

        PinModel pinModel = new PinModel(pinEntity);
        pinModel.setClientModel(this);

        if (this.pinModels.size() >= Config.GAME_PIN_PER_CLIENT)
            this.pinModels.remove(0);

        this.pinModels.add(pinModel);
    }

    // getter
    public String    getName()      { return this.clientEntity.getName();    }
    public Color     getColor()     { return this.clientEntity.getColor();   }
    public GameModel getGameModel() { return this.gameModel;                 }
    public boolean   isPlaying()    { return this.clientEntity.isPlaying();  }
    public boolean   isInGame()     { return this.gameModel != null;         }
    public boolean   isFinished()   { return this.clientEntity.isFinished(); }

    public ClientEntity getClientEntity() {

        this.clientEntity.setPinEntities(this.pinModels);

        return this.clientEntity;
    }
}
