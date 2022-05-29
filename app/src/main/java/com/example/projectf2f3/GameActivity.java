package com.example.projectf2f3;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AlertDialog;


import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
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
    TextView play1,play2;
    MediaPlayer player;
    Animation play;
    Button buttonPlay, restart, resetScore;

    public boolean musicatorn = false ;

    int PLAYER_O = 0;
    int PLAYER_X = 1;

    String nom = "Nom";
    int punts = 0;

    int activePlayer = PLAYER_O;

    ConexionSQLiteHelper conn;

    int[] filledPos = {-1, -1, -1, -1, -1, -1, -1, -1, -1};

    boolean isGameActive = true;
    boolean mboolean = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        play1= (TextView) findViewById(R.id.ply1);
        play2= (TextView) findViewById(R.id.ply2);

        conn = new ConexionSQLiteHelper(getApplicationContext(),"User_Database",null,1);

        // Variable MediaPlayer on guardem la musica
        player = MediaPlayer.create(getApplicationContext(), R.raw.musicadefons);

        // Variable bool que controla el torn de la musica
        // Iniciem amb false
        musicatorn = false;
        getScorePlayers();
        // Aixo fa que nomes faci un cop l'insert quan instale la aplicacio
        SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
        mboolean = settings.getBoolean("FIRST_RUN", false);
        if (!mboolean) {
            InsertPointsP1();
            settings = getSharedPreferences("PREFS_NAME", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("FIRST_RUN", true);
            editor.commit();
        }

        restart = (Button) findViewById(R.id.restart);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartGame();
            }
        });


        resetScore = (Button) findViewById(R.id.resetScore);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartGame();
            }
        });

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

                        actualizarPointsPlayer1();
                        getScorePlayers();

                    }

                    else {
                        showDialog("X is winner");

                        actualizarPointsPlayer2();
                        getScorePlayers();

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


        Toast.makeText(getApplicationContext(),"Ya se creo la puntuación",Toast.LENGTH_LONG).show();

        db.close();
    }

    private void actualizarPointsPlayer1() {

        SQLiteDatabase db = conn.getWritableDatabase();

        String x = "Nom";

        String value = play1.getText().toString();
        int scor = Integer.parseInt(value);

        int score1 = scor + 100;

        String[] parametros={x};

        ContentValues values=new ContentValues();
        values.put(Utilidades.CAMPO_PLAYER1,score1);

        db.update(Utilidades.TABLA_PUNTUACIO,values,Utilidades.CAMPO_USUARIO+"=?",parametros);

        Toast.makeText(getApplicationContext(),"Ya se actualizó el usuario",Toast.LENGTH_LONG).show();

        db.close();
    }

    private void actualizarPointsPlayer2() {

        SQLiteDatabase db = conn.getWritableDatabase();

        String x = "Nom";

        String value = play2.getText().toString();
        int scor = Integer.parseInt(value);

        int score2 = scor + 100;

        String[] parametros={x};

        ContentValues values=new ContentValues();
        values.put(Utilidades.CAMPO_PLAYER2,score2);

        db.update(Utilidades.TABLA_PUNTUACIO,values,Utilidades.CAMPO_USUARIO+"=?",parametros);

        Toast.makeText(getApplicationContext(),"Ya se actualizó el usuario",Toast.LENGTH_LONG).show();

        db.close();
    }

    private void resetScore() {

        SQLiteDatabase db = conn.getWritableDatabase();

        String x = "Nom";

        int score = 0;

        // restablir la puntuacio Payer1
        String[] parametros1={x};

        ContentValues values1 = new ContentValues();
        values1.put(Utilidades.CAMPO_PLAYER1,score);
        db.update(Utilidades.TABLA_PUNTUACIO,values1,Utilidades.CAMPO_USUARIO+"=?",parametros1);


        // restablir la puntuacio Player2
        String[] parametros2={x};
        
        ContentValues values2 = new ContentValues();
        values2.put(Utilidades.CAMPO_PLAYER2,score);
        db.update(Utilidades.TABLA_PUNTUACIO,values2,Utilidades.CAMPO_USUARIO+"=?",parametros2);

        Toast.makeText(getApplicationContext(),"Reset",Toast.LENGTH_LONG).show();

        db.close();
    }

    private void getScorePlayers() {

        SQLiteDatabase db=conn.getReadableDatabase();

        String x = "Nom";

        String[] parametros={x};

        try {
            Cursor cursor=db.rawQuery("SELECT " +Utilidades.CAMPO_PLAYER1+"  ,  "+Utilidades.CAMPO_PLAYER2+
                    " FROM "+Utilidades.TABLA_PUNTUACIO+" WHERE "+Utilidades.CAMPO_USUARIO+ "=? ",parametros);

            cursor.moveToFirst();
            play1.setText(cursor.getString(0));
            play2.setText(cursor.getString(1));

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"getscorePlayers Error",Toast.LENGTH_LONG).show();
        }
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