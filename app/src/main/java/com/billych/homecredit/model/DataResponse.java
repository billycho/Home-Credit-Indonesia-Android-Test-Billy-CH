package com.billych.homecredit.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

import java.util.List;

public class DataResponse {
    @SerializedName("data")
    private JSONArray jsonArray;
}
