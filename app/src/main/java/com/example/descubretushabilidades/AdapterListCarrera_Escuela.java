package com.example.descubretushabilidades;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.okhttp.HttpUrl;

import java.util.ArrayList;

public class AdapterListCarrera_Escuela extends RecyclerView.Adapter<AdapterListCarrera_Escuela.ViewHolderCarrera_Escuelas> {

    private ArrayList<listEscuela> listaEscuelas;

    public AdapterListCarrera_Escuela(ArrayList<listEscuela> listaEscuelas){
        this.listaEscuelas=listaEscuelas;
    }

    @NonNull
    @Override
    public ViewHolderCarrera_Escuelas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_escuelas,parent,false);
        return new ViewHolderCarrera_Escuelas(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCarrera_Escuelas holder, int position) {
        holder.NombreEscuela.setText(listaEscuelas.get(position).getNescuela());
        holder.TelefonoEscuela.setText(listaEscuelas.get(position).getTelEscuela());
        holder.Telefono2Escuela.setText(listaEscuelas.get(position).getTelEscuela2());
        holder.DireccionEscuela.setText(listaEscuelas.get(position).getDirEscuela());
        holder.sectorEscuela.setText(listaEscuelas.get(position).getSectorEscuela());
        holder.urlEscuela.setText(listaEscuelas.get(position).getUrlEscuela());
        holder.url=listaEscuelas.get(position).getUrlEscuela();
    }

    @Override
    public int getItemCount() {
        return listaEscuelas.size();
    }

    public class ViewHolderCarrera_Escuelas extends RecyclerView.ViewHolder {
        Context context;
        TextView NombreEscuela;
        TextView TelefonoEscuela;
        TextView Telefono2Escuela;
        TextView DireccionEscuela;
        TextView sectorEscuela;
        TextView urlEscuela;
        String url;

        public ViewHolderCarrera_Escuelas(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            NombreEscuela=itemView.findViewById(R.id.EscuelaName);
            TelefonoEscuela=itemView.findViewById(R.id.Telefono_escuela);
            Telefono2Escuela=itemView.findViewById(R.id.Telefono2_escuela);
            DireccionEscuela=itemView.findViewById(R.id.Direccion_escuela);
            sectorEscuela=itemView.findViewById(R.id.Sector_escuela);
            urlEscuela=itemView.findViewById(R.id.Url_escuela);
            urlEscuela.setSelected(true);

            itemView.setOnClickListener((v) ->{
                Log.d("demo","Item cliceado");
            });

            itemView.findViewById(R.id.Url_escuela).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri direccion = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, direccion);
                    context.startActivity(intent);
                    Log.d("demo","url"+url);
                }
            });

        }
    }
}