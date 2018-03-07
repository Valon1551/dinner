package com.jolameva.app.runningdinner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.jolameva.app.runningdinner.fbauth.AuthRunningActivity;
import com.jolameva.app.runningdinner.profile.ProfileActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Variablen
    private static final String TAG = "SignedInActivity";
    private static final String EXTRA_IDP_RESPONSE = "extra_idp_response";
    private static final String EXTRA_SIGNED_IN_CONFIG = "extra_signed_in_config";

    // IdpResponse beinhaltet den Token und das Secret
    private IdpResponse mIdpResponse;
    private MainActivity.SignedInConfig mSignedInConfig;

    Button btn_Start, btn_RateProfile, btn_GotoLogin;

    public static Intent createIntent(
            Context context,
            IdpResponse idpResponse,
            MainActivity.SignedInConfig signedInConfig) {

        Intent startIntent = new Intent();
        if (idpResponse != null) {
            startIntent.putExtra(EXTRA_IDP_RESPONSE, idpResponse);
        }

        return startIntent.setClass(context, MainActivity.class)
                .putExtra(EXTRA_SIGNED_IN_CONFIG, signedInConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            startActivity(AuthRunningActivity.createIntent(this));
            finish();
            return;
        }

        // Holt sich die IDP Response. Die IDP Response beinhaltet den Token vom Loginprovider
        mIdpResponse = getIntent().getParcelableExtra(EXTRA_IDP_RESPONSE);
        mSignedInConfig = getIntent().getParcelableExtra(EXTRA_SIGNED_IN_CONFIG);

        // Layout setzen
        setContentView(R.layout.activity_main);
        Toolbar main_toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(main_toolbar);

        // Holt sich User Daten und speichert sie in die DB
        retrieveUserData(currentUser);

        // Buttons initialisieren
        btn_Start = findViewById(R.id.btn_Start);
        btn_RateProfile = findViewById(R.id.btn_rateProfile);

        // Startet ActivityMatch
        btn_Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , ActivityMatch.class);
                startActivity(intent);
            }
        });

        // Startet ActivityRating
        btn_RateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ActivityRating.class);
                startActivity(intent);
            }
        });
    }

    /**
     * retriveUserData überprüft ob der User über Facebook oder Google eingeloggt ist und holt sich dann
     * zusätzliche Infos wie zum Beispiel Geschlecht
     * @param currentUser ist der eingeloggte Firebase User
     */
    private void retrieveUserData(final FirebaseUser currentUser) {
        // Unser Runningdinner User Objekt
        final RDUser rdUser = new RDUser();

        // rdUser Objekt bekommt den Namen, die Email, und die FotoUrl vom FirebaseUser. Das Foto ist in 100x100 also sehr klein
        // Weitere Attribute außer noch die FirebaseID sind im FirebaseUser nicht vorhanden
        rdUser.setName(currentUser.getDisplayName());
        rdUser.setEmail(currentUser.getEmail());
        rdUser.setPicture(currentUser.getPhotoUrl());

        String photoUrl = currentUser.getPhotoUrl().toString();

        // Überprüft ob der angemeldete User sich über facebook oder über google angemeldet hat
        for (UserInfo profile : currentUser.getProviderData()){
            if (profile.getProviderId().equals("facebook.com")){

                String facebookUserId = profile.getUid();
                // Übergibt dem rdUser die FacebookUserID
                rdUser.setFacebookId(facebookUserId);
                // Übergibt dem rdUser den Providernamen mit dem der User sich eingeloggt hat
                rdUser.setSignInProvider(profile.getProviderId()); // facebook.com | google.com

                // Code für größeres Profilbild. Alternativ kann hier '?type=small|medium|large' verwendet werden
                photoUrl = "https://graph.facebook.com/" + facebookUserId + "/picture?height=500";

                // Prüft ob AccessToken vorhanden
                if (AccessToken.getCurrentAccessToken()!= null){

                    Log.d("AccessToken","Facebook Token erhalten"+AccessToken.getCurrentAccessToken());

                    // GraphRequest gehört zum Facebook SDK und wird hier verwendet um "gender" auszulesen
                    GraphRequest request = GraphRequest.newMeRequest(
                            AccessToken.getCurrentAccessToken(),
                            new GraphRequest.GraphJSONObjectCallback() {
                                @Override
                                public void onCompleted(JSONObject object, GraphResponse response) {
                                    // Versuch Gender auszulesen
                                    try {
                                        String gender = object.getString("gender");
                                        // Übergibt dem rdUser das gender
                                        rdUser.setGender(gender);
                                        // TODO: Statt saveUser sollte ein anderer Weg gefunden werden um gender zu speichern
                                        rdUser.saveUser();

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                    Bundle parameters = new Bundle();
                    // Hier wird bestimmt auf was wir über das Facebook SDK Zugriff haben wollen
                    // firstname und lastname sind noch nicht in der rduser Klasse können wir aber später auch hinzufügen
                    parameters.putString("fields", "id,name,link,email,first_name,last_name,gender");
                    request.setParameters(parameters);
                    request.executeAsync();
                }


            } else if (profile.getProviderId().equals("google.com")){
                // Mit Google eingeloggt, TODO: code für google einfügen um Gender und größeres Profilbild zu bekommen

            }
        }
//        rdUser.setGender(gender);
        rdUser.setLargePicture(photoUrl);
        rdUser.setFirebaseId(currentUser.getUid());
        // Hier wird der RDUSER gespeichert und in die Datenbank eingetragen. saveUser() wird hier
        // nochmal verwendet nachdem es bereits in onCompleted verwendet wurde
        rdUser.saveUser();
    }

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

                // Startet die Profile Activity
                Intent intentProfile = new Intent(this, ProfileActivity.class);
                startActivity(intentProfile);
                return true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Kp was das macht.. vielleicht braucht man das auch gar nicht.. Ich glaub das gehört zum Smartlock..
     *
     */
    public static final class SignedInConfig implements Parcelable {
        List<AuthUI.IdpConfig> providerInfo;
        boolean isCredentialSelectorEnabled;
        boolean isHintSelectorEnabled;

        public SignedInConfig(List<AuthUI.IdpConfig> providerInfo,
                              boolean isCredentialSelectorEnabled,
                              boolean isHintSelectorEnabled) {

            this.providerInfo = providerInfo;
            this.isCredentialSelectorEnabled = isCredentialSelectorEnabled;
            this.isHintSelectorEnabled = isHintSelectorEnabled;
        }

        SignedInConfig(Parcel in) {
            providerInfo = new ArrayList<>();
            in.readList(providerInfo, AuthUI.IdpConfig.class.getClassLoader());
            isCredentialSelectorEnabled = in.readInt() != 0;
            isHintSelectorEnabled = in.readInt() != 0;
        }

        public static final Creator<MainActivity.SignedInConfig> CREATOR = new Creator<MainActivity.SignedInConfig>() {
            @Override
            public MainActivity.SignedInConfig createFromParcel(Parcel in) {
                return new MainActivity.SignedInConfig(in);
            }

            @Override
            public MainActivity.SignedInConfig[] newArray(int size) {
                return new MainActivity.SignedInConfig[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeList(providerInfo);
            dest.writeInt(isCredentialSelectorEnabled ? 1 : 0);
            dest.writeInt(isHintSelectorEnabled ? 1 : 0);
        }
    }
}