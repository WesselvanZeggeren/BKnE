package server.model;

import both.Config;
import both.Texture;
import server.controller.ServerApplication;
import server.entity.GameEntity;
import server.entity.PinEntity;

import java.util.ArrayList;

public class GameModel implements Runnable {

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

        this.pinModels = new ArrayList<>();
        this.clientModels = new ArrayList<>();
        this.clientModelsOrder = new ArrayList<>();

        this.key = 0;
    }

    // methods
    @Override
    public void run() {

        if (this.gameEntity.isRunning()) {

            this.gameEntity.setSize(0, this.getPlayingClients());

            for (int x = 0; x < this.gameEntity.getSize(); x++)
                for (int y = 0; y < this.gameEntity.getSize(); y++)
                    this.pinModels.add(new PinModel(x, y));
        }
    }

    private void restartGame() {

        if (this.getPlayingClients() != 1) {

            this.getCurrentClient().isPlaying(false);
            this.clientModelsOrder.add(this.getCurrentClient());

            for (ClientModel clientModel : this.clientModelsOrder) {

                clientModel.isFinished(false);
            }

            this.clientModels = this.clientModelsOrder;
            this.clientModelsOrder = new ArrayList<>();

            this.run();
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
        }
    }

    private void nextClientModel() {

        this.key++;

        if (this.key == this.clientModels.size())
            this.key = 0;

        if (this.getCurrentClient().isFinished() || !this.getCurrentClient().isPlaying())
            this.nextClientModel();

        if (this.getNotFinishedClients() == 1)
            this.restartGame();
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

        this.run();

        this.observer.writeObject(this.clientModels, "<" + clientModel.getName() + "> Joined the game!");
        this.observer.writeObject(this.clientModels, this.getGameEntity());
    }

    public void removeClients() {

        for (ClientModel clientModel : this.clientModels) {

            clientModel.cleanClient();
        }

        this.clientModels = new ArrayList<>();
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

        this.gameEntity.setClientEntities(this.clientModels);
        this.gameEntity.setPinEntities(this.pinModels);

        return this.gameEntity;
    }
}
