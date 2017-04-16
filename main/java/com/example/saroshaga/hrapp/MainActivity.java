package com.example.saroshaga.hrapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private EditText username,password;
    private Cursor c;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            db = openOrCreateDatabase("employee", MODE_PRIVATE, null);
            db.execSQL("create table if not exists login(username varchar,password varchar);");
            db.execSQL("insert into login values('admin','admin');");
            db.execSQL("insert into login values('jimmy','jimmy');");
            db.execSQL("create table if not exists notices(notice varchar);");
            db.execSQL("create table if not exists details(username varchar,attendance varchar,salary varchar);");
            db.execSQL("insert into details values('jimmy','8','25000');");
        }catch(SQLException e)
        {
            e.printStackTrace();
        }

    }






    public void Login(View v)
    {
        username=(EditText)findViewById(R.id.Username);
        password=(EditText)findViewById(R.id.Password);
        String pass=" ";
        try {
            c = db.rawQuery("select * from login where username= '" +username.getText().toString().trim()+"';", null);
            c.moveToFirst();
            pass = c.getString(1);
        }catch(SQLException e)
        {
            e.printStackTrace();
        }


        if(password.getText().toString().trim().equals(pass))
        {
            if(username.getText().toString().trim().equals("admin"))
            {
                Intent i=new Intent(this,adminpanel.class);
                startActivity(i);
            }
            else
            {
                Toast.makeText(getApplicationContext(),"Login successful",Toast.LENGTH_LONG).show();
                Intent i=new Intent(this,UserActivity.class);
                i.putExtra("name",username.getText().toString().trim());
                startActivity(i);
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Username and Password do not match",Toast.LENGTH_LONG).show();
        }

    }


    public void quit(View v)
    {
        getApplicationContext().deleteDatabase("employee");
        this.finishAffinity();
    }
}