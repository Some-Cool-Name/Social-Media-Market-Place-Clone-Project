package com.example.social_media_market_place_clone_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
                loginChat();
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
            error.setText("wrong credentials");

            //return;

        } else {
            Toast.makeText(SignIn.this, "correct", Toast.LENGTH_SHORT).show();
            session.createSession(userCredentials.getString("username"), userCredentials.getString("name"), userCredentials.getString("Birthday"), userCredentials.getString("gender"), userCredentials.getString("Sexuality"), userCredentials.getString("bio"), userCredentials.getString("profile_picture"), userCredentials.getString("location"));
            Intent intentSignIn = new Intent(SignIn.this, HomeView.class);
            intentSignIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intentSignIn);
        }
        // Toast.makeText(getBaseContext(),"Wrong login details",Toast.LENGTH_SHORT).show();
        // System.out.print("Wrong");
        //error.setText("H");


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
                        Toast.makeText(SignIn.this, "user not found", Toast.LENGTH_LONG).show();

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