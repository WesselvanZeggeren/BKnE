package server.model;

import both.Config;
import javafx.animation.AnimationTimer;
import server.controller.ServerApplication;

import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Runnable, Serializable {

    // attributes
    private ServerApplication observer;
    private ArrayList<Client> clientsOrder;
    private ArrayList<Client> clients;
    private ArrayList<Pin> pins;
    private int size;

    private boolean isRunning = false;

    // startup
    public Game(ServerApplication observer) {

        this.observer = observer;
        this.clientsOrder = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.pins = new ArrayList<>();
    }

    // methods
    @Override
    public void run() {

        if (this.isRunning) {

            this.size = this.getSize(0);

            for (int x = 0; x < this.size; x++)
                for (int y = 0; y < this.size; y++)
                    this.pins.add(new Pin(x, y));

            this.startGame();
        }
    }

    private void startGame() {

    }

    public boolean isFreePin(int x, int y) {

        for (Client client : this.clients)
            if (client.containsPin(x, y))
                return false;

        return true;
    }

    // setters
    public void addClient(Client client) {

        client.setGame(this);

        this.clients.add(client);

        this.observer.writeObject(this.clients, "<" + client.getName() + "> Joined the game!");

        if (this.clients.size() > Config.GAME_MAX_PLAYERS)
            this.isRunning = true;
    }

    public void removeClients() {

        for (Client client : this.clients) {

            client.setGame(null);
            client.setPins(new ArrayList<>());
        }

        this.clients = new ArrayList<>();
    }

    // getters
    public ArrayList<Client> getClients() {

        return this.clients;
    }

    public int getSize() {

        return this.size;
    }

    private int getSize(int size) {

        if ((size * size) > (this.clients.size() * Config.GAME_PIN_PER_CLIENT))
            return size;

        return this.getSize(++size);
    }

    public boolean isRunning() {

        return this.isRunning;
    }
}
