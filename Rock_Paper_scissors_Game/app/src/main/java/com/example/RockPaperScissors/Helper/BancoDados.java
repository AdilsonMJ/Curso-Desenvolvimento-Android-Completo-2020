package com.example.RockPaperScissors.Helper;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BancoDados extends SQLiteOpenHelper{

    public static  int VERSION = 1;
    public static  String NAME_DB = "BANK.GAME";
    public static  String TABLE_HISTORIC = "HISTORIC";


    public BancoDados(Context context) {
        super(context, NAME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_HISTORIC +
                    " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "date varchar(10), time varchar(6), result varchar(5), player int(1), computer int(1))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
