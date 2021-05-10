package com.example.social_media_market_place_clone_project;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.HttpUrl;

public class SignIn extends AppCompatActivity {
    EditText email, password;
    Button signIn;
    TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        signIn = (Button) findViewById(R.id.sign_in_button);
        email = findViewById(R.id.editTextSignInEmailAddress);
        password = findViewById(R.id.editTextSignInPassword);
        error = findViewById(R.id.textError);
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
        if(email.getText().toString().equals("")){
            email.setError("Username can't be blank");
            return;
        }
        if(password.getText().toString().equals("")){
            password.setError("Password can't be blank");
            return;
        }
        //else {
            String link = "https://lamp.ms.wits.ac.za/home/s1851427/WDAGet.php";
            HttpUrl.Builder urlBuilder = HttpUrl.parse(link).newBuilder();
            urlBuilder.addQueryParameter("username", email.getText().toString());
            urlBuilder.addQueryParameter("password", password.getText().toString());
            String url = urlBuilder.build().toString();
            request.execute(url);

            while (request.Result.equals("Waiting")) {
                //Toast.makeText(SignIn.this,"Loading",Toast.LENGTH_SHORT).show();
                System.out.print("waiting");
                //password.setError("Wrong things");

            }


            // Request is finished
            JSONObject wholeString = new JSONObject(request.Result); // Read the whole string
            JSONArray jsonArray = new JSONArray(wholeString.getJSONArray("login").toString()); // extract the login credentials array
            JSONObject userCredentials = null;
            try {
                userCredentials = jsonArray.getJSONObject(0);
            } catch (Exception xception) {
                xception.printStackTrace();
            }
           // Toast.makeText(getBaseContext(), wholeString.getString("success"), Toast.LENGTH_SHORT).show();

            if (wholeString.getString("success").equals("0")) {
                System.out.print("");
                error.setText("wrong cred");

                //return;

            } else {
                Toast.makeText(SignIn.this, "correct", Toast.LENGTH_SHORT).show();
                session.createSession(userCredentials.getString("username"), userCredentials.getString("name"), userCredentials.getString("Birthday"), userCredentials.getString("gender"), userCredentials.getString("Sexuality"), userCredentials.getString("bio"), userCredentials.getString("profile_picture"));
                Intent intentSignIn = new Intent(SignIn.this, HomeView.class);
                intentSignIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentSignIn);
            }
            // Toast.makeText(getBaseContext(),"Wrong login details",Toast.LENGTH_SHORT).show();
            // System.out.print("Wrong");
            //error.setText("H");

        }
    //}
}