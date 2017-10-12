package com.example.user.loginregister;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Admin extends AppCompatActivity {

    private String TAG = ListViewActivity.class.getSimpleName();
    private ListView lv;
    private ArrayList<ItemClass> contactList;
    private Spinner spin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        contactList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.lvAdmin);
        new Admin.GetContacts().execute();

        spin = (Spinner) findViewById(R.id.spinnerFunction);

        List<String> spinitems = new ArrayList<String>();
        Collections.addAll(spinitems, "Add Item", "Delete Item", "Edit Item");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Admin.this, R.layout.support_simple_spinner_dropdown_item, spinitems);
        spin.setAdapter(adapter);
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(Admin.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

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
                    JSONArray contacts = jsonObj.getJSONArray("server_response");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        String item = c.getString("item");
                        String description = c.getString("description");
                        Double price = c.getDouble("price");

                        // tmp hash map for single contact
                        ItemClass contact = new ItemClass();

                        // adding each child node to HashMap key => value
                        contact.setItem(item);
                        contact.setDescription(description);
                        contact.setPrice(price);
                        // adding contact to contact list
                        contactList.add(contact);
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
            CustomAdapter adapter = new CustomAdapter(Admin.this, R.layout.custom_row, contactList);
            lv.setAdapter(adapter);
        }
    }

}