package com.example.com1test.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by masterUNG on 3/22/16 AD.
 */
public class MyOponHelper extends SQLiteOpenHelper{

    //Explicit
    public static final String database_name = "Atc.db";
    private static final int database_version = 1;

    private static final String create_user_table = "create table userTABLE (" +
            "_id integer primary key, " +
            "User text, " +
            "Password text, " +
            "Name text);";

    private static final String create_question_table = "create table questionTABLE (" +
            "_id integer primary key, " +
            "Question text, " +
            "Choice1 text, " +
            "Choice2 text, " +
            "Choice3 text, " +
            "Choice4 text, " +
            "Answer text);";




    public MyOponHelper(Context context) {
        super(context, database_name, null, database_version);
    }   // Constructor

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(create_user_table);
        sqLiteDatabase.execSQL(create_question_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}   // Main Class