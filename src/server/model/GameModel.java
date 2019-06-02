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

        this.clientModels = new ArrayList<>();
        this.clientModelsOrder = new ArrayList<>();
    }

    // methods
    public void startGame() {

        this.gameEntity.setSize(0, this.getPlayingClients());
        this.pinModels = new ArrayList<>();
        this.key = 0;

        for (int x = 0; x < this.gameEntity.getSize(); x++)
            for (int y = 0; y < this.gameEntity.getSize(); y++)
                this.pinModels.add(new PinModel(x, y));
    }

    private void nextRound(boolean restart) {

        if (this.getPlayingClients() > 2) {

            this.refreshClients();

            if (!restart)
                this.getCurrentClient().isPlaying(false);

            this.startGame();

            this.clientModels = this.getClientModelsOrder();
            this.clientModelsOrder = new ArrayList<>();

            this.observer.writeObject(this.clientModels, "NEXT ROUND");
            this.observer.writeObject(this.clientModels, this.getGameEntity());
        } else {

            this.observer.writeObject(this.clientModels, "CONGRATULATIONS " + this.clientModelsOrder.get(0).getName() + "!!!");
        }
    }

    public void receivePin(ClientModel clientModel, PinEntity pinEntity) {

        if (this.getCurrentClient().equals(clientModel) && this.isFreePin(pinEntity.getX(), pinEntity.getY())) {

            pinEntity.setTexture(Texture.getPinTexture(clientModel.getColor()));

            clientModel.addPin(pinEntity);

            this.checkThreeInARow(clientModel);
            this.nextClientModel();

            this.observer.writeObject(this.clientModels, this.getGameEntity());

            if (this.getNotFinishedClients() == 1)
                this.nextRound(false);
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

        if (this.clientModels.size() >= Config.GAME_MAX_PLAYERS)
            this.gameEntity.isRunning(true);

        this.startGame();

        this.observer.writeObject(this.clientModels, "<" + clientModel.getName() + "> Joined the game!");
        this.observer.writeObject(this.clientModels, this.getGameEntity());
    }

    public void removeClients() {

        for (ClientModel clientModel : this.clientModels) {

            clientModel.setGameModel(null);
            clientModel.setPinModels(new ArrayList<>());
            clientModel.isFinished(false);
            clientModel.isPlaying(true);
        }

        this.clientModels = new ArrayList<>();
    }

    public void removeClient(ClientModel clientModel) {

        this.observer.writeObject(this.getClientModels(), clientModel.getColor() + " LEFT THE GAME!");

        if (this.gameEntity.isRunning())
            this.nextRound(true);

        this.clientModels.remove(clientModel);
        this.clientModelsOrder.remove(clientModel);
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

    private GameEntity getGameEntity() {

        this.gameEntity.setClientEntitiesOrder(this.clientModelsOrder);
        this.gameEntity.setClientEntities(this.clientModels);
        this.gameEntity.setPinEntities(this.pinModels);
        this.gameEntity.setKey(this.key);

        return this.gameEntity;
    }

    private ArrayList<ClientModel> getClientModelsOrder() {

        for (ClientModel clientModel : this.clientModels)
            if (!clientModel.isPlaying())
                this.clientModelsOrder.add(clientModel);

        return this.clientModelsOrder;
    }
}
