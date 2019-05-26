package server.controller;

import both.Config;
import org.json.simple.JSONObject;
import server.controller.interfaces.ServerInterface;
import server.model.Client;
import _old.entity.entity.ClientEntity;
import server.model.Game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerApplication implements ServerInterface {

    // attributes
    private ServerSocket serverSocket;

    private ArrayList<Client> clients;
    private ArrayList<Thread> threads;
    private ArrayList<Game> games;

    private boolean isRunning = true;

    // startup
    public ServerApplication() {

        this.clients = new ArrayList<>();
        this.threads = new ArrayList<>();
        this.games = new ArrayList<>();
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

            Client client = new Client(socket, this);
            Thread thread = new Thread(client);
            thread.start();

            this.clients.add(client);
            this.threads.add(thread);

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    private void createGame() {

        Game game = new Game(this);
        Thread thread = new Thread(game);
        thread.start();

        this.threads.add(thread);
        this.games.add(game);
    }

    private void addAllToGame(ArrayList<Client> clients) {

        for (Client client : this.clients)
            this.addToGame(client);
    }

    private void sleepThread(int miliseconds) {

        try {
            Thread.sleep(miliseconds);

        } catch (InterruptedException e) {

            e.printStackTrace();
        }

        Thread.yield();
    }

    // observer
    @Override
    public void receiveData(JSONObject json, Client client) {

        this.sendToClients(this.clients, (String) json.get("message"));
    }

    @Override
    public void sendToClients (ArrayList<Client> clients, String data) {

        for (Client client : clients)
            client.writeUTF(data);
    }

    @Override
    public void addToGame(Client client) {

        for (Game game : this.games)
            if (!game.isRunning() && !client.isInGame())
                game.addClient(client);

        if (!client.isInGame()) {

            this.createGame();
            this.addToGame(client);
        }
    }
}
