package client.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientConnection {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private Scanner scanner;

    private String host;
    private int port;

    public ClientConnection(String host, int port ) {

        this.host = host;
        this.port = port;
    }

    public boolean connect () {

        try {

            this.socket = new Socket(this.host, this.port);
            this.in = new DataInputStream( this.socket.getInputStream() );
            this.out = new DataOutputStream( this.socket.getOutputStream() );
            this.scanner = new Scanner( System.in );

            this.sendName();
            this.writeChat();
            this.manageChat();

        } catch (IOException e) {

            System.out.println("Could not connect with the server on " + this.host + " with port " + this.port + ": " + e.getMessage());

            return false;
        }

        return true;
    }

    private void sendName() throws IOException {

        String server = this.in.readUTF();

        System.out.println(server);
        System.out.print("What is your name: ");

        String name = this.scanner.nextLine();
        this.out.writeUTF(name);
    }

    private void writeChat() {

        new Thread ( () -> {

            while ( true ) {

                try {

                    System.out.println(this.in.readUTF());

                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void manageChat() throws IOException {

        String message = "";

        while (!message.equals("stop" )) {

            System.out.print("> ");

            message = scanner.nextLine();

            this.out.writeUTF(message);
        }

        this.socket.close();
    }
}
