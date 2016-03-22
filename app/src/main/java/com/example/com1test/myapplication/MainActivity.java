package com.example.com1test.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    //Explicit ประกาศตัวแปร
    private EditText userEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private String userString, passwordString;
    private MyManage myManage;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind widget
        bindWidget();

        //Request Database
        myManage = new MyManage(this);

        //Button Controller
        buttonController();

    }   // main method

    private void buttonController() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Receive value form Edit Text
                userString = userEditText.getText().toString().trim();
                passwordString = passwordEditText.getText().toString().trim();

                //check space
                if (userString.equals("")|| passwordString.equals("")) {
                //have Space
                MyAlertDialog myAlertDialog = new MyAlertDialog();
                    myAlertDialog.myDialog(MainActivity.this,
                            "มีช่องงว่าง", "กรุณากรอกทุกช่อง ค่ะ");

                } else {
                    //No Space
                }

            } //onclick
        });
    }

    private void bindWidget() {
        userEditText = (EditText) findViewById(R.id.editText);
        passwordEditText = (EditText) findViewById(R.id.editText2);
        loginButton = (Button) findViewById(R.id.button);
    }
}   //main class นี่คื่อ คลาสหลัก
