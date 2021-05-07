package com.example.social_media_market_place_clone_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUp2 extends AppCompatActivity {
    TextView birthday, gender, preference;
    Button register;
    EditText name, bio;
    ImageView profilePicture;
    int numPics = 1;
    Uri uri;
    String imageUrl;
    String date, dateURLformat; // DD MONTH YYYY
    String genderValue, preferenceValue;
    String[] genderList = new String[] {"Male", "Female"};
    String filePath;

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
                AgeCalculator ageCalculator = new AgeCalculator();
                String isValid =validate.validateSignuUp2(name.getText().toString());
               if(isValid.equals("Valid") && Integer.parseInt(ageCalculator.calculateAge(dateURLformat))>=18){
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
                    urlBuilder.addQueryParameter("birthday",dateURLformat);
                    urlBuilder.addQueryParameter("sexuality",preferenceValue);
                    urlBuilder.addQueryParameter("location","Braamfontein");
                   urlBuilder.addQueryParameter("bio",bio.getText().toString());

                   String updatedImageUrl=addChar(imageUrl, 's', 4); // should add an s to image url


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
                        date = day + " " + monthString + " " + year;
                        dateURLformat = calendarMonth.getDateFormatURL(day, month, year);
                        /*if(month<10){
                            if(day<10){
                                dateURLformat = "0"+day+"-"+"0"+month+"-"+year;
                            }else{
                                dateURLformat = day+"-"+"0"+month+"-"+year;
                            }
                        }else if(day<10){
                            dateURLformat = "0"+day+"-"+month+"-"+year;
                        }else{
                            dateURLformat = day+"-"+month+"-"+year;
                        }

                         */
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
        Intent intentRegister = new Intent(SignUp2.this, Profile.class);
        intentRegister.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentRegister);
    }
    public String addChar(String str, char ch, int position) {
        int len = str.length();
        char[] updatedArr = new char[len + 1];
        str.getChars(0, position, updatedArr, 0);
        updatedArr[position] = ch;
        str.getChars(position, len, updatedArr, position + 1);
        return new String(updatedArr);
    }

   /* public String getMonth(int i){
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

    */

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

//    TODO: remove the uploadToCloudinary(filePath) to when the form is submitted, only change page once you have the url of the image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == numPics && resultCode == RESULT_OK && data != null && data.getData() != null){
            uri = data.getData();

            Bitmap bitmap = null;
            //get the image's file location
            filePath = getRealPathFromUri(uri, SignUp2.this);
            uploadToCloudinary(filePath);


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

//    gets the file path of an image from the URI
    private String getRealPathFromUri(Uri imageUri, Activity activity){
        Cursor cursor = activity.getContentResolver().query(imageUri, null, null, null, null);

        if(cursor==null) {
            return imageUri.getPath();
        }else{
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

//    this method uploads image to cloud and returns it's url
//    also outputs a toast message to indicate status of upload
//    if you want to do something after successful image upload, do it on onSuccess override
    private void uploadToCloudinary(String filePath) {
        Log.d("A", "sign up uploadToCloudinary- ");

        MediaManager.get().upload(filePath).callback(new UploadCallback() {
            @Override
            public void onStart(String requestId) {
//                start
                System.out.println("starting");
                Toast.makeText(SignUp2.this,"starting",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {
//                uploading
                System.out.println("in progress...");
                Toast.makeText(SignUp2.this,"processing image...",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
//                TODO: save this string url in database and use it in imageViews (Vhugala already knows how to do this)
                String url = resultData.get("url").toString();
                System.out.println(url);
                imageUrl = url;
                Toast.makeText(SignUp2.this,url,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(String requestId, ErrorInfo error) {
//               error.getDescription()
                System.out.println(error.getDescription());
                Toast.makeText(SignUp2.this,error.getDescription(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {
                System.out.println(error.getDescription());
                Toast.makeText(SignUp2.this,error.getDescription(),Toast.LENGTH_LONG).show();
            }
        }).dispatch();
    }

    public void incorrect(){
        Intent viewProfile = new Intent(this, SignUp.class);
        viewProfile.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(viewProfile);
    }
}
