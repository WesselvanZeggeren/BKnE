package both;

import client.model.GameData;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import server.model.Client;
import server.model.ClientData;

public class JSONModel {

    public static GameData convertServerJSON(String jsonString) {

        JSONObject json = parseJSON(jsonString);

        return new GameData();
    }

    public static ClientData convertClientJSON(String jsonString, Client client) {

        System.out.println("received: " + client);

        JSONObject json = parseJSON(jsonString);

        ClientData clientData = new ClientData(client);
        clientData.setMessage((String) json.get("message"));
        clientData.setX(Math.toIntExact((long) json.get("x")));
        clientData.setY(Math.toIntExact((long) json.get("y")));

        return clientData;
    }

    public static String convertClientName(String jsonString) {

        return (String) parseJSON(jsonString).get("name");
    }

    //
    private static JSONObject parseJSON(String jsonString) {

        JSONObject json = null;
        JSONParser parser = new JSONParser();

        try {

            json = (JSONObject) parser.parse(jsonString);

        } catch (ParseException e) {

            e.printStackTrace();
        }

        return json;
    }
}
