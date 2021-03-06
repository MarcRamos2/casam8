package com.example.projectf2f3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

// Aquesta Activity basicament el que fa es obrir altres activty
// depenent del botó que premem
public class MainActivity extends AppCompatActivity {

    Button btna, btnb,btnc,btnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        btna = (Button) findViewById(R.id.menu);
        btnb = (Button) findViewById(R.id.order);
        btnc = (Button) findViewById(R.id.play);
        btnd = (Button) findViewById(R.id.sortir);

        String userna = getIntent().getStringExtra("nameuserB");


        // MENU
        btna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MenuRecycleViewActivity.class);
                startActivity(intent);
            }
        });

        // ORDER(Comanda)
        btnb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),OrderActivity.class);
                startActivity(intent);
            }
        });

        // GAME
        btnc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),GameActivity.class);
                intent.putExtra("nameuserC",userna); // enviem el user a game activity per fe una consulta
                startActivity(intent);
            }
        });

        // SORTIR
        btnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}