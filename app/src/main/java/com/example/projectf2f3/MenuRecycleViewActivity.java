package com.example.projectf2f3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import com.example.projectf2f3.Utilidades.Utilidades;
import com.example.projectf2f3.entitades.MenuVo;
import com.example.projectf2f3.entitades.Usuario;

import java.util.ArrayList;

// Main Menu Recycle View
public class MenuRecycleViewActivity extends AppCompatActivity {

    ArrayList<MenuVo> listaMenu;
    RecyclerView recycler;

    ConexionSQLiteHelper conn;
    boolean mboolean = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_recycle_view);

        conn=new ConexionSQLiteHelper(getApplicationContext(),"User_Database",null,1);

        listaMenu=new ArrayList<>();

        // Això fa que només feu una vegada l'insert quan instal·leu l'aplicació
        SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
        mboolean = settings.getBoolean("FIRST_RUN", false);
        if (!mboolean)
        {
            InsertMenu();

            settings = getSharedPreferences("PREFS_NAME", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("FIRST_RUN", true);
            editor.commit();
        }

        recycler= (RecyclerView) findViewById(R.id.recycleID);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        omplirMenu();

        AdapterDatos adapter=new AdapterDatos(listaMenu);
        recycler.setAdapter(adapter);

    }

    private void omplirMenu() {

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

    // insertar dades a Base de dades SQL
    private void InsertMenu() {

        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues v1 = new ContentValues();
        ContentValues v2 = new ContentValues();
        ContentValues v3 = new ContentValues();
        ContentValues v4 = new ContentValues();
        ContentValues v5 = new ContentValues();
        ContentValues v6 = new ContentValues();
        ContentValues v7 = new ContentValues();
        ContentValues v8= new ContentValues();
        ContentValues v9 = new ContentValues();
        ContentValues v10 = new ContentValues();

        v1.put(Utilidades.CAMPO_NOMBRE_MENU,"Burger");
        v1.put(Utilidades.CAMPO_DESCRIP_MENU,"Burger bla bla bla");
        v1.put(Utilidades.CAMPO_PRICE_MENU,"5€");
        v1.put(Utilidades.CAMPO_FOTO_MENU,"https://mcdonalds.es/api/cms/images/mcdonalds-es/358419ae-5d60-4b34-999b-2c8d744bd4a8_1080x943_Best-Burger-clasicas-cuarto-libra.png?auto=compress,format");

        v2.put(Utilidades.CAMPO_NOMBRE_MENU,"Pizza");
        v2.put(Utilidades.CAMPO_DESCRIP_MENU,"Pizza bla bla bla ");
        v2.put(Utilidades.CAMPO_PRICE_MENU,"5€");
        v2.put(Utilidades.CAMPO_FOTO_MENU,"https://w6h5a5r4.rocketcdn.me/wp-content/uploads/2019/06/pizza-con-chorizo-y-verduras-1080x671.jpg");

        v3.put(Utilidades.CAMPO_NOMBRE_MENU,"Coca Cola");
        v3.put(Utilidades.CAMPO_DESCRIP_MENU,"Coca Cola bla bla bla");
        v3.put(Utilidades.CAMPO_PRICE_MENU,"10€");
        v3.put(Utilidades.CAMPO_FOTO_MENU,"https://mcdonalds.es/api/cms/images/mcdonalds-es/5b6931a9-76d3-4f32-a446-e8f26e8d3a69_producto-237.png?auto=compress,format");

        v4.put(Utilidades.CAMPO_NOMBRE_MENU,"7Up");
        v4.put(Utilidades.CAMPO_DESCRIP_MENU,"7Up bla bla bla");
        v4.put(Utilidades.CAMPO_PRICE_MENU,"5€");
        v4.put(Utilidades.CAMPO_FOTO_MENU,"https://assets.tuzonamarket.com/images/producto/huhmDnIIQE.jpg");

        v5.put(Utilidades.CAMPO_NOMBRE_MENU,"Water");
        v5.put(Utilidades.CAMPO_DESCRIP_MENU,"Water bla bla bla");
        v5.put(Utilidades.CAMPO_PRICE_MENU,"5€");
        v5.put(Utilidades.CAMPO_FOTO_MENU,"https://mcdonalds.es/api/cms/images/mcdonalds-es/52cb6a85-a601-4993-8711-de79538c9a0c_producto-78.png?auto=compress,format");

        v6.put(Utilidades.CAMPO_NOMBRE_MENU,"Ice Cream");
        v6.put(Utilidades.CAMPO_DESCRIP_MENU,"Ice Cream bla bla bla");
        v6.put(Utilidades.CAMPO_PRICE_MENU,"10€");
        v6.put(Utilidades.CAMPO_FOTO_MENU,"https://mcdonalds.es/api/cms/images/mcdonalds-es/6020f509-5485-48a0-b428-0fba8389d2bb_2b35038a0877ef087c9d501d1df67c1c.png?auto=compress,format");

        v7.put(Utilidades.CAMPO_NOMBRE_MENU,"Chicken");
        v7.put(Utilidades.CAMPO_DESCRIP_MENU,"Chicken bla bla bla");
        v7.put(Utilidades.CAMPO_PRICE_MENU,"10€");
        v7.put(Utilidades.CAMPO_FOTO_MENU,"https://tmbidigitalassetsazure.blob.core.windows.net/rms3-prod/attachments/37/1200x1200/Crispy-Fried-Chicken_EXPS_TOHJJ22_6445_DR%20_02_03_11b.jpg");

        v8.put(Utilidades.CAMPO_NOMBRE_MENU,"Chips");
        v8.put(Utilidades.CAMPO_DESCRIP_MENU,"Chips bla bla bla");
        v8.put(Utilidades.CAMPO_PRICE_MENU,"5€");
        v8.put(Utilidades.CAMPO_FOTO_MENU,"https://mcdonalds.es/api/cms/images/mcdonalds-es/79140026-c66c-4810-a01a-24757af4b122_producto-31.png?auto=compress,format");

        v9.put(Utilidades.CAMPO_NOMBRE_MENU,"Big Mac");
        v9.put(Utilidades.CAMPO_DESCRIP_MENU,"Big Mac bla bla bla");
        v9.put(Utilidades.CAMPO_PRICE_MENU,"10€");
        v9.put(Utilidades.CAMPO_FOTO_MENU,"https://mcdonalds.es/api/cms/images/mcdonalds-es/157b26b6-b733-4875-b42e-1ab1c6ec2a22_1080x943_Best-Burger-clasicas-bm.png?auto=compress,format");

        v10.put(Utilidades.CAMPO_NOMBRE_MENU,"McWrap Chicken");
        v10.put(Utilidades.CAMPO_DESCRIP_MENU,"McWrap Chicken bla bla bla");
        v10.put(Utilidades.CAMPO_PRICE_MENU,"10€");
        v10.put(Utilidades.CAMPO_FOTO_MENU,"https://mcdonalds.es/api/cms/images/mcdonalds-es/6dc6eef7-9aa6-4ad9-8e32-33a1d71818e6_021284086e58743ce4eff3d0f68e023e.png?auto=compress,format");

        db.insert(Utilidades.TABLA_MENU, null, v1);
        db.insert(Utilidades.TABLA_MENU, null, v2);
        db.insert(Utilidades.TABLA_MENU, null, v3);
        db.insert(Utilidades.TABLA_MENU, null, v4);
        db.insert(Utilidades.TABLA_MENU, null, v5);
        db.insert(Utilidades.TABLA_MENU, null, v6);
        db.insert(Utilidades.TABLA_MENU, null, v7);
        db.insert(Utilidades.TABLA_MENU, null, v8);
        db.insert(Utilidades.TABLA_MENU, null, v9);
        db.insert(Utilidades.TABLA_MENU, null, v10);

        db.close();
    }






}