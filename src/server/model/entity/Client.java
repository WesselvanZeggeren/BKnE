package server.model.entity;

import server.controller.ServerController;
import server.model.ServerConnection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client implements Runnable {

    private Socket socket;
    private ServerController server;
    private DataOutputStream out;
    private DataInputStream in;
    private String name;

    public Client ( Socket socket, ServerController server ) {

        this.socket = socket;
        this.server = server;
    }

    public void writeUTF ( String text ) {

        System.out.println("Got message for client");

        try {

            this.out.writeUTF(text);

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        try {

            this.in  = new DataInputStream( this.socket.getInputStream() );
            this.out = new DataOutputStream( this.socket.getOutputStream() );

            this.out.writeUTF("Avans ChatServer 1.2.3.4");

            this.getName();
            this.manageChat();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    private void getName() throws IOException {

        this.name = this.in.readUTF();

        System.out.println("#### " + this.name + " joined the chat!");

        this.server.sendToAllClients("#### " + this.name + " joined the chat!");
    }

    private void manageChat() throws IOException {

        String message = "";

        while (!message.equals("stop")) {

            message = this.in.readUTF();

            this.out.writeUTF(message);

            System.out.println("Client send: " + message);

            this.server.sendToAllClients("(" + this.name + "): " + message);
        }

        this.socket.close();
    }
}
