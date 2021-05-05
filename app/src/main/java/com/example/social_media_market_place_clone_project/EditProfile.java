package com.example.social_media_market_place_clone_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;

public class EditProfile extends AppCompatActivity {
    View back;
    Button save;
    EditText name, biog, interests, location;
    ImageView profilePicture;
    String imageUrl;
    int numPics = 1;
    String filePath;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Buttons and edit texts on the settings page
        back = (View) findViewById(R.id.back_button);
        save = (Button) findViewById(R.id.save_button);
        name = (EditText) findViewById(R.id.editTextEditProfileName);
        biog = (EditText) findViewById(R.id.editTextBio);
        interests = (EditText) findViewById(R.id.editTextInterests);
        location = (EditText) findViewById(R.id.editTextLocation);

        profilePicture = (ImageView) findViewById(R.id.sign_up_profile_picture_background);

        // What happens when the buttons are clicked
        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, numPics);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doBack();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // upper part is for profile picture;
                    if(imageUrl!=null){
                        processImage();
                    }


                    doSave();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void processImage() throws JSONException {
        String updatedImageUrl=addChar(imageUrl, 's', 4);
        SessionManager session = new SessionManager(EditProfile.this);
        session.checkLogin();
        HashMap<String, String> currentUser = session.getUserDetails();
        AsyncNetwork request = new AsyncNetwork();
        String fullname = currentUser.get("FULLNAME");
        String email = currentUser.get("EMAIL");
        String birthday = currentUser.get("BIRTHDAY");
        String sexuality = currentUser.get("SEXUALITY");
        String gender = currentUser.get("GENDER");
        String bio = currentUser.get("BIO");

        String link="https://lamp.ms.wits.ac.za/home/s1851427/WDAUpPicture.php";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(link).newBuilder();
        urlBuilder.addQueryParameter("username","vhugala");
        urlBuilder.addQueryParameter("profile_picture",updatedImageUrl);


        String url = urlBuilder.build().toString();
        request.execute(url);

        while(request.Result.equals("Waiting")){
            Toast.makeText(EditProfile.this,"Loading",Toast.LENGTH_SHORT).show();
        }


        // Request is finished
        JSONObject wholeString = new JSONObject(request.Result); // Read the whole string


        if(wholeString.getString("success").equals("0")){
            Toast.makeText(EditProfile.this,wholeString.getString("message"),Toast.LENGTH_SHORT).show();
        }else{
            session.createSession(email,fullname,birthday,gender,sexuality, bio, updatedImageUrl);
            Intent intentSignIn = new Intent(EditProfile.this, HomeView.class);
            intentSignIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intentSignIn);
        }
    }
    public void doBack() {
        Intent intent = new Intent(EditProfile.this, Settings.class);
        startActivity(intent);
    }
    public String addChar(String str, char ch, int position) {
        int len = str.length();
        char[] updatedArr = new char[len + 1];
        str.getChars(0, position, updatedArr, 0);
        updatedArr[position] = ch;
        str.getChars(position, len, updatedArr, position + 1);
        return new String(updatedArr);
    }

    public void doSave() throws JSONException {
        Intent intent = new Intent(EditProfile.this, Settings.class);
        startActivity(intent);

        if(!name.getText().toString().equals("")){
            //then send request to change name
            SessionManager sessionManager = new SessionManager(EditProfile.this);
            sessionManager.checkLogin();
            HashMap<String, String> currentUser = sessionManager.getUserDetails();
            AsyncNetwork request = new AsyncNetwork();

            //get values on current session then delete it
            String fullname = currentUser.get("FULLNAME");
            String email = currentUser.get("EMAIL");
            String birthday = currentUser.get("BIRTHDAY");
            String sexuality = currentUser.get("SEXUALITY");
            String gender = currentUser.get("GENDER");
            String bio = currentUser.get("BIO");
            String imageUrl = currentUser.get("PROFILE_PICTURE");
            String link="https://lamp.ms.wits.ac.za/home/s1851427/WDAUpName.php";
            HttpUrl.Builder urlBuilder = HttpUrl.parse(link).newBuilder();
            urlBuilder.addQueryParameter("name",name.getText().toString());
            urlBuilder.addQueryParameter("username",email);


            String url = urlBuilder.build().toString();
            request.execute(url);

            while(request.Result.equals("Waiting")){
                Toast.makeText(EditProfile.this,"Loading",Toast.LENGTH_SHORT).show();
            }


            // Request is finished
            JSONObject wholeString = new JSONObject(request.Result); // Read the whole string
            //JSONArray jsonArray = new JSONArray(wholeString.getJSONArray("login").toString()); // extract the login credentials array

            //JSONObject userCredentials = jsonArray.getJSONObject(0);

            if(wholeString.getString("success").equals("0")){
                Toast.makeText(EditProfile.this,wholeString.getString("message"),Toast.LENGTH_SHORT).show();
            }else{


                sessionManager.createSession(email, name.getText().toString(),birthday,gender,sexuality,bio,imageUrl);
                Intent intentSignIn = new Intent(EditProfile.this, Profile.class);
                intentSignIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentSignIn);
            }

        }
        if(!biog.getText().toString().equals("")){
            //then send request to change name
            SessionManager sessionManager = new SessionManager(EditProfile.this);
            sessionManager.checkLogin();
            HashMap<String, String> currentUser = sessionManager.getUserDetails();
            AsyncNetwork request = new AsyncNetwork();

            //get values on current session then delete it
            String fullname = currentUser.get("FULLNAME");
            String email = currentUser.get("EMAIL");
            String birthday = currentUser.get("BIRTHDAY");
            String sexuality = currentUser.get("SEXUALITY");
            String gender = currentUser.get("GENDER");
            String bio = currentUser.get("BIO");
            String imageUrl = currentUser.get("PROFILE_PICTURE");
            String link="https://lamp.ms.wits.ac.za/home/s1851427/WDAUpBio.php";
            HttpUrl.Builder urlBuilder = HttpUrl.parse(link).newBuilder();
            urlBuilder.addQueryParameter("biography", biog.getText().toString());
            urlBuilder.addQueryParameter("username",email);


            String url = urlBuilder.build().toString();
            request.execute(url);

            while(request.Result.equals("Waiting")){
                Toast.makeText(EditProfile.this,"Loading",Toast.LENGTH_SHORT).show();
            }


            // Request is finished
            JSONObject wholeString = new JSONObject(request.Result); // Read the whole string

            if(wholeString.getString("success").equals("0")){
                Toast.makeText(EditProfile.this,wholeString.getString("message"),Toast.LENGTH_SHORT).show();
            }else{


                sessionManager.createSession(email, fullname,birthday,gender,sexuality,biog.getText().toString(),imageUrl);
                Intent intentSignIn = new Intent(EditProfile.this, Profile.class);
                intentSignIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentSignIn);
            }

        }




    }
    private void requestPermission(){
        if(ContextCompat.checkSelfPermission
                (EditProfile.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
        ){
        } else {
            ActivityCompat.requestPermissions(
                    EditProfile.this,
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
            filePath = getRealPathFromUri(uri, EditProfile.this);
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
                Toast.makeText(EditProfile.this,"starting",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onProgress(String requestId, long bytes, long totalBytes) {
//                uploading
                System.out.println("in progress...");
                Toast.makeText(EditProfile.this,"processing image...",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(String requestId, Map resultData) {
//                TODO: save this string url in database and use it in imageViews (Vhugala already knows how to do this)
                String url = resultData.get("url").toString();
                System.out.println(url);
                imageUrl = url;
                Toast.makeText(EditProfile.this,url,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(String requestId, ErrorInfo error) {
//               error.getDescription()
                System.out.println(error.getDescription());
                Toast.makeText(EditProfile.this,error.getDescription(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onReschedule(String requestId, ErrorInfo error) {
                System.out.println(error.getDescription());
                Toast.makeText(EditProfile.this,error.getDescription(),Toast.LENGTH_LONG).show();
            }
        }).dispatch();
    }

}