package com.xzcode.socket.core.utils.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GGSocketJsonUtil {
	
	private static final Gson gson = new GsonBuilder().serializeNulls().create();
	
	public static String toJson(Object object) {
		return gson.toJson(object);
	}

}
