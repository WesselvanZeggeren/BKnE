package server.controller;

import both.Config;
import server.controller.interfaces.ServerInterface;
import server.entity.PinEntity;
import server.model.ClientModel;
import server.model.GameModel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerApplication implements ServerInterface {

    // attributes
    private ServerSocket serverSocket;

    private ArrayList<ClientModel> clientModels;
    private ArrayList<Thread> threads;
    private ArrayList<GameModel> gameModels;

    private boolean isRunning = true;

    // startup
    public ServerApplication() {

        this.clientModels = new ArrayList<>();
        this.threads = new ArrayList<>();
        this.gameModels = new ArrayList<>();
    }

    public void startup() {

        try {

            this.serverSocket = new ServerSocket(Config.SERVER_PORT);

            new Thread(() -> {

                while (this.isRunning) {

                    this.receiveClient();
                    this.sleepThread(100);
                }
            }).start();

            System.out.println("started up!");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // methods
    private void receiveClient() {

        try {

            Socket socket = this.serverSocket.accept();

            ClientModel clientModel = new ClientModel(socket, this);
            Thread thread = new Thread(clientModel);
            thread.start();

            this.clientModels.add(clientModel);
            this.threads.add(thread);

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    private void createGame() {

        GameModel gameModel = new GameModel(this);
        this.gameModels.add(gameModel);
    }

    private void sleepThread(int milliseconds) {

        try {

            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }

        Thread.yield();
    }

    // connection observer
    @Override
    public void receiveObject(ClientModel clientModel, Object object) {

        if (object instanceof String)
            this.writeObject(clientModel.getGameModel().getClientModels(), object);

        if (object instanceof PinEntity)
            clientModel.getGameModel().receivePin(clientModel, (PinEntity) object);
    }

    @Override
    public void writeObject(ArrayList<ClientModel> clientModels, Object object) {

        for (ClientModel clientModel : clientModels)
            clientModel.writeObject(object);
    }

    // game observer
    @Override
    public void removeClient(ClientModel clientModel) {

        clientModel.getGameModel().removeClient(clientModel);

        this.threads.remove(this.clientModels.indexOf(clientModel));
        this.clientModels.remove(clientModel);
    }

    @Override
    public void addToGame(ClientModel clientModel) {

        for (GameModel gameModel : this.gameModels)
            if (!gameModel.isRunning() && !clientModel.isInGame())
                gameModel.addClient(clientModel);

        if (!clientModel.isInGame()) {

            this.createGame();
            this.addToGame(clientModel);
        }
    }
}
