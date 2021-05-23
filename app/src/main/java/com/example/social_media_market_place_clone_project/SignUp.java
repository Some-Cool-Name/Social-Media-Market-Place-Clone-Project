package com.example.social_media_market_place_clone_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class SignUp extends AppCompatActivity {
    Button next;
    EditText email, password, confirmPassword;
    public static  String emailExport, passwordExport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Buttons and Edit Texts
        Firebase.setAndroidContext(this);
        next = (Button) findViewById(R.id.sign_up_next_button);
        email = (EditText) findViewById(R.id.editTextSignUpEmail);
        password = (EditText) findViewById(R.id.editTextSignUpPassword);
        confirmPassword = (EditText) findViewById(R.id.editTextSignUpConfirmPassword);
        // **************************************************************

        // Switch activities when buttons are pressed
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataValidation validate = new DataValidation();
                String isValid = validate.validateSignUp1(email.getText().toString(),password.getText().toString(),
                        confirmPassword.getText().toString());
                if(isValid.equals("Valid")){
                    emailExport = email.getText().toString();
                    passwordExport= password.getText().toString();
                    doNext();
                    chatRegister();
                }else{
                    Toast.makeText(SignUp.this,isValid,Toast.LENGTH_SHORT).show();
                }


            }
        });
        // **************************************************************
    }

    public void doNext(){
        Intent intentNext = new Intent(SignUp.this, Interest.class);
        startActivity(intentNext);
    }

    public void chatRegister(){
        String url = "https://datingapp-d1e37-default-rtdb.firebaseio.com/.json";
        final ProgressDialog pd = new ProgressDialog(SignUp.this);
        pd.setMessage("Loading...");
        pd.show();

        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            Firebase reference = new Firebase("https://datingapp-d1e37-default-rtdb.firebaseio.com/");

            if (response.equals("null")) {
                reference.child(emailExport).child("password").setValue("pass123");
                Toast.makeText(SignUp.this, "registration successful", Toast.LENGTH_LONG).show();
            } else {
                try {
                    JSONObject obj = new JSONObject(response);

                    if (!obj.has(emailExport)) {
                        reference.child(emailExport).child("password").setValue("pass123");
                        Toast.makeText(SignUp.this, "registration successful", Toast.LENGTH_LONG).show();

                        //startActivity(new Intent(getApplicationContext(), Login.class));
                        //finish();
                    } else {
                        Toast.makeText(SignUp.this, "username already exists", Toast.LENGTH_LONG).show();

                        // reset focus
                        //resetDataAndFocus();
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
        RequestQueue rQueue = Volley.newRequestQueue(SignUp.this);
        rQueue.add(request);
    }
}