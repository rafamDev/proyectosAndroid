package com.liceolapaz.des.jprf.tresenraya;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button btnNuevaPartida;
    private TresEnRaya terTablero;
    private TextView txtGanador;
    private TextView txtJugador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNuevaPartida = (Button)findViewById(R.id.btnNuevaPartida);
        terTablero = (TresEnRaya)findViewById(R.id.tablero);
        txtGanador = (TextView)findViewById(R.id.txtGanador);
        txtJugador = (TextView)findViewById(R.id.txtJugador);

        txtGanador.setText("");

        btnNuevaPartida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                terTablero.inicializacion();
                actualizarCabecera();
                txtGanador.setText("");
            }
        });

        terTablero.setOnCasillaSeleccionadaListener(new OnCasillaSeleccionadaListener() {
            @Override
            public void onCasillaSeleccionada(int fila, int columna) {
                terTablero.alternarFichaActiva();
                actualizarCabecera();
                if (terTablero.isFin()) {
                    int ganador = terTablero.getGanador();
                    if (ganador == TresEnRaya.FICHA_X) {
                        txtGanador.setText(R.string.ganador_1);
                    } else if (ganador == TresEnRaya.FICHA_O) {
                        txtGanador.setText(R.string.ganador_2);
                    } else {
                        txtGanador.setText(R.string.empate);
                    }
                } else {
                    txtGanador.setText("");
                }
            }
        });
    }

    private void actualizarCabecera() {
        if (terTablero.getFichaActiva() == terTablero.FICHA_X) {
            txtJugador.setText(R.string.jugador_1_cruz);
        } else {
            txtJugador.setText(R.string.jugador_2_circulo);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
