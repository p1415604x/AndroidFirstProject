package com.example.user.loginregister;

import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

    private String TAG = ListViewActivity.class.getSimpleName(), ADMIN_REQUEST_URL;
    private ItemClass selectedItem;
    private ListView lv;
    private ArrayList<ItemClass> productList;
    private Spinner spin;
    private Button bExecute;
    private EditText etProductName, etDescription, etPrice;
    private String itemName, itemDesc;
    private Double itemPrice;
    private AdminRequest adminRequest;
    private Response.Listener<String> responseListener;
    private int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        etProductName = (EditText) findViewById(R.id.etProductName);
        etDescription = (EditText) findViewById(R.id.etDescription);
        etPrice = (EditText) findViewById(R.id.etPrice);

        //List View preparation and execution
        productList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.lvAdmin);

        //Listener to get information from selected item in list view
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                selectedItem = (ItemClass) lv.getItemAtPosition(position);   }
        });
        this.makeListViewScrollable();   //METHOD TO MAKE LV SCROLLABLE INSIDE SCROLLVIEW
        new Admin.GetProducts().execute(); //Executing List Population

        //Spinner creation and collecion addition
        spin = (Spinner) findViewById(R.id.spinnerFunction);
        List<String> spinitems = new ArrayList<>();
        Collections.addAll(spinitems, "Add Item", "Delete Item", "Edit Item");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Admin.this, R.layout.support_simple_spinner_dropdown_item, spinitems);
        spin.setAdapter(adapter);
        this.changeEditTextVisibility(); //spin SPINNER method

        //Button
        bExecute = (Button) findViewById(R.id.bExecute);
        bExecute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponset = new JSONObject(response);
                            boolean success = jsonResponset.getBoolean("success");
                            if(success) {
                                //REFRESH LIST
                                Toast.makeText(getApplicationContext(), "List Updated", Toast.LENGTH_SHORT).show();
                                productList.clear();
                                new Admin.GetProducts().execute();
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

                if(spin.getSelectedItemPosition()==0) {                 //Statement to add item
                    if(etProductName.getText().toString().equals("") || etDescription.getText().toString().equals("")
                            || etPrice.getText().toString().equals("")) {
                        errorMessage("Check if all fields have values!");
                    } else {
                        addItemRequest();
                        clearEditTexts();
                        addRequestToTheQueue();
                    }
                } else if (spin.getSelectedItemPosition()==1) {         //Statement to delete item
                    if(selectedItem !=null) {
                        ADMIN_REQUEST_URL = "https://darkgienius.000webhostapp.com/DeleteItem.php";
                        itemId = selectedItem.getId();
                        adminRequest = new AdminRequest(itemId, ADMIN_REQUEST_URL, responseListener);
                        addRequestToTheQueue();
                    } else {
                        errorMessage("Select item to delete!");
                    }
                } else {                                                //Statement to Edit item
                   if(selectedItem !=null) {
                       if (!etProductName.getText().toString().equals("") || !etDescription.getText().toString().equals("")
                               || !etPrice.getText().toString().equals("")) {
                           editItemRequest();
                           clearEditTexts();
                           addRequestToTheQueue();
                       } else {
                           errorMessage("At least one field has to have value!");
                       }
                   } else { errorMessage("Select item to edit!");}
                }

            }
        });
    }

    private void clearEditTexts() {
        etProductName.setText("");
        etDescription.setText("");
        etPrice.setText("");
    }

    private void errorMessage(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Admin.this);
        builder.setMessage(s)
                .setNegativeButton("Retry", null)
                .create()
                .show();
    }

    private void addRequestToTheQueue() {
        RequestQueue queue = Volley.newRequestQueue(Admin.this);
        queue.add(adminRequest);
    }

    private void addItemRequest() {
        ADMIN_REQUEST_URL = "https://darkgienius.000webhostapp.com/AddItem.php";
        itemName = etProductName.getText().toString();
        itemDesc = etDescription.getText().toString();
        itemPrice = Double.parseDouble(etPrice.getText().toString());
        adminRequest = new AdminRequest(itemName, itemDesc, itemPrice,
                ADMIN_REQUEST_URL, responseListener);
    }

    private void editItemRequest() {
        ADMIN_REQUEST_URL = "https://darkgienius.000webhostapp.com/EditItem.php";
        // GET ADMIN WANTED VALUES
        if(etProductName.getText().toString().equals("")) {itemName = selectedItem.getItem();}
        else{itemName = etProductName.getText().toString();}
        if(etDescription.getText().toString().equals("")) {itemDesc = selectedItem.getDescription();}
        else{itemDesc = etDescription.getText().toString();}
        if(etPrice.getText().toString().equals("")) {itemPrice = selectedItem.getPrice();}
        else{itemPrice = Double.parseDouble(etPrice.getText().toString());}
        //GET VALUES OF ITEM SELECTED (NEEDED TO IDENTIFY WHICH ITEM TO EDIT IN MYSQLI)
        itemId = selectedItem.getId();
        adminRequest = new AdminRequest(itemId, itemName, itemDesc, itemPrice,
                ADMIN_REQUEST_URL, responseListener);
    }

    private void makeListViewScrollable() {
        lv.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });
    }

    private void changeEditTextVisibility() { //HIDES EDITTEXTs IF NOT NEEDED
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (spin.getSelectedItemPosition() == 1) {
                    etProductName.setVisibility(View.GONE);
                    etDescription.setVisibility(View.GONE);
                    etPrice.setVisibility(View.GONE);
                } else {
                    etProductName.setVisibility(View.VISIBLE);
                    etDescription.setVisibility(View.VISIBLE);
                    etPrice.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                etProductName.setVisibility(View.GONE);
                etDescription.setVisibility(View.GONE);
                etPrice.setVisibility(View.GONE);
            }
        });
    }

    private class GetProducts extends AsyncTask<Void, Void, Void> {
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
                        int id = c.getInt("id");
                        String item = c.getString("item");
                        String description = c.getString("description");
                        Double price = c.getDouble("price");

                        // tmp hash map for single contact
                        ItemClass product = new ItemClass();

                        // adding each child node to HashMap key => value
                        product.setId(id);
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
                } } else {
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
            CustomAdapter adapter = new CustomAdapter(Admin.this, R.layout.custom_row, productList);
            lv.setAdapter(adapter);
        }
    }

}