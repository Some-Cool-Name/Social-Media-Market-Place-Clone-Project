package com.example.social_media_market_place_clone_project;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
                loginChat();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public void doSignIn() throws JSONException {
        SessionManager session = new SessionManager(SignIn.this);
        DatabaseQueries queryLogin = new DatabaseQueries();

        // Request is finished
        JSONObject wholeString = new JSONObject(queryLogin.loginUser(email.getText().toString(), password.getText().toString())); // Read the whole string

        if(wholeString.getString("success").equals("0")){
            Toast.makeText(SignIn.this,wholeString.getString("message"),Toast.LENGTH_SHORT).show();
        }else{
            // moved because of index out of bounds error
            JSONArray jsonArray = new JSONArray(wholeString.getJSONArray("login").toString()); // extract the login credentials array
            JSONObject userCredentials = jsonArray.getJSONObject(0);

            session.createSession(userCredentials.getString("username"),userCredentials.getString("name"),userCredentials.getString("Birthday"),userCredentials.getString("gender"),userCredentials.getString("Sexuality"), userCredentials.getString("bio"), userCredentials.getString("profile_picture"));
            Intent intentSignIn = new Intent(SignIn.this, HomeView.class);
            intentSignIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intentSignIn);
        }
    }
    public void loginChat(){
        String url = "https://datingapp-d1e37-default-rtdb.firebaseio.com/.json";
        final ProgressDialog pd = new ProgressDialog(SignIn.this);
        pd.setMessage("Loading...");
        pd.show();
        pd.dismiss();
        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            if (response.equals("null")) {
                Toast.makeText(SignIn.this, "user not found", Toast.LENGTH_LONG).show();
            } else {
                try {
                    JSONObject obj = new JSONObject(response);

                    if (!obj.has(email.getText().toString())) {
                        //Toast.makeText(SignIn.this, "user not found", Toast.LENGTH_LONG).show();

                    } else if (obj.getJSONObject(email.getText().toString()).getString("password").equals("pass123")) {
                        UserDetails.username = email.getText().toString();
                        UserDetails.password = password.getText().toString();
                       // startActivity(new Intent(SignIn.this, Users.class));
                    } else {
                        Toast.makeText(SignIn.this, "incorrect password", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            pd.dismiss();
        }, volleyError -> {
            System.out.println("" + volleyError);
            pd.dismiss();
        });

        RequestQueue rQueue = Volley.newRequestQueue(SignIn.this);
        rQueue.add(request);
    }
}