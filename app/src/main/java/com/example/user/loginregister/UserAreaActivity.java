package com.example.user.loginregister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etAge = (EditText) findViewById(R.id.etAge);
        final TextView welcomeMessage = (TextView) findViewById(R.id.tvGreeting);
        final Button bList = (Button) findViewById(R.id.bList);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String username = intent.getStringExtra("username");
        int age = intent.getIntExtra("age", -1);

        String message = name +  "\nWelcome to User Area!";
        welcomeMessage.setText(message);

        etUsername.setText(username);
        etAge.setText(age + "");

        bList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bListIntent = new Intent(UserAreaActivity.this, ListViewActivity.class);
                UserAreaActivity.this.startActivity(bListIntent);
            }
        });





    }
}
