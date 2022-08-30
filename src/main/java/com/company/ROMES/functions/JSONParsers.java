package com.company.ROMES.functions;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JSONParsers {
	public static JSONObject parseStringtoJSONObject(String s) {
		JSONParser parser = new JSONParser();
		JSONObject ret = new JSONObject();
		try {
			ret= (JSONObject) parser.parse(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	public static JSONArray parseStingToJSONArray(String s) {
		JSONParser parser = new JSONParser();
		JSONArray ret = new JSONArray();
		try {
			ret = (JSONArray)parser.parse(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	public static JsonObject parseStringtoJsonObject(Object object) {
		Gson gson = new Gson();
		JsonObject ret = new JsonObject();
		try {
			ret= gson.fromJson(object.toString(), JsonObject.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	public static JsonArray parseStingToJsonArray(String s) {
		Gson gson = new Gson();
		JsonArray ret = new JsonArray();
		try {
			ret= gson.fromJson(s.toString(), JsonArray.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	public static JSONObject makeItem(int id,String display) {
		JSONObject o = new JSONObject();
		o.put("id", id);
		o.put("text", display);
		return o;
	}
	
}
