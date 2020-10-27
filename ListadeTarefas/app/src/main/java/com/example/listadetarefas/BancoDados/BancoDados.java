package com.example.listadetarefas.BancoDados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class BancoDados extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String NOME_DB = "DB_TAREFAS";
    public static String TABELAS_TAREFAS = "tarefas";


    public BancoDados(Context context) {
        super(context, NOME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABELAS_TAREFAS
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " nome TEXT NOT NULL); ";

        try{
            db.execSQL(sql);
            Log.i("INFO DB: ", "Sucesso ao criar a tabela");
        } catch (Exception e) {
            Log.i("INFO DB", "Erro ao criar a tabela " + e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       /*
        String sql = ... Atualizacao da tabela... ATapagar, atualizar, manipular...

        try{
            db.execSQL(sql);
            Log.i("INFO DB: ", "Sucesso ao Atualizar  App");
        } catch (Exception e) {
            Log.i("INFO DB", "Erro ao Atualizar  App " + e.getMessage());
        }

        */
    }
}
