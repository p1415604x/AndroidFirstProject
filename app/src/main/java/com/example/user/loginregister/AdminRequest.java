package com.example.user.loginregister;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Laurynas Mykolaitis on 2017.10.04.
 */

public class AdminRequest extends StringRequest {

    private Map<String, String> params;
    public AdminRequest(int itemId, String REGISTER_REQUEST_URL, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("id", itemId + "");
    }

    public AdminRequest(String itemName, String itemDesc, Double itemPrice, String REGISTER_REQUEST_URL, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("item", itemName);
        params.put("description", itemDesc);
        params.put("price", itemPrice + "");
    }

    public AdminRequest(int itemId, String itemName, String itemDesc, Double itemPrice, String REGISTER_REQUEST_URL, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("id", itemId + "");
        params.put("item", itemName);
        params.put("description", itemDesc);
        params.put("price", itemPrice + "");
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
