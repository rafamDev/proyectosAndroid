package com.rafam.examen1rrmfg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Empleado> listaEmpleados;

    private SQLiteDatabase bdd;

    private EmpleadosSQLiteHelper bd_empleados;

    private RecyclerView recycler;

    private AdaptadorEmpleadosList adaptador_empleados_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.recycler = (RecyclerView) findViewById(R.id.recView);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        this.bd_empleados = new EmpleadosSQLiteHelper(getApplicationContext(),"bd_empleados",null,1);

        this.listaEmpleados = new ArrayList<Empleado>();

        this.consultaEmpleados();

    }
    private void consultaEmpleados() {
        this.bdd = bd_empleados.getReadableDatabase();
        Cursor cursor = this.bdd.rawQuery("SELECT * FROM empleados",null);

        while(cursor.moveToNext()){
            Empleado empleado = new Empleado();
            empleado.setDni(cursor.getString(0));
            empleado.setNombre(cursor.getString(1));
            empleado.setApeliido(cursor.getString(2));
            empleado.setSueldo(cursor.getDouble(3));
            empleado.setMoneda(cursor.getString(4));
            empleado.setDepartamento(cursor.getInt(5));

            this.listaEmpleados.add(empleado);
        }
        this.adaptador_empleados_list= new AdaptadorEmpleadosList(this.listaEmpleados);

        this.adaptador_empleados_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =
                        new Intent(MainActivity.this, ModificacionEmpleados2.class);

                Bundle bundle = new Bundle();
                bundle.putString("dni", listaEmpleados.get(recycler.getChildAdapterPosition(v)).getDni());
                bundle.putString("nombre", listaEmpleados.get(recycler.getChildAdapterPosition(v)).getNombre());
                bundle.putString("apellido", listaEmpleados.get(recycler.getChildAdapterPosition(v)).getApeliido());
                bundle.putDouble("sueldo", listaEmpleados.get(recycler.getChildAdapterPosition(v)).getSueldo());
                bundle.putString("moneda", listaEmpleados.get(recycler.getChildAdapterPosition(v)).getMoneda());
                bundle.putInt("departamento", listaEmpleados.get(recycler.getChildAdapterPosition(v)).getDepartamento());

                intent.putExtras(bundle);

                startActivity(intent);

            }
        });

        this.recycler.setAdapter(this.adaptador_empleados_list);

        TextView textViewNumeroDeEmpleados = (TextView) findViewById(R.id.textViewNumero);
        textViewNumeroDeEmpleados.setText("" + this.adaptador_empleados_list.getItemCount());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.modificarempleados,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.iconoParaModificarEmpleados){
            this.modificacionEmpleados();
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }


    private void modificacionEmpleados() {
        //Creamos el Intent
        Intent intent =
                new Intent(MainActivity.this, ModificacionEmpleados.class);

        //Iniciamos la nueva actividad
        startActivity(intent);
    }

}