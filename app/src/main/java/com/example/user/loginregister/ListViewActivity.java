package com.example.user.loginregister;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class ListViewActivity extends AppCompatActivity {

    private String TAG = ListViewActivity.class.getSimpleName();
    private ListView lv;
    ArrayList<ItemClass> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        productList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.lvItems);

        new GetProducts().execute();
    }

    private class GetProducts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(ListViewActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "https://darkgienius.000webhostapp.com/ListViewItems.php";
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray products = jsonObj.getJSONArray("server_response");

                    // looping through All Contacts
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);
                        String item = c.getString("item");
                        String description = c.getString("description");
                        Double price = c.getDouble("price");

                        // tmp hash map for single contact
                        ItemClass product = new ItemClass();

                        // adding each child node to HashMap key => value
                        product.setItem(item);
                        product.setDescription(description);
                        product.setPrice(price);
                        // adding contact to contact list
                        productList.add(product);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            CustomAdapter adapter = new CustomAdapter(ListViewActivity.this, R.layout.custom_row, productList);
            lv.setAdapter(adapter);
        }
    }

}