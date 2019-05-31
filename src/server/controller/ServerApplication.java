package server.controller;

import both.Config;
import server.controller.interfaces.ServerInterface;
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
        Thread thread = new Thread(gameModel);
        thread.start();

        this.threads.add(thread);
        this.gameModels.add(gameModel);
    }

    private void addAllToGame(ArrayList<ClientModel> clientModels) {

        for (ClientModel clientModel : this.clientModels)
            this.addToGame(clientModel);
    }

    private void sleepThread(int miliseconds) {

        try {

            Thread.sleep(miliseconds);
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
    }

    @Override
    public void writeObject(ArrayList<ClientModel> clientModels, Object object) {

        for (ClientModel clientModel : clientModels)
            clientModel.writeObject(object);
    }

    // game observer
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
