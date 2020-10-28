package com.example.RockPaperScissors.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.RockPaperScissors.model_historic.Model_Historic;

import java.util.ArrayList;
import java.util.List;

public class HistoricDAO {

    private Conexao conexao;
    private SQLiteDatabase banco;

    public HistoricDAO(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }
    public long inserir(Model_Historic model_historic){
        ContentValues values = new ContentValues();
        values.put("date", model_historic.getDate());
        values.put("time", model_historic.getTime());
        values.put("result", model_historic.getResult());
        values.put("player", model_historic.getPlayer());
        values.put("computer", model_historic.getComputer());
       return banco.insert("historic", null, values);

    }
}
