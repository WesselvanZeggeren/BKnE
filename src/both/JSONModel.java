package both;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class JSONModel {

//    public static GameEntity convertServerJSON(String jsonString) {
//
//        JSONObject json = parseJSON(jsonString);
//
//        GameEntity gameEntity = new GameEntity();
//        gameEntity.setSize(Math.toIntExact((long) json.get("size")));
//
//
//
//        return new GameEntity();
//    }
//
//    private static ArrayList<PlayerEntity> convertPlayersJSON(JSONArray) {
//
//    }
//
//    public static ClientEntity convertClientJSON(String jsonString) {
//
//        System.out.println("received: " + client);
//
//        JSONObject json = parseJSON(jsonString);
//
//        ClientEntity clientEntity = new ClientEntity(client);
//        clientEntity.setMessage((String) json.get("message"));
//        clientEntity.setX(Math.toIntExact((long) json.get("x")));
//        clientEntity.setY(Math.toIntExact((long) json.get("y")));
//
//        return clientEntity;
//    }
//
//    public static String convertClientName(String jsonString) {
//
//        return (String) parseJSON(jsonString).get("name");
//    }

    public static JSONObject parseJSONOject(String jsonString) {

        JSONObject json = null;
        JSONParser parser = new JSONParser();

        try {

            json = (JSONObject) parser.parse(jsonString);

        } catch (ParseException e) {

            e.printStackTrace();
        }

        return json;
    }

    public static ArrayList<JSONObject> parseJSONArray(JSONArray jsonArray) {

        ArrayList<JSONObject> jsonObjects = new ArrayList<>();

        for (Object jsonObject : jsonArray)
            jsonObjects.add((JSONObject) jsonObject);

        return jsonObjects;
    }
}
