package com.example.mjclements.myapplication;

import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mjclements.myapplication.DataStorageWrapper;
import com.example.mjclements.myapplication.Profile;
import com.example.mjclements.myapplication.User;
import com.example.mjclements.myapplication.R;

import java.util.ArrayList;
import java.util.UUID;


public class PortalsHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portals_home);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        Intent i = getIntent();
        Bundle bundle = i.getExtras();

        // 5. get status value from bundle
        String status = bundle.getString("status");
        DataStorageWrapper db = new DataStorageWrapper( getApplicationContext() );
        UUID userName = UUID.fromString(status);
        User user = db.get_User(userName);
        ArrayList profiles = (ArrayList) user.get_Profile();
        //loop to access all user profiles and display them as buttons on the homepage
        LinearLayout layout = findViewById(R.id.buttons);
        for( int j = 0; j < profiles.size(); j++ )
        {
            Button b = new Button( this );
            Profile p = (Profile) profiles.get(j);
            b.setText( p.getName() );
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layout.addView( b, lp );
        }


        // 6. show status on Toast

            Toast.makeText(getApplicationContext(), user.getID().toString(), Toast.LENGTH_SHORT).show();

            final String status1 = status;
            Button b1 = (Button) findViewById(R.id.createPortal );
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent createPortal = new Intent( PortalsHome.this, CreatePortal.class );
                    Bundle extras = new Bundle();
                   extras.putString("send", status1 );

                    // 4. add bundle to intent
                    createPortal.putExtras(extras);
                    startActivity(createPortal);

                }
            });


    }

}
