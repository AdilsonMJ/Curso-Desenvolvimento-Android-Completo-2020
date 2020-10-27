package com.example.listadetarefas.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.listadetarefas.BancoDados.TarefaDAO;
import com.example.listadetarefas.R;
import com.example.listadetarefas.model.TarefaModel;
import com.google.android.material.textfield.TextInputEditText;

public class AdicionarTarefasActivity extends AppCompatActivity {

    private TextInputEditText editTarefa;
    private TarefaModel tarefaAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefas);

        editTarefa = findViewById(R.id.TextTarefa);

        //Recuperar tarefa, caso seja edicao
        tarefaAtual = (TarefaModel) getIntent().getSerializableExtra("tarefaSelecionada");

        //Configurando tarefa na caixa de texto
        if(tarefaAtual != null){
            editTarefa.setText(tarefaAtual.getNomeTarefa());
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


                    // EXECUTAR ACAO PARA ITEM SALVAR
                    TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());

                if (tarefaAtual != null ) {
                    String nomeTarefa = editTarefa.getText().toString();
                    if ( !nomeTarefa.isEmpty()){
                        TarefaModel tarefa = new TarefaModel();
                        tarefa.setNomeTarefa(nomeTarefa);
                        tarefa.setId(tarefaAtual.getId());

                        //Atualizar no banco de dados
                        if(tarefaDAO.atualizar( tarefa )){
                            finish();
                            Toast.makeText(getApplicationContext(),
                                    "Sucesso ao atualizar tarefa",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Erro ao atualizar  tarefa",
                                    Toast.LENGTH_SHORT).show();
                        }


                    }


        } else {
            //Executar acao para o item salvar
            String nomeTarefa = editTarefa.getText().toString();

            if(!nomeTarefa.isEmpty()) {

                TarefaModel tarefa = new TarefaModel();
                tarefa.setNomeTarefa(nomeTarefa);

                if(tarefaDAO.salvar(tarefa)){
                    finish();
                    Toast.makeText(getApplicationContext(),
                            "Sucesso ao salvar tarefa",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Erro ao salvar  tarefa",
                            Toast.LENGTH_SHORT).show();
                }

            }
        }

        return super.onOptionsItemSelected(item);
    }
}