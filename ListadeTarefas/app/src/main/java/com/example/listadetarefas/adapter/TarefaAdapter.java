package com.example.listadetarefas.adapter;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listadetarefas.R;
import com.example.listadetarefas.model.TarefaModel;

import java.util.List;

public class TarefaAdapter extends RecyclerView.Adapter<TarefaAdapter.MyVH> {

    private List<TarefaModel> listaTarefas;

    public TarefaAdapter(List<TarefaModel> lista){
        this.listaTarefas = lista;
    }


    @NonNull
    @Override
    public MyVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_tarefas_activity, parent, false);

        return new MyVH(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyVH holder, int position) {

        TarefaModel tarefa = listaTarefas.get(position);
        holder.tarefa.setText(tarefa.getNomeTarefa());

    }

    @Override
    public int getItemCount() {
        return this.listaTarefas.size();
    }

    public class MyVH extends RecyclerView.ViewHolder{

        TextView tarefa;

        public MyVH(@NonNull View itemView) {
            super(itemView);

            tarefa = itemView.findViewById(R.id.TVTarefa);

        }
    }
}
