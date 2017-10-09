package com.example.user.loginregister;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        String[] Items =  {
                "Cape Gooseberry",
                "Capuli cherry"
        };
         ListAdapter customAdapter = new CustomAdapter(this, Items);
         ListView lvItems = (ListView) findViewById(R.id.lvItems);
         List<String> Items_List = new ArrayList<String>(Arrays.asList(Items));

        lvItems.setAdapter(customAdapter);
    }
}
