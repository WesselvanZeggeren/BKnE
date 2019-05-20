package server.model;

import server.model.Client;

import java.util.ArrayList;

public class Game {

   private ArrayList<Client> clients;

   public Game() {

      this.clients = new ArrayList<>();
   }

   // setters
   public void addClient(Client client) {

      this.clients.add(client);
   }

   // getters
   public ArrayList<Client> getClients() {

      return this.clients;
   }

   public int getClientsAmount() {

      return this.clients.size();
   }
}
