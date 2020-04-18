package com.billych.homecredit.network;

import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataService {
    @GET("home")
    Call<JsonElement> getAllData();
}
