package client.model;

import both.JSONModel;
import client.controller.interfaces.ClientInterface;
import both.Config;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerConnection {

    private ClientInterface observer;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private String host;

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
            this.in = new DataInputStream(this.socket.getInputStream());
            this.out = new DataOutputStream(this.socket.getOutputStream());
            this.isRunning = true;

            this.manageInput();

        } catch (IOException e) {

            return false;
        }

        return true;
    }

    private void manageInput() {

        new Thread(() -> {

            while (this.isRunning) {

                try {

                    this.observer.receiveJSON(JSONModel.parseJSONObject(this.in.readUTF()));

                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void writeUTF(String data) {

        try {

            this.out.writeUTF(data);

        } catch (IOException e) {

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
