package com.example.projectf2f3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projectf2f3.Utilidades.Utilidades;

public class MainActivity extends AppCompatActivity {

    // Register User Activity

    EditText campoEmail,campoUser,campoPassword,repassword;
    Button btnSingUp, btnSignIn;

    ConexionSQLiteHelper conn; // connexio a base de dades per guardar les dades del usuari

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conn = new ConexionSQLiteHelper(getApplicationContext(),"User_Database",null,1);

        campoUser = (EditText) findViewById(R.id.user);
        campoEmail = (EditText) findViewById(R.id.email);
        campoPassword = (EditText) findViewById(R.id.password);
        repassword = (EditText) findViewById(R.id.repassword);

        btnSingUp = (Button) findViewById(R.id.btnSignUp);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);

        btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = campoUser.getText().toString();
                String email = campoEmail.getText().toString();
                String pass = campoPassword.getText().toString();
                String repass = repassword.getText().toString();

                // si els edittext estan buits llavors mostrar missatge
                if (user.equals("") || email.equals("") || pass.equals("") || repass.equals("") )
                {
                    Toast.makeText(MainActivity.this, "Fill all the Fields.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(pass.equals(repass))
                    {
                        Boolean usercheckResult = CheckUserEmail(email); // funcion si existeix el email

                        if(!usercheckResult) // no existeix
                        {
                            Boolean regResult = InsertUser(user, email ,pass); // funcio si s'ha insertat correctamente l'usuari

                            if(regResult) //si
                            {
                                Toast.makeText(MainActivity.this, "Resgistration Successful.", Toast.LENGTH_SHORT).show();
                                limpiar();
                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                            }
                            else // no
                            {
                                Toast.makeText(MainActivity.this, "Resgistration failed.", Toast.LENGTH_SHORT).show();
                            }

                        } // ja existeix email
                        else
                        {
                            Toast.makeText(MainActivity.this, "User Email already Exist. \n Please Sign In", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Password not Matching.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // si l'usuari ja esta registrart i vol entrar el seu compte
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);

            }
        });
    }

    // netejar els edittexts despres de registrar
    private void limpiar() {
        campoUser.setText("");
        campoEmail.setText("");
        campoPassword.setText("");
        repassword.setText("");
    }

    // Insertar el User
    public Boolean InsertUser(String username, String email, String password)
    {
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Utilidades.CAMPO_NOMBRE, username);
        contentValues.put(Utilidades.CAMPO_EMAIL, email);
        contentValues.put(Utilidades.CAMPO_PASSWORD, password);
        long result = db.insert(Utilidades.TABLA_USUARIO, null, contentValues);

        if (result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    // Comprovar si existeix el email en bd
    public Boolean CheckUserEmail(String username)
    {
        SQLiteDatabase db=conn.getWritableDatabase();

        Cursor cursor=db.rawQuery("SELECT * FROM " +Utilidades.TABLA_USUARIO+" WHERE "+Utilidades.CAMPO_EMAIL+"=? ", new String[] {username});

        if (cursor.getCount() > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}