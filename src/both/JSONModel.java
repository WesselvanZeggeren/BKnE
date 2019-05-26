package both;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class JSONModel {

    public static JSONObject parseJSONObject(String jsonString) {

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
