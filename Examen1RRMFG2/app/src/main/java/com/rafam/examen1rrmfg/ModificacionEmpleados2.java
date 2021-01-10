package com.rafam.examen1rrmfg;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ModificacionEmpleados2 extends AppCompatActivity {
    private EditText dni;
    private EditText nombre;
    private EditText apellido;
    private EditText sueldo;
    private Spinner moneda;
    private EditText departamento;

    private Button btnEditar;
    private Button btnEliminar;
    private Button btnCancelar;

    private SQLiteDatabase bdd_empleados;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificacion_empleados2);

        this.dni = (EditText)this.findViewById(R.id.editTextDni);
        this.dni.setEnabled(false);
        this.nombre = (EditText)this.findViewById(R.id.editTextNombre);
        this.apellido = (EditText)this.findViewById(R.id.editTextApellido);
        this.sueldo = (EditText)this.findViewById(R.id.editTextSueldo);

        this.moneda = (Spinner)this.findViewById(R.id.spinnerMoneda);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.monedas, R.layout.color_spinner_layout);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        this.moneda.setAdapter(spinnerAdapter);

        this.departamento = (EditText)this.findViewById(R.id.editTextDepartamento);

        Bundle bundle = this.getIntent().getExtras();
        String dni = bundle.getString("dni");
        String nom = bundle.getString("nombre");
        String ape = bundle.getString("apellido");
        double suel= bundle.getDouble("sueldo");
        String mon = bundle.getString("moneda");
        int dep = bundle.getInt("departamento");

        this.setTitle("Empleado "+ dni);
        this.dni.setText(dni);
        this.nombre.setText(nom);
        this.apellido.setText(ape);
        this.sueldo.setText(""+ suel);
        int spinnerPosition = spinnerAdapter.getPosition(mon);
        this.moneda.setSelection(spinnerPosition);
        this.departamento.setText(""+dep);

        final EmpleadosSQLiteHelper bddMod_empleados =
                new EmpleadosSQLiteHelper(this, "bd_empleados", null, 1);
        this.bdd_empleados = bddMod_empleados.getWritableDatabase();

        this.btnEditar = (Button) this.findViewById(R.id.buttonEditarr);
        this.btnEliminar = (Button) this.findViewById(R.id.buttonEliminar);
        this.btnCancelar = (Button) this.findViewById(R.id.btnCancelar);

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
                volverMainActivity();
            }
        });

    }

    private void dialogoEditar(){
        final AlertDialog.Builder dialogoSalir = new AlertDialog.Builder(ModificacionEmpleados2.this);
        dialogoSalir.setMessage("Los datos se Modificarán en la base de datos.¿Está seguro?").setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ContentValues valores = new ContentValues();
                        valores.put("dni", dni.getText().toString());
                        valores.put("nombre", nombre.getText().toString());
                        valores.put("apellido", apellido.getText().toString());
                        valores.put("sueldo", Double.parseDouble(sueldo.getText().toString()));
                        valores.put("moneda", moneda.getSelectedItem().toString());
                        valores.put("departamento", Integer.parseInt(departamento.getText().toString()));
                        bdd_empleados.update("empleados", valores, "dni LIKE " +  dni.getText().toString(), null);
                        volverMainActivity();
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

    private void dialogoEliminar(){
        final AlertDialog.Builder dialogoSalir = new AlertDialog.Builder(ModificacionEmpleados2.this);
        dialogoSalir.setMessage("Los datos se Eliminarán en la base de datos.¿Está seguro?").setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bdd_empleados.delete("jugadores", "dni LIKE " + dni.getText().toString(), null);
                        volverMainActivity();
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

    private void volverMainActivity() {
        
        Intent intent =
                new Intent(ModificacionEmpleados2.this, MainActivity.class);

        startActivity(intent);
    }

}