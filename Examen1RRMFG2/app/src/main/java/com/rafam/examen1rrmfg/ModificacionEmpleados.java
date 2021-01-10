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

public class ModificacionEmpleados extends AppCompatActivity {
    private EditText dni;
    private EditText nombre;
    private EditText apellido;
    private EditText sueldo;
    private Spinner moneda;
    private EditText departamento;

    private Button btnAceptar;
    private Button btnCancelar;

    private SQLiteDatabase bdd_empleados;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificacion_empleados);
        this.setTitle("Nuevo Empleado");

        this.dni = (EditText)this.findViewById(R.id.editTextDni);
        this.nombre = (EditText)this.findViewById(R.id.editTextNombre);
        this.apellido = (EditText)this.findViewById(R.id.editTextApellido);
        this.sueldo = (EditText)this.findViewById(R.id.editTextSueldo);

        this.moneda = (Spinner)this.findViewById(R.id.spinnerMoneda);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.monedas, R.layout.color_spinner_layout);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        this.moneda.setAdapter(spinnerAdapter);

        this.departamento = (EditText)this.findViewById(R.id.editTextDepartamento);

        final EmpleadosSQLiteHelper bddMod_empleados =
                new EmpleadosSQLiteHelper(this, "bd_empleados", null, 1);
        this.bdd_empleados = bddMod_empleados.getWritableDatabase();

        this.btnAceptar = (Button) this.findViewById(R.id.buttonAceptar);
        this.btnCancelar = (Button) this.findViewById(R.id.buttonCancelar);

        this.btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder dialogoSalir = new AlertDialog.Builder(ModificacionEmpleados.this);
                dialogoSalir.setMessage("Los datos se guardarán en la base de datos.¿Está seguro?").setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Recuperamos los valores de los campos de texto
                                String dn = dni.getText().toString();
                                String nom = nombre.getText().toString();
                                String ape = apellido.getText().toString();
                                double sue = Double.parseDouble(sueldo.getText().toString());
                                String mon = moneda.getSelectedItem().toString();
                                int dep = Integer.parseInt(departamento.getText().toString());

                                ContentValues valores = new ContentValues();
                                valores.put("dni", dn);
                                valores.put("nombre", nom);
                                valores.put("apellido", ape);
                                valores.put("sueldo", sue);
                                valores.put("moneda", mon);
                                valores.put("departamento", dep);

                                bdd_empleados.update("empleados", valores, "dni LIKE " +  dni.getText().toString(), null);

                                bdd_empleados.insert("empleados", null, valores);

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
        });

        this.btnCancelar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialogoCancelar();
        }
    });
}

    private void dialogoCancelar(){
        final AlertDialog.Builder dialogoSalir = new AlertDialog.Builder(ModificacionEmpleados.this);
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


    private void volverMainActivity() {

        Intent intent =
                new Intent(ModificacionEmpleados.this, MainActivity.class);

        startActivity(intent);
    }


}