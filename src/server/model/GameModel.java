package server.model;

import both.Config;
import both.Texture;
import server.controller.ServerApplication;
import server.entity.GameEntity;
import server.entity.PinEntity;

import java.util.ArrayList;

public class GameModel {

    // attributes
    private ServerApplication observer;
    private GameEntity gameEntity;

    private ArrayList<PinModel> pinModels;
    private ArrayList<ClientModel> clientModels;
    private ArrayList<ClientModel> clientModelsOrder;

    private int key;

    // startup
    public GameModel(ServerApplication observer) {

        this.observer = observer;

        this.gameEntity = new GameEntity();

        this.key = 0;

        this.pinModels = new ArrayList<>();
        this.clientModels = new ArrayList<>();
        this.clientModelsOrder = new ArrayList<>();
    }

    // methods
    public void startGame(String message) {

        this.gameEntity.isRunning(true);
        this.gameEntity.setSize(0, this.getPlayingClients());

        this.pinModels = new ArrayList<>();

        for (int x = 0; x < this.gameEntity.getSize(); x++)
            for (int y = 0; y < this.gameEntity.getSize(); y++)
                this.pinModels.add(new PinModel(x, y));

        this.observer.writeObject(this.clientModels, message);
        this.observer.writeObject(this.clientModels, this.getGameEntity());
    }

    private void nextRound(boolean restart) {

        if (this.getPlayingClients() > 2 || restart && this.getPlayingClients() > 1) {

            this.refreshClients();

            if (!restart)
                this.getCurrentClient().isPlaying(false);

            this.clientModels = this.getClientModelsOrder();
            this.clientModelsOrder = new ArrayList<>();

            this.key = 0;

            this.startGame("NEXT ROUND!");

        } else {

            if (this.clientModels.size() == 1)
                this.observer.writeObject(this.clientModels, "CONGRATULATIONS " + this.clientModels.get(0).getName() + "! YOU WON!");
            else
                this.observer.writeObject(this.clientModels, "CONGRATULATIONS " + this.clientModelsOrder.get(0).getName() + "! YOU WON!");

            this.gameEntity.isRunning(false);
        }
    }

    public void receivePin(ClientModel clientModel, PinEntity pinEntity) {

        if (this.getCurrentClient().equals(clientModel) && this.isFreePin(pinEntity.getX(), pinEntity.getY()) && this.gameEntity.isRunning()) {

            pinEntity.setTexture(Texture.getPinTexture(clientModel.getColor()));

            clientModel.addPin(pinEntity);

            this.checkThreeInARow(clientModel);
            this.nextClientModel();

            this.observer.writeObject(this.clientModels, this.getGameEntity());

            if (this.getNotFinishedClients() == 1)
                this.nextRound(false);
        }
    }

    public void receiveCommand(String command) {

        switch (command) {
            case Config.TEXT_START:
                this.startGame("START GAME!");
        }
    }

    private void refreshClients() {

        for (ClientModel clientModel : this.clientModels) {

            clientModel.setPinModels(new ArrayList<>());
            clientModel.isFinished(false);
        }
    }

    private void nextClientModel() {

        this.key++;

        if (this.key == this.clientModels.size())
            this.key = 0;

        if (this.getCurrentClient().isFinished() || !this.getCurrentClient().isPlaying())
            this.nextClientModel();
    }

    private void checkThreeInARow(ClientModel clientModel) {

        if (clientModel.hasThreeInARow()) {

            this.clientModelsOrder.add(clientModel);
            clientModel.isFinished(true);
        }
    }

    private boolean isFreePin(int x, int y) {

        for (ClientModel clientModel : this.clientModels)
            if (clientModel.containsPin(x, y))
                return false;

        return true;
    }

    // setters
    public void addClient(ClientModel clientModel) {

        clientModel.setGameModel(this);

        this.clientModels.add(clientModel);

        this.observer.writeObject(this.clientModels, clientModel.getName() + " JOINED THE GAME!");
        this.observer.writeObject(this.clientModels, this.getGameEntity());

        if (this.clientModels.size() >= Config.GAME_MAX_PLAYERS)
            this.startGame("START GAME!");
    }

    public void removeClient(ClientModel clientModel) {

        if (this.gameEntity.isRunning() && this.getCurrentClient().equals(clientModel))
            this.nextClientModel();

        this.clientModels.remove(clientModel);
        this.clientModelsOrder.remove(clientModel);

        this.observer.writeObject(this.getClientModels(), clientModel.getName() + " LEFT THE GAME!");
        this.observer.writeObject(this.getClientModels(), this.getGameEntity());

        if (this.gameEntity.isRunning() && clientModel.isPlaying())
            this.nextRound(true);
    }

    // getters
    public ArrayList<ClientModel> getClientModels()  { return this.clientModels;               }
    public ClientModel            getCurrentClient() { return this.clientModels.get(this.key); }
    public boolean                isRunning()        { return this.gameEntity.isRunning();     }

    public int getPlayingClients() {

        int amount = 0;

        for (ClientModel clientModel : this.clientModels)
            if (clientModel.isPlaying())
                amount++;

        return amount;
    }

    public int getNotFinishedClients() {

        int amount = 0;

        for (ClientModel clientModel : this.clientModels)
            if (clientModel.isFinished())
                amount++;

        return this.getPlayingClients() - amount;
    }

    public GameEntity getGameEntity() {

        this.gameEntity.setClientEntitiesOrder(this.clientModelsOrder);
        this.gameEntity.setClientEntities(this.clientModels);
        this.gameEntity.setPinEntities(this.pinModels);
        this.gameEntity.setKey(this.key);

        return this.gameEntity;
    }

    private ArrayList<ClientModel> getClientModelsOrder() {

        for (ClientModel clientModel : this.clientModels)
            if (!this.clientModelsOrder.contains(clientModel))
                this.clientModelsOrder.add(clientModel);

        return this.clientModelsOrder;
    }
}
