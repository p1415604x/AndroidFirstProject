package com.example.user.loginregister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserAreaActivity extends AppCompatActivity {
    private TextView introduction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        introduction = (TextView) findViewById(R.id.tvIntroduction);
        final TextView welcomeMessage = (TextView) findViewById(R.id.tvGreeting);
        final Button bList = (Button) findViewById(R.id.bList);
        final Button bContact = (Button) findViewById(R.id.bContact);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");




        addIntroductionText();
        String message = name +  "\nWelcome visiting Fresh Products!";
        welcomeMessage.setText(message);


        bList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bListIntent = new Intent(UserAreaActivity.this, ListViewActivity.class);
                UserAreaActivity.this.startActivity(bListIntent);
            }
        });

        bContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent bContactIntent = new Intent(UserAreaActivity.this, ContactActivity.class);
                UserAreaActivity.this.startActivity(bContactIntent);
            }
        });
    }


    private void addIntroductionText() {
        introduction.setText("Tekstas (angl. text, pranc. texte) – duomenys, pateikiami ženklais, simboliais, žodžiais, žodžių junginiais, sakiniais, pastraipomis, lentelėmis, išnašomis ar kitais ženklų junginiais, parengti siekiant perteikti prasmę ir darant prielaidą, jog jie suvokiami skaitytojų, mokančių tam tikrą natūraliąją arba dirbtinę kalbą.[1]\n" +
                "Pvz: Dalykinis laiškas, parašytas ar išspausdintas popieriuje arba rodomas displėjaus ekrane.\n" +
                "\n" +
                "Taip pat lietuvių kalboje tekstu yra vadinama:\n" +
                "bet koks rišlus kalbos darinys, Pvz., diktanto, apsakymo tekstas.\n" +
                "knygos, kūrinio žodinė dalis, skiriant ją nuo piešinių, brėžinių ir kt.\n" +
                "muzikos kūrinio žodžiai.\n");
    }

}
