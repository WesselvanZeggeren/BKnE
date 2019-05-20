package server.model;

import server.controller.ServerController;
import server.model.entity.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerConnection {

    private ServerSocket socket;
    private ServerController server;

    private boolean isRunning;
    private int port;

    public ServerConnection (ServerController server, int port) {

        this.server = server;
        this.isRunning = true;
        this.port = port;
    }

    public boolean start(ArrayList<Client> clients, ArrayList<Thread> threads) {

        try {

            this.socket = new ServerSocket(port);

            Thread thread = new Thread(() -> {

                while (this.isRunning) {

                    this.findClient(clients, threads);
                    this.sleepThread();
                }
            });

            thread.start();

            System.out.println("Server is started and listening on port " + this.port);

        } catch (IOException e) {

            System.out.println("Could not connect: " + e.getMessage());

            return false;
        }

        return true;
    }

    private void findClient(ArrayList<Client> clients, ArrayList<Thread> threads) {

        System.out.println("Waiting for clients to connect.");

        try {

            Socket socket = this.socket.accept();

            System.out.println("Client connected from " + socket.getInetAddress().getHostAddress() + ".");

            Client client = new Client(socket, this.server);
            Thread threadClient = new Thread(client);
            threadClient.start();
            clients.add(client);
            threads.add(threadClient);

            System.out.println("Total clients connected: " + clients.size());

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    private void sleepThread() {

        try {

            Thread.sleep(100);

        } catch (InterruptedException e) {

            e.printStackTrace();
        }

        Thread.yield();
    }
}
