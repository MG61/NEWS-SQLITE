package com.example.news_sqlite;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news_sqlite.Auth.Auth;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivitySec extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseHelper databaseHelper;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainsec);

        recyclerView = findViewById(R.id.recycler_view);
        databaseHelper = new DatabaseHelper(getApplicationContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this, databaseHelper.getArray());
        Button exit = findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(new Intent(MainActivitySec.this, Auth.class));
                startActivity(i);
            }
        });
        recyclerView.setAdapter(adapter);
    }

}