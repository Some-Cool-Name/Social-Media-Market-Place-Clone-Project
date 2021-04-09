package com.example.social_media_market_place_clone_project;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.HttpUrl;

public class SignIn extends AppCompatActivity {
    EditText email, password;
    Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signIn = (Button) findViewById(R.id.sign_in_button);
        email = findViewById(R.id.editTextSignInEmailAddress);
        password = findViewById(R.id.editTextSignInPassword);

        signIn.setOnClickListener(v -> {
            try {
                doSignIn();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public void doSignIn() throws JSONException {
        SessionManager session = new SessionManager(SignIn.this);
        AsyncNetwork request = new AsyncNetwork();

        String link="https://lamp.ms.wits.ac.za/home/s1851427/WDAGet.php";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(link).newBuilder();
        urlBuilder.addQueryParameter("username",email.getText().toString());
        urlBuilder.addQueryParameter("password",password.getText().toString());
        String url = urlBuilder.build().toString();
        request.execute(url);

        while(request.Result.equals("Waiting")){
            Toast.makeText(SignIn.this,"Loading",Toast.LENGTH_SHORT).show();
        }

        // Request is finished
        JSONObject wholeString = new JSONObject(request.Result); // Read the whole string
        JSONArray jsonArray = new JSONArray(wholeString.getJSONArray("login").toString()); // extract the login credentials array
        JSONObject userCredentials = jsonArray.getJSONObject(0);

        if(wholeString.getString("success").equals("0")){
            Toast.makeText(SignIn.this,wholeString.getString("message"),Toast.LENGTH_SHORT).show();
        }else{
            session.createSession(userCredentials.getString("username"),userCredentials.getString("name"),userCredentials.getString("Birthday"),userCredentials.getString("gender"),userCredentials.getString("Sexuality"));
            Intent intentSignIn = new Intent(SignIn.this, Profile.class);
            intentSignIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intentSignIn);
        }
    }
}