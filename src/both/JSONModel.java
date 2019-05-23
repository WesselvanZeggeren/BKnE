package both;

import client.model.GameData;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import server.model.ClientData;

public class JSONModel {

    public static GameData convertServerJSON(String jsonString) {

        JSONObject json = parseJSON(jsonString);

        return new GameData();
    }

    public static ClientData convertClientJSON(String jsonString) {

        JSONObject json = parseJSON(jsonString);

        return new ClientData();
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
