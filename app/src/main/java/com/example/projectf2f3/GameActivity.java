package com.example.projectf2f3;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AlertDialog;


import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectf2f3.Utilidades.Utilidades;


public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8;
    TextView headerText;

    MediaPlayer player;
    Animation play;
    Button buttonPlay;
    public boolean musicatorn = false ;

    int PLAYER_O = 0;
    int PLAYER_X = 1;

    String nom = "masass";
    int punts = 100;

    int activePlayer = PLAYER_O;

    ConexionSQLiteHelper conn;

    int[] filledPos = {-1, -1, -1, -1, -1, -1, -1, -1, -1};

    boolean isGameActive = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Variable MediaPlayer on guardem la musica
        player = MediaPlayer.create(getApplicationContext(), R.raw.musicadefons);

        // Variable bool que controla el torn de la musica
        // Iniciem amb false
        musicatorn = false;


        buttonPlay = findViewById(R.id.playmusic);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Si esta false será true i si está true será false així activem o
                // desactivem quan premem el botó

                if(musicatorn == false){
                    musicatorn = true;
                    // Cridem a la funció que retorna la musica passant-li el torn (bool)
                    MusicControl(musicatorn);

                }else {
                    musicatorn = false;
                    MusicControl(musicatorn);
                }





            }
        });


        play = AnimationUtils.loadAnimation(this,R.anim.play) ;

        headerText = findViewById(R.id.header_text);
        headerText.setText("O turn");



        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);

        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        // logic for button press

        if (!isGameActive)
            return;

        Button clickedBtn = findViewById(v.getId());
        int clickedTag = Integer.parseInt(v.getTag().toString());

        if (filledPos[clickedTag] != -1) {
            return;
        }

        filledPos[clickedTag] = activePlayer;

        if (activePlayer == PLAYER_O) {
            clickedBtn.setText("O");
            clickedBtn.setBackground(getDrawable(android.R.color.holo_blue_bright));
            activePlayer = PLAYER_X;
            headerText.setText("X turn");
        } else {
            clickedBtn.setText("X");
            clickedBtn.setBackground(getDrawable(android.R.color.holo_orange_light));
            activePlayer = PLAYER_O;
            headerText.setText("O turn");
        }

        checkForWin();

    }

    public void MusicControl(boolean musicatorn){
        if(musicatorn){
            player.start();
            buttonPlay.startAnimation(play);



        }else{
            player.pause();
            buttonPlay.clearAnimation();

        }

    }

    private void checkForWin() {
        //we will check who is winner and show
        int[][] winningPos = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

        for (int i = 0; i < 8; i++) {
            int val0 = winningPos[i][0];
            int val1 = winningPos[i][1];
            int val2 = winningPos[i][2];

            if (filledPos[val0] == filledPos[val1] && filledPos[val1] == filledPos[val2]) {
                if (filledPos[val0] != -1) {
                    //winner declare

                    isGameActive = false;


                    if (filledPos[val0] == PLAYER_O){
                        showDialog("O is winner");
                        }





                    else {
                        showDialog("X is winner");
                        }
                }
            }
        }


    }
    private void InsertPointsP1() {
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(Utilidades.CAMPO_USUARIO,nom);
        values.put(Utilidades.CAMPO_PLAYER1,punts);
        values.put(Utilidades.CAMPO_PLAYER2,punts);


        db.insert(Utilidades.TABLA_PUNTUACIO, null, values);


        Toast.makeText(getApplicationContext(),"Ya se actualizó la puntuación",Toast.LENGTH_LONG).show();

        db.close();

    }

    private void showDialog(String winnerText) {
        new AlertDialog.Builder(this)
                .setTitle(winnerText)
                .setPositiveButton("Restart game", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        restartGame();
                    }
                })
                .show();
    }

    private void restartGame() {
        activePlayer = PLAYER_O;
        headerText.setText("O turn");
        filledPos = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1};
        btn0.setText("");
        btn1.setText("");
        btn2.setText("");
        btn3.setText("");
        btn4.setText("");
        btn5.setText("");
        btn6.setText("");
        btn7.setText("");
        btn8.setText("");

        btn0.setBackground(getDrawable(android.R.color.darker_gray));
        btn1.setBackground(getDrawable(android.R.color.darker_gray));
        btn2.setBackground(getDrawable(android.R.color.darker_gray));
        btn3.setBackground(getDrawable(android.R.color.darker_gray));
        btn4.setBackground(getDrawable(android.R.color.darker_gray));
        btn5.setBackground(getDrawable(android.R.color.darker_gray));
        btn6.setBackground(getDrawable(android.R.color.darker_gray));
        btn7.setBackground(getDrawable(android.R.color.darker_gray));
        btn8.setBackground(getDrawable(android.R.color.darker_gray));
        isGameActive = true;
    }


}