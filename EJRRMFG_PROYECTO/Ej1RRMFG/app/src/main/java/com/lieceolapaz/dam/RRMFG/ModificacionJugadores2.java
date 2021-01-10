package com.lieceolapaz.dam.RRMFG;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ModificacionJugadores2 extends AppCompatActivity {

    private EditText codigo;
    private EditText nombre;
    private EditText precio;
    private Spinner posicion;
    private EditText puntos;

    private Button btnEditar;
    private Button btnEliminar;
    private Button btnCancelar;

    private SQLiteDatabase bdd_jugadores;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificacion_jugadores2);

        //Referencias de los campos de texto.
        this.codigo = (EditText)this.findViewById(R.id.editTextTextCodigo);
        this.codigo.setEnabled(false);
        this.nombre = (EditText)this.findViewById(R.id.editTextTextNombre);
        this.precio = (EditText)this.findViewById(R.id.editTextTextPrecio);
        this.puntos = (EditText)this.findViewById(R.id.editTextTextPuntos);

        //Referencia al spinner con layout del spinner (1) y otro para los items (2).
        this.posicion = (Spinner)this.findViewById(R.id.spinnerPosicion);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.posiciones, R.layout.color_spinner_layout);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        this.posicion.setAdapter(spinnerAdapter);

        //Objeto bundle CLAVE <-> VALOR. Para pasar los datos de una actividad a otra.
        Bundle bundle = this.getIntent().getExtras();
        int cod = bundle.getInt("codigo");
        String nom = bundle.getString("nombre");
        double prec = bundle.getDouble("precio");
        String pos = bundle.getString("posicion");
        int pun = bundle.getInt("puntos");

        //Setear los campos.
        this.setTitle(nom);
        this.codigo.setText(""+cod);
        this.nombre.setText(nom);
        this.precio.setText(""+prec);
        int spinnerPosition = spinnerAdapter.getPosition(pos);
        this.posicion.setSelection(spinnerPosition);
        this.puntos.setText(""+pun);

        //Abrimos la base de datos 'db_jugadores' en modo escritura
        final JugadoresSQLiteHelper bddMod_jugadores =
                new JugadoresSQLiteHelper(this, "bd_jugadores", null, 1);
        this.bdd_jugadores = bddMod_jugadores.getWritableDatabase();

        //Referencia botones.
        this.btnEditar = (Button) this.findViewById(R.id.buttonEditarr);
        this.btnEliminar = (Button) this.findViewById(R.id.buttonEliminar);
        this.btnCancelar = (Button) this.findViewById(R.id.btnCancelar);

        //Acciones botones.
        this.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoEditar();
            }
        });
        this.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoEliminar();
            }
        });

        this.btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volverListadoJugadores();
            }
        });

    }

    //Dialogo con la accion de editar.
    private void dialogoEditar(){
        final AlertDialog.Builder dialogoSalir = new AlertDialog.Builder(ModificacionJugadores2.this);
        dialogoSalir.setMessage("Los datos se Modificarán en la base de datos.¿Está seguro?").setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ContentValues valores = new ContentValues();
                        valores.put("nombre", nombre.getText().toString());
                        valores.put("precio", Double.parseDouble(precio.getText().toString()));
                        valores.put("posicion", posicion.getSelectedItem().toString());
                        valores.put("puntos", Integer.parseInt(puntos.getText().toString()));
                        bdd_jugadores.update("jugadores", valores, "codigo=" +  Integer.parseInt(codigo.getText().toString()), null);
                        volverListadoJugadores();
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

    //Dialogo con la accion de eliminar.
    private void dialogoEliminar(){
        final AlertDialog.Builder dialogoSalir = new AlertDialog.Builder(ModificacionJugadores2.this);
        dialogoSalir.setMessage("Los datos se Eliminarán en la base de datos.¿Está seguro?").setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bdd_jugadores.delete("jugadores", "codigo=" + Integer.parseInt(codigo.getText().toString()), null);
                        volverListadoJugadores();
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

    //Intent para cambiar de actividad.
    private void volverListadoJugadores() {
        //Creamos el Intent
        Intent intent =
                new Intent(ModificacionJugadores2.this, ListadoJugadores.class);

        //Iniciamos la nueva actividad
        startActivity(intent);
    }


}

/*
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ModificacionJugadores2 extends AppCompatActivity {

    //Campos
    private EditText codigo;
    private EditText nombre;
    private EditText precio;
    private Spinner posicion;
    private EditText puntos;

    //bdd
    private SQLiteDatabase bdd_jugadores;;

    //Botones
    private Button btnEditar;
    private Button btnEliminar;
    private Button btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificacion_jugadores2);
         this.crearCampos();
         this.guardarCambios();
         this.crearBotones();
    }

    private void crearCampos(){
        //Referencias de los campos de texto.
        this.codigo = (EditText)this.findViewById(R.id.editTextTextCodigo);
        this.codigo.setEnabled(false);
        this.nombre = (EditText)this.findViewById(R.id.editTextTextNombre);
        this.precio = (EditText)this.findViewById(R.id.editTextTextPrecio);
        this.puntos = (EditText)this.findViewById(R.id.editTextTextPuntos);

        //Referencia del spinner con layout del spinner (1) y otro para los items (2).
        this.posicion = (Spinner)this.findViewById(R.id.spinnerPosicion);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.posiciones, R.layout.color_spinner_layout);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        this.posicion.setAdapter(spinnerAdapter);

       //Recupero los datos de la actividad listadoJugadores.
        Bundle bundle = this.getIntent().getExtras();
        int cod = bundle.getInt("codigo");
        String nom = bundle.getString("nombre");
        double prec = bundle.getDouble("precio");
        String pos = bundle.getString("posicion");
        int pun = bundle.getInt("puntos");

        //Setear los campos.
        this.setTitle(nom);
        this.codigo.setText(""+cod);
        this.nombre.setText(nom);
        this.precio.setText(""+prec);
        int spinnerPosition = spinnerAdapter.getPosition(pos);
        this.posicion.setSelection(spinnerPosition);
        this.puntos.setText(""+pun);

    }

    private void guardarCambios() {
        //Abrimos la base de datos 'db_jugadores' en modo escritura
        final JugadoresSQLiteHelper bddMod_jugadores =
                new JugadoresSQLiteHelper(this, "bd_jugadores", null, 1);
        this.bdd_jugadores = bddMod_jugadores.getWritableDatabase();
    }


    //Referencia y accion de los botones
    private void crearBotones(){
        //Referencia botones.
        this.btnEditar = (Button) this.findViewById(R.id.buttonEditarr);
        this.btnEliminar = (Button) this.findViewById(R.id.buttonEliminar);
        this.btnCancelar = (Button) this.findViewById(R.id.btnCancelar);

        //Acciones botones.
        this.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoEditar();
            }
        });

        this.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogoEliminar();
            }
        });


        this.btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volverListadoJugadores();
            }
        });

    }

    //Dialogo con la accion de editar.
    private void dialogoEditar(){
        final AlertDialog.Builder dialogoSalir = new AlertDialog.Builder(ModificacionJugadores2.this);
        dialogoSalir.setMessage("Los datos se Modificarán en la base de datos.¿Está seguro?").setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ContentValues valores = new ContentValues();
                        valores.put("nombre", nombre.getText().toString());
                        valores.put("precio", Double.parseDouble(precio.getText().toString()));
                        valores.put("posicion", posicion.getSelectedItem().toString());
                        valores.put("puntos", Integer.parseInt(puntos.getText().toString()));
                        bdd_jugadores.update("jugadores", valores, "codigo=" +  Integer.parseInt(codigo.getText().toString()), null);
                        volverListadoJugadores();
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

    //Dialogo con la accion de eliminar.
    private void dialogoEliminar(){
        final AlertDialog.Builder dialogoSalir = new AlertDialog.Builder(ModificacionJugadores2.this);
        dialogoSalir.setMessage("Los datos se Eliminarán en la base de datos.¿Está seguro?").setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bdd_jugadores.delete("jugadores", "codigo=" + Integer.parseInt(codigo.getText().toString()), null);
                        volverListadoJugadores();
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

    //Intent para cambiar de actividad.
    private void volverListadoJugadores() {
        //Creamos el Intent
        Intent intent =
                new Intent(ModificacionJugadores2.this, ListadoJugadores.class);

        //Iniciamos la nueva actividad
        startActivity(intent);
    }


}
*/
