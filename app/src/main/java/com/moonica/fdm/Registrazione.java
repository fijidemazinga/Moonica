package com.moonica.fdm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Registrazione extends AppCompatActivity {

    EditText nome, cognome, username, password, mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Studente nuovoS = new Studente();


        Intent registrazione = getIntent();
        Button b = findViewById(R.id.continua);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent continua = new Intent(Registrazione.this, SceltaFacolta.class);

            }
        });
    }
}
