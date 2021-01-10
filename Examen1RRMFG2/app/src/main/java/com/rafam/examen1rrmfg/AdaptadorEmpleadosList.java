package com.rafam.examen1rrmfg;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorEmpleadosList extends RecyclerView.Adapter<AdaptadorEmpleadosList.Vista_datos> implements View.OnClickListener {

    private ArrayList<Empleado> lista_empleados;

    private View.OnClickListener listener;

    public AdaptadorEmpleadosList(ArrayList<Empleado> lista_empleados) {
        this.lista_empleados = lista_empleados;
    }

    @NonNull
    @Override
    public Vista_datos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empleadoslist,null,false);

        view.setOnClickListener(this);

        return new Vista_datos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vista_datos holder, int position) {
        holder.txtNombre.setText(this.lista_empleados.get(position).getNombre());
        holder.txtApellido.setText(this.lista_empleados.get(position).getApeliido());
        holder.txtDepartamento.setText("departamento");
        holder.txtNumDepartamento.setText("" + this.lista_empleados.get(position).getDepartamento());
    }


    @Override
    public int getItemCount() {
        return this.lista_empleados.size();
    }


    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(this.listener != null){

            this.listener.onClick(v);
        }
    }

    public class Vista_datos extends RecyclerView.ViewHolder {

        private TextView txtNombre;
        private TextView txtApellido;
        private TextView txtDepartamento;
        private TextView txtNumDepartamento;

        public Vista_datos(@NonNull View itemView) {
            super(itemView);
            this.txtNombre = (TextView) itemView.findViewById(R.id.textViewNombre);
            this.txtApellido = (TextView) itemView.findViewById(R.id.textViewApellido);
            this.txtDepartamento = (TextView) itemView.findViewById(R.id.textViewDepartamento);
            this.txtNumDepartamento = (TextView) itemView.findViewById(R.id.textViewNumDepartamento);

        }

    }


}
