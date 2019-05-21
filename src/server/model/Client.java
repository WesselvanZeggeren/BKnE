package server.model;

import server.controller.Server;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Client implements Runnable {

    private Game game;
    private Socket socket;
    private Server server;
    private ArrayList<Pin> pins;
    private DataInputStream in;
    private DataOutputStream out;

    private Color color;

    private String name;

    private boolean isRunning = true;

    // constructor
    public Client (Socket socket, Server server) {

        this.socket = socket;
        this.server = server;
        this.pins = new ArrayList<>();
    }

    // connection
    @Override
    public void run() {

        try {

            this.in  = new DataInputStream(this.socket.getInputStream());
            this.out = new DataOutputStream(this.socket.getOutputStream());

            this.manageInput();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    private void manageInput() {

        new Thread(() -> {

            while (this.isRunning) {

                try {

                    this.server.receiveData(this.in.readUTF(), this);
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        });
    }

    public void writeUTF (String text) {

        try {

            this.out.writeUTF(text);

        } catch (IOException
                e) {

            e.printStackTrace();
        }
    }

    // setters
    public void setGame(Game game) {

        this.game = game;
    }

    // getters
    public String getName() {

        return this.name;
    }

    public boolean isInGame() {

        return (this.game != null);
    }
}
