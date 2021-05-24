package com.example.social_media_market_place_clone_project;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
    String email, password;
    String imageUrl;
    String date, dateURLformat; // DD MONTH YYYY
    String genderValue, preferenceValue;
    String[] genderList = new String[] {"Male", "Female"};
    String filePath;
    ImageHandler imageHandler;
    StringHandler stringHandler;
    CheckBox consent;

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
        consent = findViewById(R.id.checkBox);
        // **************************************************************

//        declare image handler
        imageHandler = new ImageHandler(this);
        stringHandler = new StringHandler();

        // Switch activities when buttons are pressed
        register.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                DataValidation validate = new DataValidation();
                AgeCalculator ageCalculator = new AgeCalculator();
                String isValid =validate.validateSignuUp2(name.getText().toString());
               if(isValid.equals("Valid") &&dateURLformat!= null&& Integer.parseInt(ageCalculator.calculateAge(dateURLformat))>=18 && consent.isChecked()){
                    SessionManager sessionManager = new SessionManager(SignUp2.this);
                    SignUp signUp = new SignUp();
                    AsyncNetwork request  = new AsyncNetwork();
                     email= signUp.emailExport;
                     password = signUp.passwordExport;
                    String link ="https://lamp.ms.wits.ac.za/home/s1851427/WDAReg.php";
                    HttpUrl.Builder urlBuilder = HttpUrl.parse(link).newBuilder();
                    urlBuilder.addQueryParameter("username",email);
                    urlBuilder.addQueryParameter("password",password);
                    urlBuilder.addQueryParameter("name", name.getText().toString());
                    urlBuilder.addQueryParameter("gender",genderValue);
                    urlBuilder.addQueryParameter("birthday",dateURLformat);
                    urlBuilder.addQueryParameter("sexuality",preferenceValue);
                    urlBuilder.addQueryParameter("location","Braamfontein");
                   urlBuilder.addQueryParameter("bio",bio.getText().toString());

                   String updatedImageUrl=stringHandler.addChar(imageHandler.imageUrl, 's', 4); // should add an s to image url


                   urlBuilder.addQueryParameter("profile_picture",updatedImageUrl);

                    String url = urlBuilder.build().toString();
                    request.execute(url);

                    // if request result is success go ahead and create session and call do register
                   while(request.Result.equals("Waiting")){
                       Toast.makeText(SignUp2.this,"Loading",Toast.LENGTH_SHORT).show();
                   }
                   JSONObject wholeString = null; // Read the whole string
                   try {
                       wholeString = new JSONObject(request.Result);
                       if(wholeString.getString("message").equals("success")){
                           sessionManager.createSession(email,name.getText().toString(),dateURLformat,genderValue,preferenceValue,bio.getText().toString(),updatedImageUrl);

                           // if done change ui'
                           doRegister();
                           loginChat();
                       }
                       else {
                           //set ui to signin1
                           AlertDialog.Builder builder = new AlertDialog.Builder(SignUp2.this);
                           builder.setMessage("User already exists change your email")
                                   .setPositiveButton("Change Email", new DialogInterface.OnClickListener() {
                                       @Override
                                       public void onClick(DialogInterface dialog, int which) {
                                           incorrect();
                                       }
                                   });

                           AlertDialog dialog = builder.create();
                           dialog.show();
                           //incorrect();

                       }
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }



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
                        CalendarMonth calendarMonth = new CalendarMonth();
                        month = month + 1;
                        String monthString = calendarMonth.getMonth(month);
                        date = stringHandler.getDateString(day, monthString, year);
                        dateURLformat = calendarMonth.getDateFormatURL(day, month, year);
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
                requestPermission();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, numPics);
            }
        });
        // **************************************************************
    }

    public void doRegister(){
        Intent intentRegister = new Intent(SignUp2.this, Interest.class);
        intentRegister.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentRegister);
    }

//    requests permission to read internal storage
    private void requestPermission(){
        if(ContextCompat.checkSelfPermission
                (SignUp2.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
        ){
        } else {
            ActivityCompat.requestPermissions(
                    SignUp2.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1
            );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == numPics && resultCode == RESULT_OK && data != null && data.getData() != null){
            uri = data.getData();

            Bitmap bitmap = null;
            //get the image's file location
            filePath = imageHandler.getRealPathFromUri(uri, SignUp2.this);
            imageHandler.uploadToCloudinary(filePath);
            imageUrl = imageHandler.imageUrl;


            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                profilePicture.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void incorrect(){
        Intent viewProfile = new Intent(this, SignUp.class);
        viewProfile.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(viewProfile);
    }
    public void loginChat(){
        String url = "https://datingapp-d1e37-default-rtdb.firebaseio.com/.json";
        final ProgressDialog pd = new ProgressDialog(SignUp2.this);
        pd.setMessage("Loading...");
        pd.show();
        pd.dismiss();

        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            if (response.equals("null")) {
                Toast.makeText(SignUp2.this, "user not found", Toast.LENGTH_LONG).show();
            } else {
                try {
                    JSONObject obj = new JSONObject(response);

                    if (!obj.has(email)) {
                        Toast.makeText(SignUp2.this, "user not found", Toast.LENGTH_LONG).show();

                    } else if (obj.getJSONObject(email).getString("password").equals(password)) {
                        UserDetails.username = email;
                        UserDetails.password = password;
                        //startActivity(new Intent(SignUp2.this, Users.class));
                    } else {
                        Toast.makeText(SignUp2.this, "incorrect password", Toast.LENGTH_LONG).show();
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

        RequestQueue rQueue = Volley.newRequestQueue(SignUp2.this);
        rQueue.add(request);
    }
}
