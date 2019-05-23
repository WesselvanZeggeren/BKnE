package server.model;

import both.Config;
import server.controller.ServerApplication;

import java.util.ArrayList;

public class Game implements Runnable {

   // attributes
   private ServerApplication serverApplication;
   private ArrayList<Pin> pins;
   private ArrayList<Client> clients;

   private boolean isRunning = false;

   // startup
   public Game(ServerApplication serverApplication) {

       this.serverApplication = serverApplication;
       this.clients = new ArrayList<>();
       this.pins = new ArrayList<>();
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

       this.serverApplication.sendToClients(this.clients, message);
   }

   // setters
   public void addClient(Client client) {

       client.setGame(this);

       this.clients.add(client);

       this.sendToAllClients(this.toString(client));
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

   // toString
   public String toString(Client client) {

       StringBuilder gameString = new StringBuilder();

       gameString.append("{\n");
       gameString.append("\t\"pins\": [\n\t\t")      .append(this.pinsToString())                         .append("\n\t],");
       gameString.append("\n\t\"clients\": [\n\t\t") .append(this.clientsToString())                      .append("\n\t],");
       gameString.append("\n\t\"trigger\": ")        .append(client.toString().replace(("\n"), ("\n\t"))) .append(",");
       gameString.append("\n\t\"clientAmount\": ")   .append(this.clients.size());
       gameString.append("\n}");

       return gameString.toString();
   }

   public String pinsToString() {

       StringBuilder pinsString = new StringBuilder();

       for (int i = 0; i < this.pins.size(); i++)
           pinsString.append(this.pins.get(i).toString()).append(i == (this.pins.size() - 1) ? "" : ",\n");

       return pinsString.toString().replace("\n", "\n\t\t");
   }

   public String clientsToString() {

       StringBuilder clientsString = new StringBuilder();

       for (int i = 0; i < this.clients.size(); i++)
           clientsString.append(this.clients.get(i).toString()).append(i == (this.clients.size() - 1) ? "" : ",\n");

       return clientsString.toString().replace("\n", "\n\t\t");
   }
}
