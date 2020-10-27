package com.example.listadetarefas.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.listadetarefas.BancoDados.BancoDados;
import com.example.listadetarefas.BancoDados.TarefaDAO;
import com.example.listadetarefas.R;
import com.example.listadetarefas.adapter.TarefaAdapter;
import com.example.listadetarefas.helper.RecyclerItemClickListener;
import com.example.listadetarefas.model.TarefaModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView RV_Lista_Tarefas;
    private TarefaAdapter tarefaAdapter;
    private TarefaModel tarefaselecionada;
    private List<TarefaModel> listaTarefas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Configurar Recycler
        RV_Lista_Tarefas = findViewById(R.id.RV_Lista_Tarefas);

        BancoDados db = new BancoDados(getApplicationContext());

        ContentValues cv = new ContentValues();
        db.getWritableDatabase().insert("tarefas", null, cv);

        //Adicionar Evento de clique
        RV_Lista_Tarefas.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        RV_Lista_Tarefas,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {


                                //Recuperar tarefa para edicao
                                TarefaModel tarefaSelecionada = listaTarefas.get(position);

                                Intent intent = new Intent(MainActivity.this, AdicionarTarefasActivity.class);
                                intent.putExtra("tarefaSelecionada", tarefaSelecionada);

                                startActivity(intent);


                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                                //Recuperar tarefa para deletar
                                tarefaselecionada = listaTarefas.get(position);

                                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                                //Configurar título e mensagem
                                dialog.setTitle("Confirmar exclusao");
                                dialog.setMessage("Deseja excluir a tarefa: " + tarefaselecionada.getNomeTarefa()+ " ? " );

                                dialog.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
                                        if(tarefaDAO.deletar(tarefaselecionada)){
                                            carregarListaTarefas();
                                            Toast.makeText(getApplicationContext(),
                                                    "Tarefa Apagada com sucesso",
                                                    Toast.LENGTH_SHORT).show();

                                        } else {
                                            Toast.makeText(getApplicationContext(),
                                                    "Erro ao excluir tarefa",
                                                    Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });

                                dialog.setNegativeButton("Nao", null);

                                //Exibir dialog
                                dialog.create();
                                dialog.show();


                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getApplicationContext(), AdicionarTarefasActivity.class);
                startActivity( intent );
            }
        });
    }


    public void carregarListaTarefas(){
        //Listar tarefas
            TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
            listaTarefas = tarefaDAO.listar();


        /*
            Exibir lista de tarefas no Recyclerview
         */

        //Configurar um adapter
        tarefaAdapter = new TarefaAdapter(listaTarefas);

        //Configurar Recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        RV_Lista_Tarefas.setLayoutManager(layoutManager);
        RV_Lista_Tarefas.setHasFixedSize(true);
        RV_Lista_Tarefas.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        RV_Lista_Tarefas.setAdapter(tarefaAdapter);
    }

    @Override
    protected void onStart() {
        carregarListaTarefas();
        super.onStart();
    }

}