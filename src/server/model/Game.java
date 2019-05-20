package server.model;

import config.Config;
import server.controller.Server;

import java.util.ArrayList;

public class Game implements Runnable {

   // attributes
   private Server server;
   private ArrayList<Pin> pins;
   private ArrayList<Client> clients;

   private boolean isRunning = false;

   // startup
   public Game(Server server) {

      this.server = server;
      this.clients = new ArrayList<>();
   }

   // methods
   @Override
   public void run() {

      if (isRunning()) {

         this.isRunning = true;
      }
   }

   public boolean isRunning() {

      return (this.clients.size() == Config.GAME_MAX_PLAYERS || this.isRunning);
   }

   public void sendToAllClients(String message) {

      this.server.sendToClients(this.clients, message);
   }

   // setters
   public void addClient(Client client) {

      client.setGame(this);

      this.clients.add(client);
   }

   public void removeClients() {

      for (Client client : this.clients)
         client.setGame(null);

      this.clients = new ArrayList<>();
   }

   // getters
   public ArrayList<Client> getClients() {

      return this.clients;
   }
}
