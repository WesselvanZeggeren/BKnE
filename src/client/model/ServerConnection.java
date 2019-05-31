package client.model;

import client.controller.interfaces.ClientInterface;
import both.Config;

import java.io.*;
import java.net.Socket;

public class ServerConnection {

    private ClientInterface observer;
    private Socket socket;
    private String host;

    private ObjectInputStream in;
    private ObjectOutputStream out;

    private boolean isRunning;
    private int port;

    public ServerConnection(ClientInterface observer) {

        this.observer = observer;
        this.host = Config.SERVER_HOST;
        this.port = Config.SERVER_PORT;
    }

    public boolean connect () {

        try {

            this.socket = new Socket(this.host, this.port);

            this.isRunning = true;

            this.out = new ObjectOutputStream(this.socket.getOutputStream());
            this.in = new ObjectInputStream(this.socket.getInputStream());

            this.manageObjectInput();

        } catch (IOException e) {

            e.printStackTrace();

            return false;
        }

        return true;
    }

    private void manageObjectInput() {

        new Thread(() -> { while (this.isRunning) {

            try {

                this.observer.receiveObject(this.in.readObject());

            } catch (Exception e) {

                e.printStackTrace();
            }
        }}).start();
    }

    public void writeObject(Object object) {

        try {

            this.out.writeObject(object);
            this.out.reset();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void stop() {

        try {

            this.isRunning = false;
            this.socket.close();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
