package com.example.com1test.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by com1test on 22/3/2559.
 */
public class MyManage {
    //Explicit
    private MyOponHelper myOponHelper;
    private SQLiteDatabase writeSqLiteDatabase,readSqLiteDatabase;


    public MyManage(Context context) {
        //Create & Connected SQLite
        myOponHelper = new MyOponHelper(context);
        writeSqLiteDatabase = myOponHelper.getWritableDatabase();
        readSqLiteDatabase = myOponHelper.getReadableDatabase();


    }   // Constructor

}       //Main Class
