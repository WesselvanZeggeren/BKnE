package server.model;

import both.Config;
import server.controller.ServerApplication;
import server.entity.GameEntity;

import java.util.ArrayList;

public class GameModel implements Runnable {

    // attributes
    private ServerApplication observer;
    private GameEntity gameEntity;

    private ArrayList<ClientModel> clientModels;
    private ArrayList<PinModel> pinModels;

    // startup
    public GameModel(ServerApplication observer) {

        this.observer = observer;

        this.clientModels = new ArrayList<>();
        this.pinModels = new ArrayList<>();

        this.gameEntity = new GameEntity();
    }

    // methods
    @Override
    public void run() {

        if (this.gameEntity.isRunning()) {

            this.gameEntity.setSize(0, this.clientModels.size());

            for (int x = 0; x < this.gameEntity.getSize(); x++)
                for (int y = 0; y < this.gameEntity.getSize(); y++)
                    this.pinModels.add(new PinModel(x, y));

            this.startGame();
        }
    }

    private void startGame() {

    }

    public boolean isFreePin(int x, int y) {

        for (ClientModel clientModel : this.clientModels)
            if (clientModel.containsPin(x, y))
                return false;

        return true;
    }

    // setters
    public void addClient(ClientModel clientModel) {

        clientModel.setGameModel(this);

        this.clientModels.add(clientModel);

        if (this.clientModels.size() >= Config.GAME_MAX_PLAYERS)
            this.gameEntity.isRunning(true);

        System.out.println(this.clientModels);

        this.observer.writeObject(this.clientModels, "<" + clientModel.getName() + "> Joined the game!");
        this.observer.writeObject(this.clientModels, this.getGameEntity());
    }

    public void removeClients() {

        for (ClientModel clientModel : this.clientModels) {

            clientModel.setGameModel(null);
            clientModel.setPinModels(new ArrayList<>());
        }

        this.clientModels = new ArrayList<>();
    }

    // getters
    public ArrayList<ClientModel> getClientModels() {

        return this.clientModels;
    }

    public boolean isRunning() {

        return this.gameEntity.isRunning();
    }

    private GameEntity getGameEntity() {

        this.gameEntity.setClientEntities(this.clientModels);
        this.gameEntity.setPinEntities(this.pinModels);

        return this.gameEntity;
    }
}
