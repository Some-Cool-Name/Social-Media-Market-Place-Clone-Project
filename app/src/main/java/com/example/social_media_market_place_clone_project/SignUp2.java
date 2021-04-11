package com.example.social_media_market_place_clone_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

import okhttp3.HttpUrl;

public class SignUp2 extends AppCompatActivity {
    TextView birthday, gender, preference;
    Button register;
    EditText name, bio;
    ImageView profilePicture;
    int numPics = 1;
    Uri uri;
    String date; // DD MONTH YYYY
    String genderValue, preferenceValue;
    String[] genderList = new String[] {"Male", "Female"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        // Buttons, Edit Texts, Text Views, Image Views and Spinners
        birthday = (TextView) findViewById(R.id.sign_up_birthday_text2);
        register = (Button) findViewById(R.id.sign_up_register_button);
        name = (EditText) findViewById(R.id.editTextSignUpName);
        bio = (EditText) findViewById(R.id.editTextSignUpBio);
        profilePicture = (ImageView) findViewById(R.id.sign_up_profile_picture_background);
        // **************************************************************

        // Switch activities when buttons are pressed
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataValidation validate = new DataValidation();
                String isValid =validate.validateSignuUp2(name.getText().toString());
               if(isValid.equals("Valid")){
                    SessionManager sessionManager = new SessionManager(SignUp2.this);
                    SignUp signUp = new SignUp();
                    AsyncNetwork request  = new AsyncNetwork();
                    String email= signUp.emailExport;
                    String password = signUp.passwordExport;
                    String link ="https://lamp.ms.wits.ac.za/home/s1851427/WDAReg.php";
                    HttpUrl.Builder urlBuilder = HttpUrl.parse(link).newBuilder();
                    urlBuilder.addQueryParameter("username",email);
                    urlBuilder.addQueryParameter("password",password);
                    urlBuilder.addQueryParameter("name", name.getText().toString());
                    urlBuilder.addQueryParameter("gender",genderValue);
                    urlBuilder.addQueryParameter("birthday",birthday.getText().toString());
                    urlBuilder.addQueryParameter("sexuality","Straight");
                    urlBuilder.addQueryParameter("location","Braamfontein");
                    String url = urlBuilder.build().toString();
                    request.execute(url);

                    // if request result is success go ahead and create session and call do register
                   sessionManager.createSession(email,name.getText().toString(),"13-01-1999","Male","Straight");

                   // if done change ui'
                   doRegister();


                }else{
                   Toast.makeText(SignUp2.this,isValid,Toast.LENGTH_SHORT).show();
                }

            }
        });
        // **************************************************************

        // Popup Menus
        // Choose gender popup menu
        gender = (TextView) findViewById(R.id.sign_up_gender_text2);
        preference = (TextView) findViewById(R.id.sign_up_preference_text2);

        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignUp2.this);
                builder.setTitle("Choose Gender");
                builder.setItems(genderList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        genderValue = genderList[which];
                        gender.setText(genderValue);
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        // Choose preference popup menu
        preference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignUp2.this);
                builder.setTitle("Select Preference");
                builder.setItems(genderList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        preferenceValue = genderList[which];
                        preference.setText(preferenceValue);
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        // **************************************************************

        // Calendar for Birthday
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        birthday.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        com.example.social_media_market_place_clone_project.SignUp2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String monthString = getMonth(month);
                        date = day + " " + monthString + " " + year;
                        birthday.setText(date);
                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });
        // **************************************************************

        // Choose Image
        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, numPics);
            }
        });
        // **************************************************************
    }

    public void doRegister(){
        Intent intentRegister = new Intent(SignUp2.this, Profile.class);
        intentRegister.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentRegister);
    }

    public String getMonth(int i){
        String m;

        switch(i){
            case 1:
                m = "January";
                break;

            case 2:
                m = "February";
                break;

            case 3:
                m = "March";
                break;

            case 4:
                m = "April";
                break;

            case 5:
                m = "May";
                break;

            case 6:
                m = "June";
                break;

            case 7:
                m = "July";
                break;

            case 8:
                m = "August";
                break;

            case 9:
                m = "September";
                break;

            case 10:
                m = "October";
                break;

            case 11:
                m = "November";
                break;

            case 12:
                m = "December";
                break;

            default:
                m = "Month";
        }

        return m;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == numPics && resultCode == RESULT_OK && data != null && data.getData() != null){
            uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                profilePicture.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}