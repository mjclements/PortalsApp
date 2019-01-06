package com.example.mjclements.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.content.Intent;

import com.example.mjclements.myapplication.DataStorageWrapper;
import com.example.mjclements.myapplication.R;
import com.example.mjclements.myapplication.AccountSetup;
import com.example.mjclements.myapplication.LoginActivity;

public class welcome_screen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataStorageWrapper db = new DataStorageWrapper(getApplicationContext());
        //db.dump_database();
        db.create_User_Table();
        db.create_Tag_Table();
        db.create_Scene_Table();
        db.create_Profile_Table();
        db.close();
        //SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(":memory:", null);
        //User user = new User("placeholder");
        //db.execSQL(user.Create_Profile_Table());

        setContentView(R.layout.activity_welcome_screen);

        //link from welcome page to user log in page
        findViewById(R.id.LogIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.LogIn:
                        Intent k = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(k);

                        //finish();
                        break;
                }
            }
        });
        findViewById(R.id.CreateAcct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.CreateAcct:
                        Intent k = new Intent(getApplicationContext(),AccountSetup.class);
                        startActivity(k);

                        //finish();
                        break;
                }
            }
        });


    }


}
