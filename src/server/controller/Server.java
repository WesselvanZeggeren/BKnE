package server.controller;

import config.Config;
import server.controller.interfaces.ServerInterface;
import server.model.Client;
import server.model.Game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements ServerInterface {

    // attributes
    private ServerSocket serverSocket;

    private ArrayList<Client> clients;
    private ArrayList<Thread> threads;
    private ArrayList<Game> games;

    private boolean isRunning = true;

    // startup
    public Server() {

        this.clients = new ArrayList<>();
        this.threads = new ArrayList<>();
        this.games = new ArrayList<>();
    }

    public void startup() {

        try {

            this.serverSocket = new ServerSocket(Config.SERVER_PORT);

            Thread thread = new Thread(() -> {

                while (this.isRunning) {

                    this.receiveClient();
                    this.sleepThread(100);
                }
            });

            thread.start();

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

        this.threads.add(thread);
        this.games.add(game);
    }

    private void addAllToGame(ArrayList<Client> clients) {

        for (Client client : this.clients)
            this.addToGame(client);
    }

    private void addToGame(Client client) {

        for (Game game : this.games)
            if (!game.isRunning() && !client.isInGame())
                game.addClient(client);

        if (!client.isInGame()) {

            this.createGame();
            this.addToGame(client);
        }
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
    public void receiveData(String data, Client client) {

        client.writeUTF("server received from client" + client.getName() + ": " + data);
    }

    @Override
    public void sendToClients (ArrayList<Client> clients, String data) {

        for (Client client : clients)
            client.writeUTF(data);
    }
}
