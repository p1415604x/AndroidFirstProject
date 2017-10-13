package com.example.user.loginregister;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Admin extends AppCompatActivity {

    private String TAG = ListViewActivity.class.getSimpleName(), REGISTER_REQUEST_URL;
    private ItemClass selectedItem;
    private ListView lv;
    private ArrayList<ItemClass> contactList;
    private Spinner spin;
    private Button bExecute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //List View preparation and execution
        contactList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.lvAdmin);
        //Listener for Selected Item
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                selectedItem = (ItemClass) lv.getItemAtPosition(position);
            }
        });
        //Executing List Population
        new Admin.GetContacts().execute();

        //Spinner creation and collecion addition
        spin = (Spinner) findViewById(R.id.spinnerFunction);
        List<String> spinitems = new ArrayList<String>();
        Collections.addAll(spinitems, "Add Item", "Delete Item", "Edit Item");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Admin.this, R.layout.support_simple_spinner_dropdown_item, spinitems);
        spin.setAdapter(adapter);

        //Button
        bExecute = (Button) findViewById(R.id.bExecute);
        bExecute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName = selectedItem.getItem();
                String itemDesc = selectedItem.getDescription();
                Double itemPrice = selectedItem.getPrice();

                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponset = new JSONObject(response);
                            boolean success = jsonResponset.getBoolean("success");
                            if(success) {
                                //REFRESH LIST
                                new Admin.GetContacts().execute();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Admin.this);
                                builder.setMessage("Execution Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                Toast.makeText(getApplicationContext(),selectedItem.getItem(), Toast.LENGTH_LONG).show();
                if(spin.getSelectedItemPosition()==0) {
                    REGISTER_REQUEST_URL = "https://darkgienius.000webhostapp.com/AddItem.php";
                } else if (spin.getSelectedItemPosition()==1) {
                    REGISTER_REQUEST_URL = "https://darkgienius.000webhostapp.com/DeleteItem.php";
                } else {
                    REGISTER_REQUEST_URL = "https://darkgienius.000webhostapp.com/EditItem.php";
                }
                AdminRequest adminRequest = new AdminRequest(itemName, itemDesc, itemPrice, REGISTER_REQUEST_URL, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Admin.this);
                queue.add(adminRequest);



            }
        });
    }







    private class GetContacts extends AsyncTask<Void, Void, Void> {
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