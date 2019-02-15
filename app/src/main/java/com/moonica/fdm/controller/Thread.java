package com.moonica.fdm.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.moonica.fdm.R;
import com.moonica.fdm.model.Commento;
import com.moonica.fdm.model.FactoryCommenti;
import com.moonica.fdm.model.FactoryUtente;
import com.moonica.fdm.model.ForumThread;
import com.moonica.fdm.model.ForumRVAdapter;
import com.moonica.fdm.model.ThreadRVAdapter;

import org.w3c.dom.Comment;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;


public class Thread extends AppCompatActivity {

    CardView cv;
    ForumThread ft;
    TextView titolo, testo, data, autore;
    FloatingActionButton fab;

    RecyclerView rv;

    ArrayList<Commento> commentsList = new ArrayList<>();
    FactoryCommenti fc = FactoryCommenti.getInstance();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        Intent i = getIntent();
        Serializable obj = i.getSerializableExtra("com.moonica.fdm");

        ft = (ForumThread) obj;

        setTitle(ft.getCorso().getNome() + " / Thread");

        commentsList = fc.cercaListaCommenti(ft.getId());

        /*
         * I dati del post principale del thread vengono caricati
         */

        cv = findViewById(R.id.cardView_main_post);
        titolo = findViewById(R.id.titoloThread_main_post);
        testo = findViewById(R.id.testoThread_main_post);
        data = findViewById(R.id.data_thread);
        autore = findViewById(R.id.autore_thread);
        fab = findViewById(R.id.fab_thread);

        titolo.setText(ft.getTitolo());
        testo.setText(ft.getTesto());
        data.setText(ft.getData().getTime().toGMTString());
        autore.setText(ft.getAutore().getNome() + " " + ft.getAutore().getCognome());

        rv = (RecyclerView) findViewById(R.id.rv_thread);

        rv.setFocusable(false);


        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initializeAdapter();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReply(Thread.this);

            }
        });



        /*cv.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

            }
        });*/

    }

    private void initializeAdapter() {
        ThreadRVAdapter adapter = new ThreadRVAdapter(commentsList);
        rv.setAdapter(adapter);

    }

    private void addReply(Context c) {

        final EditText reply = new EditText(c);

        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Risposta")
                .setMessage("Aggiungi una risposta")
                .setView(reply)
                .setPositiveButton("Rispondi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = String.valueOf(reply.getText());

                        Commento newReply = new Commento();

                        FactoryUtente factoryUtente = FactoryUtente.getInstance();

                        newReply.setFt(ft.getId());
                        newReply.setTesto(task);
                        newReply.setData(Calendar.getInstance());
                        newReply.setAutore(factoryUtente.cercaUtente("Ines"));

                        commentsList.add(newReply);

                    }
                })
                .setNegativeButton("Annulla", null)
                .create();
        dialog.show();

    }
}


