package server.model;

import both.Config;
import server.controller.ServerApplication;

import java.util.ArrayList;

public class Game implements Runnable {

   // attributes
   private ServerApplication serverApplication;
   private ArrayList<Pin> pins;
   private ArrayList<Client> clients;
   private ArrayList<Client> clientsOrder;

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

           if (!this.isRunning) {

               this.isRunning = true;

               int size = this.getSize(0);

               for (int x = 0; x < size; x++)
                   for (int y = 0; y < size; y++)
                       this.pins.add(new Pin(x, y));
           }
       }
   }

   public boolean isRunning() {

       return (this.clients.size() == Config.GAME_MAX_PLAYERS || this.isRunning);
   }

   public void sendToAllClients(String message) {

       this.serverApplication.sendToClients(this.clients, message);
   }

   public boolean isFreePin(int x, int y) {

       for (Client client : this.clients)
           if (client.containsPin(x, y))
               return false;

       return true;
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

   private int getSize() {

       return this.getSize(0);
   }

   private int getSize(int size) {

       if ((size * size) > (this.clients.size() * Config.GAME_PIN_PER_CLIENT))
           return size;

       return this.getSize(++size);
   }

   // toString
   public String toString(Client client) {

       StringBuilder gameString = new StringBuilder();

       gameString.append("{\n\t");
       gameString.append("\"size\": ")           .append(this.getSize())                              .append(",\n\t");
       gameString.append("\"pins\": [\n\t\t")    .append(this.pinsToString())                         .append("\n\t],\n\t");
       gameString.append("\"clients\": [\n\t\t") .append(this.clientsToString())                      .append("\n\t],\n\t");
       gameString.append("\"trigger\": ")        .append(client.toString().replace(("\n"), ("\n\t"))) .append(",\n\t");
       gameString.append("\"clientAmount\": ")   .append(this.clients.size())                         .append("\n");
       gameString.append("}");

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
