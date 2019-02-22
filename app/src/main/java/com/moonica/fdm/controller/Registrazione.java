package com.moonica.fdm.controller;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.moonica.fdm.R;
import com.moonica.fdm.model.FactoryUtente;
import com.moonica.fdm.model.Studente;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.moonica.fdm.controller.NewThread.hideKeyboard;

public class Registrazione extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText nome, cognome, username, password, mail;
    Spinner gender;
    TextInputLayout rNome, rCognome, rUser, rPass, rMail;
    Dialog dialog;

    public static final String USER = "com.moonica.fdm";
    public static final String FILE = "com.moonica.fdm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Spinner spinner = findViewById(R.id.gender);
        setTitle("Registrazione");

        spinner.setOnItemSelectedListener(this);
        dialog = new Dialog(this);

        //lista di stringhe da aggiungere allo spinner
        List<String> genders = new ArrayList<String>();
        genders.add("Sesso");
        genders.add("Maschio");
        genders.add("Femmina");
        genders.add("Altro");

        //adattatore dello spinner con la lista passata e lo stile scelto
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, genders);
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown);//stile dropdown list
        spinner.setAdapter(dataAdapter);//aggiunta dell'adapter allo spinner

        Intent registrazione = getIntent();
        Button b = findViewById(R.id.continua);

        //dichiarazione di cosa accade premendo il pulsante
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trovaView();//ricerca dei vari campi da compilare

                //se i campi non sono buoni passo i dati all'activiy successiva
                if(check()) {
                    Intent continua = new Intent(Registrazione.this, SceltaFacolta.class);
                    Studente s = creaUtente();
                    FactoryUtente factoryUtente = FactoryUtente.getInstance();
                    factoryUtente.aggiungiUtente(s);
                    continua.putExtra(USER, s);
                    startActivity(continua);
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //creazione utente coi dati inseriti
    public Studente creaUtente(){
        Studente nuovoS = new Studente();
        nuovoS.setNome(nome.getText().toString());
        nuovoS.setCognome(cognome.getText().toString());
        nuovoS.setUsername(username.getText().toString());
        nuovoS.setPassword(password.getText().toString());
        nuovoS.setEmail(mail.getText().toString());
        nuovoS.setSesso(gender.getSelectedItem().toString());
        return nuovoS;
    }

    //funzione per cercare le view
    public void trovaView(){
        nome = findViewById(R.id.nome);
        cognome = findViewById(R.id.cognome);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        mail = findViewById(R.id.mail);
        gender = findViewById(R.id.gender);

        rNome = findViewById(R.id.rNome);
        rCognome = findViewById(R.id.rCognome);
        rUser = findViewById(R.id.rUser);
        rMail = findViewById(R.id.rMail);
        rPass = findViewById(R.id.rPass);
    }

    //funzione per controllare che i campi non siano vuoti
    public boolean check(){
        Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.shake_error);
        int errors = 0;
        if(username.getText() == null || username.getText().length() == 0){
            username.setError("Inserire username");
            rUser.startAnimation(animation);
            errors++;
        }
        else  password.setError(null);
        if(password.getText() == null || password.getText().length() == 0){
            password.setError("Inserire password");
            rPass.startAnimation(animation);
            errors++;
        }
        else  password.setError(null);
        if(nome.getText() == null || nome.getText().length() == 0){
            nome.setError("Inserire nome");
            rNome.startAnimation(animation);
            errors++;
            hideKeyboard(this);
        }
        else  nome.setError(null);
        if(cognome.getText() == null || cognome.getText().length() == 0){
            cognome.setError("Inserire cognome");
            rCognome.startAnimation(animation);
            errors++;
            hideKeyboard(this);
        }
        else  cognome.setError(null);
        if(mail.getText() == null || mail.getText().length() == 0){
            mail.setError("Inserire mail");
            rMail.startAnimation(animation);
            errors++;
        }
        else if(!(Patterns.EMAIL_ADDRESS.matcher(mail.getText()).matches())){
            mail.setError("Inserire mail valida");
            rMail.startAnimation(animation);
            errors++;
        }
        else  mail.setError(null);
        if(gender.getSelectedItem().toString().equals("Sesso")) {
            SetError("Inserire sesso", gender, (TextView) findViewById(R.id.invisibleError));
            gender.startAnimation(animation);
            errors++;
            hideKeyboard(this);
        }

        if(errors == 0)
            return true;
        return false;
    }
    //funzione che aggiunge un messaggio di errore allo spinner
    public void SetError(String errorMessage, Spinner s, TextView textView){
        View view = s.getSelectedView();

        TextView t = (TextView) view;
        TextView error = (TextView) findViewById(R.id.invisibleError);
        if(errorMessage != null){
            t.setError(errorMessage);
            t.requestFocus();
        }
        else{
            t.setError(null);
            textView.setError(null);
        }
    }

    public void SceltaAvatar(View v){
        Intent intent = new Intent(Registrazione.this, CaricaFile.class);
        intent.putExtra(FILE, "registrazione");
        startActivityForResult(intent, 0);
    }
}
