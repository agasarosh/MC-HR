package com.example.saroshaga.hrapp;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class UserActivity extends AppCompatActivity {

    private String current_user;
    private ListView l1;
    private TextView t;
    private SQLiteDatabase db;
    private Cursor c;
    private  String notices=" ";
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        try {
            db = openOrCreateDatabase("employee", MODE_PRIVATE, null);
            current_user = getIntent().getStringExtra("name");

            Toast.makeText(getApplicationContext(),"Current user: "+current_user,Toast.LENGTH_LONG).show();
        }catch(SQLException e)
        {
            e.printStackTrace();
        }
        t = (TextView) findViewById(R.id.display);
        String[] menu_items = {"Notices", "Attendance", "Salary"};

        ArrayAdapter<String> a = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu_items);
        l1 = (ListView) findViewById(R.id.l1);
        l1.setAdapter(a);

        l1.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) throws SQLException {
                try {
                    switch (position) {
                        case 0:
                            c = db.rawQuery("select *from notices;", null);
                            if (c.moveToFirst()) {
                                do {
                                    notices = notices + c.getString(0) + "\n";
                                } while (c.moveToNext());
                            }

                            t.setText(notices);
                            notices = " ";
                            break;

                        case 1:
                            c = db.rawQuery("select * from details where username= '" + current_user + "';", null);
                            c.moveToFirst();
                            String att = c.getString(1);
                            t.setText("Attendance: " + att + "/30 days");
                            break;

                        case 2:
                            c = db.rawQuery("select * from details where username= '" + current_user + "';", null);
                            c.moveToFirst();
                            String sal = c.getString(2);
                            t.setText("Salary: " + sal);
                            break;
                    }
                }catch(SQLException e)
                {
                    e.printStackTrace();
                }

            }
        });





    }

    public void back(View v)
    {
        finish();
    }
}
