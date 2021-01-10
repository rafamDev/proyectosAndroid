package com.lieceolapaz.dam.RRMFG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListadoJugadores extends AppCompatActivity {

    //ArrayList con los objetos jugador.
    private ArrayList<Jugador> listaJugadores;

    //Contenedor donde visualizar los objetos.
    private RecyclerView recycler;

    //Instancia bd.
    private SQLiteDatabase bdd;

    //Clase donde creo la tabla.
    private JugadoresSQLiteHelper bd_jugadores;

    //Adaptador con la vista de jugadores.
    private AdaptadorJugadoresList adaptador_jug_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_jugadores);
         //Tabla
         this.bd_jugadores = new JugadoresSQLiteHelper(getApplicationContext(),"bd_jugadores",null,1);
         this.cargarListaJugadores();
         this.mostrarNumeroDeRegistros();
         this.crearRecycler();
         this.modificacionJugadoresConDatos();
    }

    //Obtener los jugadores
    private void cargarListaJugadores() {
        //Lista de objetos
        this.listaJugadores = new ArrayList<Jugador>();
        //Consulta a la bdd para rellenar el arraylist.
        this.bdd = bd_jugadores.getReadableDatabase();
        Cursor cursor = this.bdd.rawQuery("SELECT * FROM jugadores",null);
         while(cursor.moveToNext()){
            Jugador jugador = new Jugador();
             jugador.setCodigo(cursor.getInt(0));
             jugador.setNombre(cursor.getString(1));
             jugador.setPrecio(cursor.getDouble(2));
             jugador.setPosicion(cursor.getString(3));
             jugador.setPuntos(cursor.getInt(4));
               this.listaJugadores.add(jugador);
         }
    }

    private void crearRecycler(){
        //Contenedor/lista donde se muestra la informacion de la bdd.
        this.recycler = (RecyclerView) findViewById(R.id.recView);
        this.recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        //Creo el adaptador, con los objetos de la lista.
        this.adaptador_jug_list = new AdaptadorJugadoresList(this.listaJugadores);

        //Establezco el contenedor/lista con la informacion que el adaptador obtiene del ArrayList.
        this.recycler.setAdapter(this.adaptador_jug_list);
    }

    //Contador de jugadores
    private void mostrarNumeroDeRegistros(){
        TextView textViewNumeroDeJugadores = (TextView) findViewById(R.id.textViewNumero);
        textViewNumeroDeJugadores.setText("" + this.adaptador_jug_list.getItemCount());
    }

    //Pasar de actividad (modificacionJugadores2) con los datos seleccionados en esta lista. [Editar].
    private void modificacionJugadoresConDatos(){
        //Cuando hago click en cada objeto, envio esa informacion a la actividad ModificacionJugadores2 y la abro. [Editar].
        this.adaptador_jug_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =
                        new Intent(ListadoJugadores.this, ModificacionJugadores2.class);
                Bundle bundle = new Bundle();
                bundle.putInt("codigo", listaJugadores.get(recycler.getChildAdapterPosition(v)).getCodigo());
                bundle.putString("nombre", listaJugadores.get(recycler.getChildAdapterPosition(v)).getNombre());
                bundle.putDouble("precio", listaJugadores.get(recycler.getChildAdapterPosition(v)).getPrecio());
                bundle.putString("posicion", listaJugadores.get(recycler.getChildAdapterPosition(v)).getPosicion());
                bundle.putInt("puntos", listaJugadores.get(recycler.getChildAdapterPosition(v)).getPuntos());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    //Toolbar menu (layout)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.modificarjugadores,menu);
        return true;
    }

    //Toolbar item (icono)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.iconoParaModificarJugadores){
            this.modificacionJugadores();
             return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }

    //Pasar de actividad (modificacionJugadores) [Crear].
    private void modificacionJugadores() {
        //Creamos el Intent
        Intent intent =
                new Intent(ListadoJugadores.this, ModificacionJugadores.class);

        //Iniciamos la nueva actividad
        startActivity(intent);
    }

}
