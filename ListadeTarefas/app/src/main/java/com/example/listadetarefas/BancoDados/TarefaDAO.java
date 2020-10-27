package com.example.listadetarefas.BancoDados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.listadetarefas.model.TarefaModel;

import java.util.ArrayList;
import java.util.List;

public class TarefaDAO implements ITarefaDAO {

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public TarefaDAO (Context context ){

        BancoDados db = new BancoDados(context);
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();

    }

    @Override
    public boolean salvar(TarefaModel tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa() );

        try {
            escreve.insert(BancoDados.TABELAS_TAREFAS, null, cv );
            Log.i("INFO", "Tarefa salva com sucesso!");
        }catch (Exception e){
            Log.e("INFO", "Erro ao salvar tarefa " + e.getMessage() );
            return false;
        }

        return true;
    }


    @Override
    public boolean atualizar(TarefaModel tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa() );



        try{
            String[] args = {tarefa.getId().toString()};
            escreve.update(BancoDados.TABELAS_TAREFAS, cv, "id=?", args);
            Log.i("INFO", "TAREFA ATUALIZADA COM SUCESSO");
        } catch (Exception e){
            Log.e("INFO", "ERRO AO ATUALIZAR TAREFA " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public Boolean deletar(TarefaModel tarefa) {

        try{
            String[] args = {tarefa.getId().toString()};
            escreve.delete(BancoDados.TABELAS_TAREFAS, "id=?", args);
            Log.i("INFO", "TAREFA excluida COM SUCESSO");
        } catch (Exception e){
            Log.e("INFO", "ERRO AO deletar TAREFA " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public List<TarefaModel> listar() {

        List<TarefaModel> tarefas = new ArrayList<>();

        String sql = "SELECT * FROM " + BancoDados.TABELAS_TAREFAS + ";";
        Cursor c = le.rawQuery(sql, null);

        while(c.moveToNext()){
            TarefaModel tarefa = new TarefaModel();

            Long id = c.getLong( c.getColumnIndex("id") );
            String nomeTarefa = c.getString( c.getColumnIndex("nome") );

            tarefa.setId(id);
            tarefa.setNomeTarefa(nomeTarefa);

            tarefas.add(tarefa);
        }

        return tarefas;

    }
}
