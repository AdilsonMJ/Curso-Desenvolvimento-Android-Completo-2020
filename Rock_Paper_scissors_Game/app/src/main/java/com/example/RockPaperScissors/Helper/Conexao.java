package com.example.RockPaperScissors.Helper;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Conexao  extends SQLiteOpenHelper{

    private static final String name = "banco.db";
    private static final int version = 1;
    public static String historic;


    public Conexao(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table historic(id integer primary key autoincrement, " +
                    "date varchar(10), time varchar(6), result varchar(5), player int(1), computer int(1))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
