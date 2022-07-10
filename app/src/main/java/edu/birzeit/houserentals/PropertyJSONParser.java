package edu.birzeit.houserentals;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PropertyJSONParser {
    public static List<Property> getObjectFromJason(String jason) {
        List<Property> properties;
        try {
            JSONArray jsonArray = new JSONArray(jason);
            properties = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject = (JSONObject) jsonArray.get(i);

                Property property = new Property(jsonObject.getString("city"), jsonObject.getString("postalAddress"),jsonObject.getLong("surfaceArea"),
                        jsonObject.getInt("numberOfBedrooms"), jsonObject.getLong("rentalPrice"),
                        jsonObject.getString("status"), jsonObject.getString("balcony"),
                        jsonObject.getString("garden"));

                properties.add(property);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return properties;
    }
}
