package com.example.user.loginregister;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;


public class ListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        ItemClass item1 = new ItemClass("Bananas", "Labai skanus ir geltonas", 0.4);
        ItemClass item2 = new ItemClass("Braske", "Mazyte ir raudona", 1.5);
        ItemClass item3 = new ItemClass("Kotletas", "Didelis ir pagamintas is mesos", 5.0);

        ArrayList<ItemClass> itemlist = new ArrayList<>();
        Collections.addAll(itemlist, item1,item2,item3);

         CustomAdapter customAdapter = new CustomAdapter(this, R.layout.custom_row, itemlist);
         ListView lvItems = (ListView) findViewById(R.id.lvItems);

        lvItems.setAdapter(customAdapter);
    }
}
