package server.model;

import both.JSONModel;
import server.controller.ServerApplication;
import server.controller.interfaces.ServerInterface;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Client implements Runnable {

    private Game game;
    private Socket socket;
    private ArrayList<Pin> pins;
    private DataInputStream in;
    private DataOutputStream out;
    private ServerInterface observer;

    private Color color;
    private String name = "";

    private boolean isRunning = true;

    // constructor
    public Client (Socket socket, ServerInterface observer) {

        this.socket = socket;
        this.observer = observer;
        this.color = new Color(0, 0, 0);
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

                    if (this.name.equals("")) {

                        this.name = JSONModel.convertClientName(this.in.readUTF());
                        this.observer.addToGame(this);
                    } else {


                        this.observer.receiveData(JSONModel.convertClientJSON(this.in.readUTF(), this));
                    }
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void writeUTF (String text) {

        try {

            this.out.writeUTF(text);

        } catch (IOException
                e) {

            e.printStackTrace();
        }
    }

    // methods
    public boolean containsPin(int x, int y) {

        for (Pin pin : this.pins)
            if (pin.getX() == x && pin.getY() == y)
                return true;

        return false;
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

    public Game getGame() {

        return this.game;
    }

    // to String
    @Override
    public String toString() {

        StringBuilder clientString = new StringBuilder();

        clientString.append("{");
        clientString.append("\n\t\"name\": \"")      .append(this.name)             .append("\",");
        clientString.append("\n\t\"color\": \"")     .append(this.color.toString()) .append("\",");
        clientString.append("\n\t\"pins\": [\n\t\t") .append(this.pinsToString())   .append("\n\t]");
        clientString.append("\n}");

        return clientString.toString();
    }

    public String pinsToString() {

        StringBuilder pinsString = new StringBuilder();

        for (int i = 0; i < this.pins.size(); i++)
            pinsString.append(this.pins.get(i).toString()).append(i == (this.pins.size() - 1) ? "" : ",\n");

        return pinsString.toString().replace("\n", "\n\t\t");
    }
}
