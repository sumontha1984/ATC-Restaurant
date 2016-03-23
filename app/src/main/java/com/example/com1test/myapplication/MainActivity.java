package com.example.com1test.myapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    //Explicit
    private EditText userEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private String userString, passwordString;
    private MyManage myManage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind Widget
        bindWidget();

        //Request Database
        myManage = new MyManage(this);

        //Button Controller
        buttonController();

        //Tester Add Value
        //tester();

        //Delete All SQLite
        deleteAllSQLite();

        //Synchronize JSON to SQLite
        synJSONtoSQLite();

    }   // Main Method


    private void synJSONtoSQLite() {

        //Connected http
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy

                .Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        int intTABLE = 0;
        while (intTABLE<=1) {

            //1 Create InputStream
            InputStream inputStream = null;
            String[] urlJSONStrings = {"http://swiftcodingthai.com/atc/php_get_user_sumontha.php",
            "http://swiftcodingthai.com/atc/php_get_question_sumontha.php"};


            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(urlJSONStrings[intTABLE]);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                inputStream = httpEntity.getContent();

            } catch (Exception e) {
                Log.d("test", "Input ==>" + e.toString());

            }


            //2 Create JSON String
            String strjsJSon = null;
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String strLine = null;
                StringBuilder stringBuilder = new StringBuilder();

                while ((strLine = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(strLine);
                }
                inputStream.close();
                strjsJSon = stringBuilder.toString();
            } catch (Exception e) {
                Log.d("test", "Input ==>" + e.toString());
            }



            //3 Update to SQLite

            try {
                JSONArray jsonArray = new JSONArray(strjsJSon);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    switch (intTABLE) {
                        case 0:
                            //userTABLE

                            String strUser = jsonObject.getString(MyManage.column_User);
                            String strPassword = jsonObject.getString(MyManage.column_Password);
                            String strName = jsonObject.getString(MyManage.column_Name);


                            myManage.addValueToSQLite(strUser, strPassword, strName);

                            break;
                        case 1:
                            //questionTABLE
                            String StrQuestion = jsonObject.getString(MyManage.column_Question);
                            String StrChoice1 = jsonObject.getString(MyManage.column_Choice1);
                            String StrChoice2 = jsonObject.getString(MyManage.column_Choice2);
                            String StrChoice3 = jsonObject.getString(MyManage.column_Choice3);
                            String StrChoice4 = jsonObject.getString(MyManage.column_Choice4);
                            String StrAnswer = jsonObject.getString(MyManage.column_Answer);

                            myManage.addQuestion(StrQuestion,
                                    StrChoice1, StrChoice2, StrChoice3, StrChoice4, StrAnswer);
                            break;
                    }
                }

            } catch (Exception e) {
                Log.d("test", "Update ==>" + e.toString());

            }

            intTABLE += 1;
        }//While


    }// synJSONtoSQLite

    private void deleteAllSQLite() {
        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase(MyOponHelper.database_name,
                MODE_PRIVATE, null);
        sqLiteDatabase.delete(MyManage.user_table, null, null);
        sqLiteDatabase.delete(MyManage.question_table, null, null);
    }

    private void tester() {
        myManage.addValueToSQLite("user", "pass", "name");
        myManage.addQuestion("คำถาม", "ก", "ข", "ค", "ง", "1");
    }

    private void buttonController() {

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Receive Value From Edit Text
                userString = userEditText.getText().toString().trim();
                passwordString = passwordEditText.getText().toString().trim();

                //Check Space
                if (userString.equals("") || passwordString.equals("")) {
                    //Have Space
                    MyAlertDialog myAlertDialog = new MyAlertDialog();
                    myAlertDialog.myDialog(MainActivity.this,
                            "มีช่องว่าง", "กรุณากรอกทุกช่อง คะ");

                } else {
                    //No Space
                    chedUser();
                }

            }   // onClick
        });

    }   // buttonController

    private void chedUser() {


        try {
            String[] resultStrings = myManage.searchUser(userString);
            if (passwordString.equals(resultStrings[2])) {
                //password True
                Toast.makeText(MainActivity.this, "ยินดีต้อนรับ" + resultStrings[3],
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, TesterActivity.class);
                intent.putExtra("Student", resultStrings[3]);
                startActivity(intent);

            } else {
                //Password False
                MyAlertDialog myAlertDialog= new MyAlertDialog();
                myAlertDialog.myDialog(MainActivity.this, "Password False",
                    "Please Try again Password False");



            }

        } catch (Exception e) {
            MyAlertDialog myAlertDialog = new MyAlertDialog();
            myAlertDialog.myDialog(MainActivity.this, "ไม่มี User นี่",
                    "ไม่มี " + userString + "ในฐานข้อมูลของเรา");
        }
    }//  checkUser

    private void bindWidget() {

        userEditText = (EditText) findViewById(R.id.editText);
        passwordEditText = (EditText) findViewById(R.id.editText2);
        loginButton = (Button) findViewById(R.id.button);

    }

}   // Main Class นี่คือ คลาสหลัก
