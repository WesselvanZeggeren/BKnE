package server.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client implements Runnable {

    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private String name;
    private Game game;

    //
    public Client (Socket socket) {

        this.socket = socket;
    }

    // connection
    public void writeUTF (String text) {

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

            this.getName();
            this.manageChat();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    private void getName() throws IOException {

        this.name = this.in.readUTF();
        this.game.sendToAllClients("(Server): " + this.name + " joined the game!");
    }

    private void manageChat() throws IOException {

        String message = "";

        while (!message.equals("stop")) {

            message = this.in.readUTF();

            this.out.writeUTF(message);
            this.game.sendToAllClients("[" + this.name + "]: " + message);
        }

        this.socket.close();
    }

    // setters
    public void setGame(Game game) {

        this.game = game;
    }

    // getters
    public boolean isInGame() {

        return (this.game != null);
    }
}
