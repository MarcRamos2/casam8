package com.example.projectf2f3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import com.example.projectf2f3.entitades.MenuVo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
public class AdapterDatos extends RecyclerView.Adapter<AdapterDatos.ViewHolderDatos> {

    // Recycle View

    ArrayList<MenuVo> listaMenu; // Array de menus

    public AdapterDatos(ArrayList<MenuVo> listaUsuario) {
        this.listaMenu = listaUsuario;
    }


    @Override
    public ViewHolderDatos onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolderDatos(view);
    }

    // mostrar el text i la imatge a Recyclerview agafant desdes base de dades
    @Override
    public void onBindViewHolder( ViewHolderDatos holder, int position) {
        holder.ElNombre.setText(listaMenu.get(position).getNombre());
        holder.LaDescripcion.setText(listaMenu.get(position).getDescripcion());
        holder.ElPrecio.setText(listaMenu.get(position).getPrecio());

        String urla = listaMenu.get(position).getFoto();
        Picasso.get().load(urla).into(holder.mAnimeImageView); //convertir un enllaç en imatge

    }

    // tamany de la lista
    @Override
    public int getItemCount() {
        return listaMenu.size();
    }


    // mantenir dades
    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView ElNombre, LaDescripcion, ElPrecio;
        ImageView mAnimeImageView;

        public ViewHolderDatos( View itemView) {
            super(itemView);
            ElNombre = (TextView) itemView.findViewById(R.id.idTitulo);
            LaDescripcion = (TextView) itemView.findViewById(R.id.idDescipcion);
            ElPrecio = (TextView) itemView.findViewById(R.id.idPrecio);
            mAnimeImageView = (ImageView) itemView.findViewById(R.id.idImagen);


        }
    }
}
