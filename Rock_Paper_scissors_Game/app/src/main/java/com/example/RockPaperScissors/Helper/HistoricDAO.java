package com.example.RockPaperScissors.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.RockPaperScissors.model_historic.Model_Historic;

import java.util.ArrayList;
import java.util.List;

public class HistoricDAO implements IHistoricDAO{



    private SQLiteDatabase write;
    private SQLiteDatabase read;

    public HistoricDAO(Context context){
        BancoDados db = new BancoDados(context);
        write = db.getWritableDatabase();
        read = db.getReadableDatabase();

    }

    @Override
    public Boolean salvar(Model_Historic historic) {
        ContentValues values = new ContentValues();
        values.put("date", historic.getDate());
        values.put("time", historic.getTime());
        values.put("result", historic.getResult());
        values.put("player", historic.getPlayer());
        values.put("computer", historic.getComputer());
        write.insert(BancoDados.TABLE_HISTORIC, null, values);
        return true;

    }

    @Override
    public List<Model_Historic> listar() {

        List<Model_Historic> historic = new ArrayList<>();

        String sql = "SELECT * FROM " + BancoDados.TABLE_HISTORIC + ";";
        Cursor c = read.rawQuery(sql, null);

        while(c.moveToNext()){

            Model_Historic model = new Model_Historic();

            Long id = c.getLong(c.getColumnIndex("id"));
            String dateHistoric     = c.getString(c.getColumnIndex("date"));
            String timeHistoric     = c.getString(c.getColumnIndex("time"));
            String resultHistoric   = c.getString(c.getColumnIndex("result"));
            String playerHistoric   = c.getString(c.getColumnIndex("player"));
            int ph = Integer.parseInt(playerHistoric);
            String computerHistoric = c.getString(c.getColumnIndex("computer"));
            int ch = Integer.parseInt(computerHistoric);

            model.setDate(dateHistoric);
            model.setTime(timeHistoric);
            model.setResult(resultHistoric);
            model.setPlayer(ph);
            model.setComputer(ch);

            historic.add(model);
        }

        return historic;
    }
}
