package com.example.social_media_market_place_clone_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.HttpUrl;

public class EditProfile extends AppCompatActivity {
    View back;
    Button save;
    EditText name, biog, interests, location;
    ImageView profilePicture;
   // String imageUrl;
    int numPics = 1;
    String filePath;
    Uri uri;
    ImageHandler imageHandler;
    StringHandler stringHandler;

    ArrayList<String> interestList = new ArrayList<>();
    ImageButton add, remove;
    TextView int1TV, int2TV;

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

        // Add and remove interests
        add = (ImageButton) findViewById(R.id.add_button);
        remove = (ImageButton) findViewById(R.id.remove_button);
        int1TV = (TextView) findViewById(R.id.int_tv_1);
        int2TV = (TextView) findViewById(R.id.int_tv_2);




        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interestList.size() < 5){
                    interestList.add(interests.getText().toString());
                    interests.setText("");

                    String s1 = "";
                    String s2 = "";

                    for (int i = 0; i < interestList.size(); ++i) {
                        if (i+1 <= 3){
                            s1 += interestList.get(i) + " ";
                        }

                        else{
                            s2 += interestList.get(i) + " ";
                        }
                    }
                    int1TV.setText(s1);
                    int2TV.setText(s2);
                }

                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);
                    builder.setTitle("Interest Full");
                    builder.setMessage("You have added the maximum number of interests.");

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interestList.size() > 0){
                    interestList.remove(interestList.size() -1);

                    String s1 = "";
                    String s2 = "";

                    for (int i = 0; i < interestList.size(); ++i) {
                        if (i+1 <= 3){
                            s1 += interestList.get(i) + " ";
                        }

                        else{
                            s2 += interestList.get(i) + " ";
                        }
                    }

                    int1TV.setText(s1);
                    int2TV.setText(s2);
                }

                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);
                    builder.setTitle("Interest Empty");
                    builder.setMessage("There are no interests to remove");

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

//        helper classes
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
                    if(imageHandler.imageUrl!=null){
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
        String updatedImageUrl= stringHandler.addChar(imageHandler.imageUrl, 's', 4);
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
        String location = currentUser.get("LOCATION");

        String link="https://lamp.ms.wits.ac.za/home/s1851427/WDAUpPicture.php";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(link).newBuilder();
        urlBuilder.addQueryParameter("username",email);
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
            session.createSession(email,fullname,birthday,gender,sexuality, bio, updatedImageUrl,location);
            Intent intentSignIn = new Intent(EditProfile.this, HomeView.class);
            intentSignIn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
            String location = currentUser.get("LOCATION");
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


                sessionManager.createSession(email, name.getText().toString(),birthday,gender,sexuality,bio,imageUrl,location);
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
            String location = currentUser.get("LOCATION");
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


                sessionManager.createSession(email, fullname,birthday,gender,sexuality,biog.getText().toString(),imageUrl,location);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == numPics && resultCode == RESULT_OK && data != null && data.getData() != null){
            uri = data.getData();

            Bitmap bitmap = null;
            //get the image's file location
            filePath = imageHandler.getRealPathFromUri(uri, EditProfile.this);
            imageHandler.uploadToCloudinary(filePath);
            //imageUrl = imageHandler.imageUrl;


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