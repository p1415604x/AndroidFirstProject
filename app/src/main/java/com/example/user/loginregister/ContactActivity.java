package com.example.user.loginregister;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ContactActivity extends AppCompatActivity {
    private TextView tvContactMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        final Button bCall = (Button) findViewById(R.id.bCall);
        final Button bText = (Button) findViewById(R.id.bTextUs);
        final Button bEmail = (Button) findViewById(R.id.bEmail);
        tvContactMessage = (TextView) findViewById(R.id.tvContactMessage);
        AddContactText();


        bEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });

        bCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0123456789"));
                ContactActivity.this.startActivity(intent);
            }
        });

        bText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMS(view);
            }
        });
    }

    public void sendSMS(View v)
    {
        Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address","0123456789");
        smsIntent.putExtra("sms_body","Hello,\nCan you please Text/Call me back?\n" +
                "I would like to know: \n\n Thank you for your Time!");
        startActivity(smsIntent);
    }

    public void AddContactText() {
        tvContactMessage.setText("Thank you for using our application. Please contact us, if you" +
                " are not sure about the details provided or wish to have business with us!");
    }

    protected void sendEmail() {
        Log.i("Send email", "");

        String[] TO = {"darkgienius@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email sent by Fresh Product App");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ContactActivity.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
