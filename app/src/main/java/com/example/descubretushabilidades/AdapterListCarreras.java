package com.example.descubretushabilidades;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterListCarreras extends RecyclerView.Adapter<AdapterListCarreras.ViewHolderCarreras> implements View.OnClickListener {

    ArrayList<listCarreras> listaCarreras;
    View.OnClickListener listener;
    String idCarrera;

    public AdapterListCarreras(ArrayList<listCarreras> listaCarreras) {
        this.listaCarreras = listaCarreras;
    }

    @NonNull
    @Override
    public AdapterListCarreras.ViewHolderCarreras onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_areas,parent,false);
        view.setOnClickListener(this);
        return new ViewHolderCarreras(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListCarreras.ViewHolderCarreras holder, int position) {
        holder.TW_dato.setText(listaCarreras.get(position).getCarreraName());
        holder.TW_numero.setText(Integer.toString(position+1));
        idCarrera=listaCarreras.get(position).getIdCarr();
    }

    @Override
    public int getItemCount() {
        return listaCarreras.size();
    }

    public void setOnClickListener(View.OnClickListener Vlistener){
        this.listener=Vlistener;
    }

    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolderCarreras extends RecyclerView.ViewHolder {

        TextView TW_dato;
        TextView TW_numero;

        public ViewHolderCarreras(@NonNull View itemView) {
            super(itemView);
            TW_dato=itemView.findViewById(R.id.nombreCarrera);
            TW_numero = itemView.findViewById(R.id.noCarrera);
        }
    }
}
