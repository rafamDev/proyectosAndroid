package com.lieceolapaz.dam.RRMFG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorJugadoresList extends RecyclerView.Adapter<AdaptadorJugadoresList.Vista_datos> implements View.OnClickListener {

    private ArrayList<Jugador> lista_jugadores;

    private View.OnClickListener listener;

    public AdaptadorJugadoresList(ArrayList<Jugador> lista_jugadores) {
        this.lista_jugadores = lista_jugadores;
    }

    //Obtengo el jugador SELECCCIONADO de la lista y lo devuelvo.
    @NonNull
    @Override
    public Vista_datos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_jugadoreslist,null,false);

      //Escucha el evento de seleccion.
      view.setOnClickListener(this);

      return new Vista_datos(view);
    }

    //Relleno el recycler con los datos obtenidos de la lista de jugadores.
    @Override
    public void onBindViewHolder(@NonNull Vista_datos holder, int position) {
        holder.txtNombre.setText(this.lista_jugadores.get(position).getNombre());
        holder.txtPrecio.setText(String.valueOf(this.lista_jugadores.get(position).getPrecio()));
        holder.txtPosicion.setText(this.lista_jugadores.get(position).getPosicion());
        holder.txtPuntos.setText("" + this.lista_jugadores.get(position).getPuntos());
    }

    //Contar los objetos jugador.
    @Override
    public int getItemCount() {
        return this.lista_jugadores.size();
    }

    //Listener para escuchar el click en el recyclerView.
    public void setOnClickListener(View.OnClickListener listener){
       this.listener = listener;
    }

    @Override
    public void onClick(View v) {
       if(this.listener != null){
           //Si el evento no esta vacio genero una accion.
           this.listener.onClick(v);
       }
    }

    //Variables para rellenar arriba el recycler.
    public class Vista_datos extends RecyclerView.ViewHolder {

        private TextView txtNombre;
        private TextView txtPrecio;
        private TextView txtPosicion;
        private TextView txtPuntos;

        public Vista_datos(@NonNull View itemView) {
            super(itemView);
              this.txtNombre = (TextView) itemView.findViewById(R.id.textViewNombre);
              this.txtPrecio = (TextView) itemView.findViewById(R.id.textViewPrecio);
              this.txtPosicion = (TextView) itemView.findViewById(R.id.textViewPoscion);
              this.txtPuntos = (TextView) itemView.findViewById(R.id.textViewPuntos);

        }

    }

    
}
