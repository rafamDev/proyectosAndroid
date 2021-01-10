package com.lieceolapaz.dam.RRMFG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  {

    //Objeto Boton.
    private Button buttonLogin;

    //Objeto textoEmail.
    private EditText textEmail;

    //Objeto textoPassword.
    private EditText textPassword;

    //Contador para error de login.
    private  int contador = 1;

    //Instancio la BDD
    private JugadoresSQLiteHelper bdd_jugadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creo la BDD
        this.bdd_jugadores = new JugadoresSQLiteHelper(this,"bd_jugadores",null,1);

        //Referencia textoEmail
        this.textEmail = (EditText) this.findViewById(R.id.editTextTextEmailAddress);

        //Referencia textoPassword
        this.textPassword = (EditText) this.findViewById(R.id.editTextTextPassword);

        //Referencia Boton y accion login.
        this.buttonLogin = (Button) this.findViewById(R.id.buttonLogin);
        this.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textEmail.getText().toString().equals("admin") && textPassword.getText().toString().equals("liceo")) {
                   this.listaJugadores();
                }else{
                    Toast.makeText(MainActivity.this, "Usuario o password incorrectas", Toast.LENGTH_LONG).show();
                    contador++;
                       if(contador > 3){
                          System.exit(1);
                       }
                }
            }
            //Pasar de actividad (listaJugadores).
            private void listaJugadores(){
                Intent intent =
                        new Intent(MainActivity.this, ListadoJugadores.class);

                startActivity(intent);
            }
        });

    }

}