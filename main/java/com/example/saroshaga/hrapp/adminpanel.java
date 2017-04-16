package com.example.saroshaga.hrapp;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class adminpanel extends AppCompatActivity {

    private SQLiteDatabase db;
    private EditText notice1;
    private EditText details;
    private TextView t;
    private Cursor c;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpanel);
        String s1="Employee names:\n ";
        t=(TextView)findViewById(R.id.display);
        try {
            db = openOrCreateDatabase("employee", MODE_PRIVATE, null);
            c=db.rawQuery("select *from details;",null);
            if(c.moveToFirst())
            {
                do {
                    s1=s1+c.getString(0)+"\n";
                }while(c.moveToNext());
            }

            t.setText(s1);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }



    public void insert_notice(View v)
    {
        notice1=(EditText)findViewById(R.id.notice);
        String msg=notice1.getText().toString();
        try {
            db.execSQL("insert into notices values('" + msg + "');");
            Toast.makeText(getApplicationContext(), "Notice issued successfully", Toast.LENGTH_LONG).show();
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void insert_details(View v)
    {
        details=(EditText)findViewById(R.id.details);
        String msg=details.getText().toString();
        String[] data=msg.split(",");
        try {
            db.execSQL("update details set attendance= '" + data[1] + "',salary= '" + data[2] + "' where username= '" + data[0] + "';");
            Toast.makeText(getApplicationContext(), "Details for " + data[0] + " updated successfully", Toast.LENGTH_LONG).show();
        }catch(SQLException e)
        {
            e.printStackTrace();

        }

    }

    public void logout(View v)
    {
        finish();
    }
}
