package com.example.com1test.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TesterActivity extends AppCompatActivity {

    //Explicit
    private TextView studentTextView, dateTextView, questionTextView;
    private RadioGroup choiceRadioGroup;
    private RadioButton choice1RadioButton, choice2RadioButton,
            choice3RadioButton, choice4RadioButton;
    private String studentString, dateString;
    private String[] questionStrings, choice1Strings,
            choice2Strings, choice3Strings, choice4Strings, answerStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tester);

        //Bind Widget
        bindWidget();

        //Show View
        showView();

        //Read All SQLite
        readAllData();


    }   // Main Method

    private void readAllData() {

        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOpenHelper.database_name,
                MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + MyManage.question_table, null);
        cursor.moveToFirst();
        int intCount = cursor.getCount();

        questionStrings = new String[intCount];
        choice1Strings = new String[intCount];
        choice2Strings = new String[intCount];
        choice3Strings = new String[intCount];
        choice4Strings = new String[intCount];
        answerStrings = new String[intCount];

        for (int i = 0; i < intCount; i++) {

            questionStrings[i] = cursor.getString(1);
            choice1Strings[i] = cursor.getString(2);
            choice2Strings[i] = cursor.getString(3);
            choice3Strings[i] = cursor.getString(4);
            choice4Strings[i] = cursor.getString(5);
            answerStrings[i] = cursor.getString(6);

            cursor.moveToNext();
        }   // for


    }   // readAllData

    private void showView() {
        studentString = getIntent().getStringExtra("Student");
        studentTextView.setText(studentString);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        dateString = dateFormat.format(date);
        dateTextView.setText(dateString);


    }

    private void bindWidget() {
        studentTextView = (TextView) findViewById(R.id.textView2);
        dateTextView = (TextView) findViewById(R.id.textView3);
        questionTextView = (TextView) findViewById(R.id.textView4);
        choiceRadioGroup = (RadioGroup) findViewById(R.id.ragChoice);
        choice1RadioButton = (RadioButton) findViewById(R.id.radioButton);
        choice2RadioButton = (RadioButton) findViewById(R.id.radioButton2);
        choice3RadioButton = (RadioButton) findViewById(R.id.radioButton3);
        choice4RadioButton = (RadioButton) findViewById(R.id.radioButton4);
    }

}      // Main Class}