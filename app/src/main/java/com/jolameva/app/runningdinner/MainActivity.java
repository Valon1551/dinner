package com.jolameva.app.runningdinner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.jolameva.app.runningdinner.login.ActivityLogin;
import com.jolameva.app.runningdinner.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    Button btn_Start, btn_RateProfile, btn_GotoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar main_toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(main_toolbar);


        // If MainActivity is reached without the user being logged in, redirect to the Login
        // Activity wird später erst gebraucht sobald die Login Activity die Mainactivity als Launchactivity ersetzt
//        if (AccessToken.getCurrentAccessToken() == null) {
//            Intent loginIntent = new Intent(MainActivity.this, ActivityLogin.class);
//            startActivity(loginIntent);
//        }

        btn_GotoLogin = findViewById(R.id.btn_gotoLogin);
        btn_Start = findViewById(R.id.btn_Start);
        btn_RateProfile = findViewById(R.id.btn_rateProfile);

        btn_Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , ActivityMatch.class);
                startActivity(intent);
            }
        });

        btn_RateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityRating.class);
                startActivity(intent);
            }
        });

        btn_GotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MainActivity.this, ActivityLogin.class);
                startActivity(intent);
            }
        });


    }

    // Kann für später mal nützlich werden
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case RESULT_PROFILE_ACTIVITY:
//                if (resultCode == RESULT_OK) {
//                    Intent profileIntent = new Intent(MainActivity.this, ActivityProfile.class);
//                    startActivity(profileIntent);
//                }
//                break;
//            default:
//                super.onActivityResult(requestCode,resultCode, data);
//        }
//    }

    // Diese Methode holt sich die main_menu.xml und zeigt sie in der appbar an
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    /**
     * Was soll passieren wenn der Button in der Appbar geklickt wurde
     * @param item Das Item, dass in der main_menu.xml definiert wurde
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_profilesettings:
                // User chose the "Settings" item, show the app settings UI...
                Toast.makeText(this, "Appbar Button", Toast.LENGTH_SHORT).show();

                // Ruft die Settingsactivity auf
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}