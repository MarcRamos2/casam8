package com.example.projectf2f3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projectf2f3.Utilidades.Utilidades;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    Button btnLogin;

    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        conn = new ConexionSQLiteHelper(getApplicationContext(),"User_Database",null,1);

        username = (EditText) findViewById(R.id.usernameLogin);
        password = (EditText) findViewById(R.id.passwordLogin);

        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (user.equals("") || pass.equals(""))
                {
                    Toast.makeText(LoginActivity.this, "Please enter the Credentials.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Boolean result = checkusernamePassword(user,pass);

                    if(result == true)
                    {
                        Intent intent = new Intent(getApplicationContext(), PrincipalActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "Invalid Credential.", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
    }


    // Comprovar si existeix el user amb el email i password entrart
    public Boolean checkusernamePassword(String username,String password)
    {
        SQLiteDatabase db = conn.getWritableDatabase();

        Cursor cursor=db.rawQuery("SELECT " +Utilidades.CAMPO_NOMBRE+
        " FROM " +Utilidades.TABLA_USUARIO+ " WHERE " + Utilidades.CAMPO_EMAIL+ "=?" +
                " AND " + Utilidades.CAMPO_PASSWORD+ " = ? " ,new String[] {username,password});

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