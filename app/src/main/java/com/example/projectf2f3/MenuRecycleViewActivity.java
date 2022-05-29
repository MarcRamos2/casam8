package com.example.projectf2f3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.projectf2f3.Utilidades.Utilidades;
import com.example.projectf2f3.entitades.MenuVo;
import com.example.projectf2f3.entitades.Usuario;

import java.util.ArrayList;

public class MenuRecycleViewActivity extends AppCompatActivity {

    ArrayList<MenuVo> listaMenu;
    RecyclerView recycler;

    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_recycle_view);

        conn=new ConexionSQLiteHelper(getApplicationContext(),"User_Database",null,1);

        listaMenu=new ArrayList<>();

        recycler= (RecyclerView) findViewById(R.id.recycleID);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        llenarMenu();

        AdapterDatos adapter=new AdapterDatos(listaMenu);
        recycler.setAdapter(adapter);

    }

    private void llenarMenu() {

        SQLiteDatabase db=conn.getReadableDatabase();

        MenuVo menu = null;

        Cursor cursor=db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_MENU,null);

        while (cursor.moveToNext()){
            menu = new MenuVo();

            menu.setNombre(cursor.getString(0));
            menu.setDescripcion(cursor.getString(1));
            menu.setPrecio(cursor.getString(2));
            menu.setFoto(cursor.getString(3));

            listaMenu.add(menu);
        }


    }
}