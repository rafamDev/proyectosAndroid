package com.lieceolapaz.dam.RRMFG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ModificacionJugadores extends AppCompatActivity {

    private EditText codigo;
    private EditText nombre;
    private EditText precio;
    private Spinner posicion;
    private EditText puntos;

    private Button btnAceptar;
    private Button btnCancelar;

    private SQLiteDatabase bdd_jugadores;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificacion_jugadores);
        this.setTitle("Nuevo Jugador");

        this.codigo = (EditText)this.findViewById(R.id.editTextTextCodigo);
        this.codigo.setEnabled(false);

        this.nombre = (EditText)this.findViewById(R.id.editTextTextNombre);
        this.precio = (EditText)this.findViewById(R.id.editTextTextPrecio);

        this.posicion = (Spinner)this.findViewById(R.id.spinnerPosicion);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.posiciones, R.layout.color_spinner_layout);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        this.posicion.setAdapter(spinnerAdapter);

        this.puntos = (EditText)this.findViewById(R.id.editTextTextPuntos);

        //Abrimos la base de datos 'db_jugadores' en modo escritura
        final JugadoresSQLiteHelper bddMod_jugadores =
                new JugadoresSQLiteHelper(this, "bd_jugadores", null, 1);

        this.bdd_jugadores = bddMod_jugadores.getWritableDatabase();

        this.btnAceptar = (Button) this.findViewById(R.id.buttonAceptar);
        this.btnCancelar = (Button) this.findViewById(R.id.buttonCancelar);

        this.btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder dialogoSalir = new AlertDialog.Builder(ModificacionJugadores.this);
                dialogoSalir.setMessage("Los datos se guardarán en la base de datos.¿Está seguro?").setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Recuperamos los valores de los campos de texto
                                String nom = nombre.getText().toString();
                                double prec = Double.parseDouble(precio.getText().toString());
                                String pos = posicion.getSelectedItem().toString();
                                int pun = Integer.parseInt(puntos.getText().toString());

                                ContentValues nuevoRegistro = new ContentValues();
                                nuevoRegistro.put("nombre", nom);
                                nuevoRegistro.put("precio", prec);
                                nuevoRegistro.put("posicion", pos);
                                nuevoRegistro.put("puntos", pun);
                                bdd_jugadores.insert("jugadores", null, nuevoRegistro);

                                modificacionJugadores();
                            }
                        });
                dialogoSalir.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(1);
                    }
                });
                dialogoSalir.setNeutralButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog titulo2 = dialogoSalir.create();
                titulo2.setTitle("ACEPTAR");
                titulo2.show();
            }
        });

        this.btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dialogoCancelar();
            }
        });
    }

   private void dialogoCancelar(){
       final AlertDialog.Builder dialogoSalir = new AlertDialog.Builder(ModificacionJugadores.this);
       dialogoSalir.setMessage("Los datos no se guardarán.¿Está seguro de cancelar?").setCancelable(false)
               .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       System.exit(1);
                   }
               });
       dialogoSalir.setNegativeButton("No", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               dialog.cancel();
           }
       });
       AlertDialog titulo2 = dialogoSalir.create();
       titulo2.setTitle("CANCELAR");
       titulo2.show();
   }


    private void modificacionJugadores() {
        //Creamos el Intent
        Intent intent =
                new Intent(ModificacionJugadores.this, ListadoJugadores.class);

        //Iniciamos la nueva actividad
        startActivity(intent);
    }

}