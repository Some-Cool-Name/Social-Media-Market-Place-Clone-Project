package com.example.social_media_market_place_clone_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.HttpUrl;

public class EditProfile extends AppCompatActivity {
    View back;
    Button save;
    EditText name, biog, location;
    ImageView profilePicture;
    String imageUrl;
    int numPics = 1;
    String filePath;
    Uri uri;
    ImageHandler imageHandler;
    StringHandler stringHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Buttons and edit texts on the settings page
        back = (View) findViewById(R.id.back_button);
        save = (Button) findViewById(R.id.save_button);
        name = (EditText) findViewById(R.id.editTextEditProfileName);
        biog = (EditText) findViewById(R.id.editTextBio);
        location = (EditText) findViewById(R.id.editTextLocation);

        profilePicture = (ImageView) findViewById(R.id.sign_up_profile_picture_background);

        // helper classes
        imageHandler = new ImageHandler(this);
        stringHandler = new StringHandler();

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
                    if (imageUrl != null) {
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
        String updatedImageUrl = stringHandler.addChar(imageUrl, 's', 4);
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
        String location =currentUser.get("LOCATION");
        String link = "https://lamp.ms.wits.ac.za/home/s1851427/WDAUpPicture.php";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(link).newBuilder();
        urlBuilder.addQueryParameter("username", email);
        urlBuilder.addQueryParameter("profile_picture", updatedImageUrl);


        String url = urlBuilder.build().toString();
        request.execute(url);

        while (request.Result.equals("Waiting")) {
            Toast.makeText(EditProfile.this, "Loading", Toast.LENGTH_SHORT).show();
        }


        // Request is finished
        JSONObject wholeString = new JSONObject(request.Result); // Read the whole string


        if (wholeString.getString("success").equals("0")) {
            Toast.makeText(EditProfile.this, wholeString.getString("message"), Toast.LENGTH_SHORT).show();
        } else {
            session.createSession(email, fullname, birthday, gender, sexuality, bio, updatedImageUrl,location);
            Intent intentSignIn = new Intent(EditProfile.this, HomeView.class);
            intentSignIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intentSignIn);
        }
    }

    public void doBack() {
        Intent intent = new Intent(EditProfile.this, Settings.class);
        startActivity(intent);
    }

    public void doSave() throws JSONException {
        Intent intent = new Intent(EditProfile.this, Settings.class);
        startActivity(intent);

        if (!name.getText().toString().equals("")) {
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
            String link = "https://lamp.ms.wits.ac.za/home/s1851427/WDAUpName.php";
            HttpUrl.Builder urlBuilder = HttpUrl.parse(link).newBuilder();
            urlBuilder.addQueryParameter("name", name.getText().toString());
            urlBuilder.addQueryParameter("username", email);
            String location =currentUser.get("LOCATION");

            String url = urlBuilder.build().toString();
            request.execute(url);

            while (request.Result.equals("Waiting")) {
                Toast.makeText(EditProfile.this, "Loading", Toast.LENGTH_SHORT).show();
            }


            // Request is finished
            JSONObject wholeString = new JSONObject(request.Result); // Read the whole string
            //JSONArray jsonArray = new JSONArray(wholeString.getJSONArray("login").toString()); // extract the login credentials array

            //JSONObject userCredentials = jsonArray.getJSONObject(0);

            if (wholeString.getString("success").equals("0")) {
                Toast.makeText(EditProfile.this, wholeString.getString("message"), Toast.LENGTH_SHORT).show();
            } else {


                sessionManager.createSession(email, name.getText().toString(), birthday, gender, sexuality, bio, imageUrl,location);
                Intent intentSignIn = new Intent(EditProfile.this, Profile.class);
                intentSignIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentSignIn);
            }

        }
        if (!biog.getText().toString().equals("")) {
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
            String link = "https://lamp.ms.wits.ac.za/home/s1851427/WDAUpBio.php";
            HttpUrl.Builder urlBuilder = HttpUrl.parse(link).newBuilder();
            urlBuilder.addQueryParameter("biography", biog.getText().toString());
            urlBuilder.addQueryParameter("username", email);
            String location =currentUser.get("LOCATION");


            String url = urlBuilder.build().toString();
            request.execute(url);

            while (request.Result.equals("Waiting")) {
                Toast.makeText(EditProfile.this, "Loading", Toast.LENGTH_SHORT).show();
            }


            // Request is finished
            JSONObject wholeString = new JSONObject(request.Result); // Read the whole string

            if (wholeString.getString("success").equals("0")) {
                Toast.makeText(EditProfile.this, wholeString.getString("message"), Toast.LENGTH_SHORT).show();
            } else {


                sessionManager.createSession(email, fullname, birthday, gender, sexuality, biog.getText().toString(), imageUrl,location);
                Intent intentSignIn = new Intent(EditProfile.this, Profile.class);
                intentSignIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentSignIn);
            }

        }


    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission
                (EditProfile.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
        ) {
        } else {
            ActivityCompat.requestPermissions(
                    EditProfile.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1
            );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == numPics && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();

            Bitmap bitmap = null;
            //get the image's file location
            filePath = imageHandler.getRealPathFromUri(uri, EditProfile.this);
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

}