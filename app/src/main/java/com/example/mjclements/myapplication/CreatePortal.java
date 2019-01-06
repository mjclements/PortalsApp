package com.example.mjclements.myapplication;
import com.example.mjclements.myapplication.Profile;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mjclements.myapplication.DataStorageWrapper;
import com.example.mjclements.myapplication.R;
import com.example.mjclements.myapplication.User;

import java.util.UUID;

public class CreatePortal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_portal);
        //Button to take the user back to the main page and create portal
        Button b1 = (Button) findViewById(R.id.create);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getIntent();
                String s = i.getStringExtra("email");
                Bundle bundle = i.getExtras();

                // 5. get status value from bundle
                String status = bundle.getString("send");
                DataStorageWrapper db = new DataStorageWrapper( getApplicationContext() );
                UUID userName = UUID.fromString(status);
                User user = db.get_User(userName);


                //User text to assign a name to the new portal
                AutoCompleteTextView portalNameText = (AutoCompleteTextView) findViewById(R.id.portalName);
                String portalName = portalNameText.getText().toString();

                //Instantiate portal and save to user database
                Profile newPortal = new Profile( portalName );
                db.insert_Profile(user, newPortal);
                Intent createPortal = new Intent( CreatePortal.this, PortalsHome.class );
                Bundle extras = new Bundle();
                extras.putString("status", user.getID().toString() );

                // 4. add bundle to intent
                createPortal.putExtras(extras);
                //go back to portal home
                startActivity(createPortal);

            }
        });


    }

}
