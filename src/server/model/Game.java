package server.model;

import config.Config;

import java.util.ArrayList;

public class Game implements Runnable {

   // attributes
   private ArrayList<Client> clients;

   private boolean isRunning = false;

   // startup
   public Game() {

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

   public ArrayList<Client> removeClients() {

      for (Client client : this.clients) {

         client.isInGame(false);
      }

      ArrayList<Client> removedClients = new ArrayList<>(this.clients);

      this.clients = new ArrayList<>();

      return removedClients;
   }

   // setters
   public void addClient(Client client) {

      client.isInGame(true);

      this.clients.add(client);
   }

   // getters
   public ArrayList<Client> getClients() {

      return this.clients;
   }
}
