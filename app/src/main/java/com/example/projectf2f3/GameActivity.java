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

// Un simple Joc Tic Tac Toe
public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8;
    Button buttonPlay, restart, resetScore;
    TextView headerText,play1,play2;
    MediaPlayer player;
    Animation play;

    public boolean musicatorn = false ;
    boolean isGameActive = true;
    boolean mboolean = false;

    int[] filledPos = {-1, -1, -1, -1, -1, -1, -1, -1, -1}; // en principi totes les posicions no estan clicats iaxo cambiara per 0 o 1 amb l'ajuda del tag

    // aixo fem per saber quin jugador esta jugant
    int PLAYER_O = 0;
    int PLAYER_X = 1;

    int punts = 0;
    int activePlayer = PLAYER_O; // jugar sempre el jugado O

    String nom = "Nom";

    ConexionSQLiteHelper conn;

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

        getScorePlayers(); // Agafar la puntuaci de base de dades si l'usuari ha juagt abans, carrgar les puntuacions


        // Això fa que només feu una vegada l'insert dels productes quan instal·leu l'aplicació
        SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
        mboolean = settings.getBoolean("FIRST_RUN", false);
        if (!mboolean) {
            InsertPointsP1();
            settings = getSharedPreferences("PREFS_NAME", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("FIRST_RUN", true);
            editor.commit();
        }

        // fer click per tornar a jugar el joc
        restart = (Button) findViewById(R.id.restart);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartGame();
            }
        });

        // Tornar a começa de nou amb puntuacio zero / zero
        resetScore = (Button) findViewById(R.id.resetScore);
        resetScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetScore();
            }
        });

        // Reproduir la musica
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

        Button clickedBtn = findViewById(v.getId()); // agafem l'id de qualsevol boto apretat i convertit en boto
        int clickedTag = Integer.parseInt(v.getTag().toString()); // fem el tag per cambiar els numero de disseny O or X

        // el usuari ha fet click a un lloc que no esta buit(-1) doncs fer return
        if (filledPos[clickedTag] != -1) {
            return;
        }

        filledPos[clickedTag] = activePlayer;

        if (activePlayer == PLAYER_O) {
            clickedBtn.setText("O");
            clickedBtn.setBackground(getDrawable(android.R.color.holo_orange_light));
            activePlayer = PLAYER_X;
            headerText.setText("X turn"); // per mostrar en text view a qui li toca
        }
        else {
            clickedBtn.setText("X");
            clickedBtn.setBackground(getDrawable(android.R.color.holo_blue_light));
            activePlayer = PLAYER_O;
            headerText.setText("O turn"); // per mostrar en text view a qui li toca
        }

        checkForWin(); //comprovarem qui és el guanyador i mostrarem
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
        //comprovarem qui és el guanyador i mostrarem
        // les posicion possbile que un  jugador surti guanyat
        int[][] winningPos = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

        for (int i = 0; i < 8; i++) {
            int val0 = winningPos[i][0]; // agafarem un valora i anirem mirant en la posicio si hi ha 3 files iguals
            int val1 = winningPos[i][1];
            int val2 = winningPos[i][2];

            // si 0 1 2 son iguals i hi ha un gunyador pero no sabem quin
            if (filledPos[val0] == filledPos[val1] && filledPos[val1] == filledPos[val2]) {

                if (filledPos[val0] != -1) {

                    isGameActive = false; // No es pugui jugar

                    // si els tres que tenim son 0 ha guanyat O
                    if (filledPos[val0] == PLAYER_O){
                        showDialog("O is winner");

                        actualizarPointsPlayer1();
                        getScorePlayers();

                    }

                    // si els tres que tenim son 1 ha guanyat X
                    else {
                        showDialog("X is winner");

                        actualizarPointsPlayer2();
                        getScorePlayers();

                    }
                }
            }
        }


    }

    // Fer un insert en las base de dades amb el nom del usuari entrart amb puntuacio 0 als dos jugadors
    private void InsertPointsP1() {
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(Utilidades.CAMPO_USUARIO,nom); // nom
        values.put(Utilidades.CAMPO_PLAYER1,punts); // puntuacio 0 player 1
        values.put(Utilidades.CAMPO_PLAYER2,punts); // puntuacio 0 player 2

        db.insert(Utilidades.TABLA_PUNTUACIO, null, values); // insert
        db.close();
    }

    // Si el jugador 1 ha guanyat doncs fer un insert de 100 punts en la bases de dades
    private void actualizarPointsPlayer1() {

        SQLiteDatabase db = conn.getWritableDatabase(); // connexió

        String x = "Nom"; // nom del usuari

        // el que fem es mirar quina puntuacio te actualmente i sumarlo 100 punts i guardar en BD.

        String value = play1.getText().toString(); // // Agafar el valor que tenim en textview i sumar 100
        int scor = Integer.parseInt(value); // convert string to int

        int score1 = scor + 100; // sumar actual + 100

        String[] parametros={x}; // actualitzar la fila de bases de dade x

        ContentValues values=new ContentValues();
        values.put(Utilidades.CAMPO_PLAYER1,score1);

        db.update(Utilidades.TABLA_PUNTUACIO,values,Utilidades.CAMPO_USUARIO+"=?",parametros); // un update a la taula del joc

        Toast.makeText(getApplicationContext(),"Player 1: +100 Points",Toast.LENGTH_LONG).show();

        db.close();
    }

    // Si el jugador 2 ha guanyat doncs fer un insert de 100 punts en la bases de dades
    private void actualizarPointsPlayer2() {

        SQLiteDatabase db = conn.getWritableDatabase();

        String x = "Nom";

        String value = play2.getText().toString();
        int scor = Integer.parseInt(value);

        int score2 = scor + 100;

        String[] parametros={x};

        ContentValues values=new ContentValues();
        values.put(Utilidades.CAMPO_PLAYER2,score2);

        db.update(Utilidades.TABLA_PUNTUACIO,values,Utilidades.CAMPO_USUARIO+"=?",parametros); // un update a la taula del joc

        Toast.makeText(getApplicationContext(),"Player 2: +100 Points",Toast.LENGTH_LONG).show();

        db.close();
    }

    // restablir la puntuació dels jugadors
    private void resetScore() {

        SQLiteDatabase db = conn.getWritableDatabase();

        String x = "Nom";

        int score = 0;

        // restablir la puntuacio Player1

        String[] parametros1={x};

        ContentValues values1 = new ContentValues();
        values1.put(Utilidades.CAMPO_PLAYER1,score);
        db.update(Utilidades.TABLA_PUNTUACIO,values1,Utilidades.CAMPO_USUARIO+"=?",parametros1);


        // restablir la puntuacio Player2

        String[] parametros2={x};

        ContentValues values2 = new ContentValues();
        values2.put(Utilidades.CAMPO_PLAYER2,score);
        db.update(Utilidades.TABLA_PUNTUACIO,values2,Utilidades.CAMPO_USUARIO+"=?",parametros2);

        getScorePlayers();
        Toast.makeText(getApplicationContext(),"Reset",Toast.LENGTH_LONG).show();

        db.close();
    }

    // obtenir la puntuació de l'usuari
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

    // Mostrar el dialogie amb el restart er tornar a jugar si un dels jugadors ha guanyat
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