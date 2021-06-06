package com.example.social_media_market_place_clone_project;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Chat extends AppCompatActivity {
    LinearLayout layout;
    RelativeLayout layout_2;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    Firebase reference1, reference2;
    View backButton;
    TextView matchName;
    Button addAttachment;
    ImageHandler imageHandler;
    int numPics = 1;
    String filePath;
    Uri uri;
    EditText captionEditText;
    String caption;
    String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        layout = findViewById(R.id.layout1);
        layout_2 = findViewById(R.id.layout2);
        sendButton = findViewById(R.id.sendButton);
        messageArea = findViewById(R.id.messageArea);
        scrollView = findViewById(R.id.scrollView);
        addAttachment = findViewById(R.id.attachment);

        imageHandler = new ImageHandler(this);

        backButton = findViewById(R.id.chat_back_button);
        matchName = (TextView) findViewById(R.id.match_name_text);

        Firebase.setAndroidContext(this);
        reference1 = new Firebase("https://dating-b5a28-default-rtdb.firebaseio.com/" + UserDetails.username + "_" + UserDetails.chatWith);
        reference2 = new Firebase("https://dating-b5a28-default-rtdb.firebaseio.com/" + UserDetails.chatWith + "_" + UserDetails.username);

        matchName.setText(UserDetails.chatWith);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();

                if(!messageText.equals("")){
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user", UserDetails.username);
                    reference1.push().setValue(map);
                    reference2.push().setValue(map);
                    messageArea.setText("");
                }
            }
        });
        addAttachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                select image
                imageSelect();
//                note that imagehandler now has string url

//                TODO: add caption to image
                captionEditText = null;
                caption = null;
                imageUrl = null;


//                send message
                Map<String, String> map = new HashMap<String, String>();
                map.put("message", caption);
                map.put("imageUrl", imageUrl);
                map.put("user", UserDetails.username);
                reference1.push().setValue(map);
                reference2.push().setValue(map);
                captionEditText.setText("");
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String userName = map.get("user").toString();
                String imageUrl = null;

                try {
                    imageUrl = map.get("imageUrl").toString();
                }
                catch (Exception e){
                    e.printStackTrace();
                }

                if(imageUrl.equals(null)){
                    if(userName.equals(UserDetails.username)){
                        addMessageBox(message, 1);
                    }
                    else{
                        addMessageBox(message, 2);
                    }
                }
                else{
                    if(userName.equals(UserDetails.username)){
                        addMessageBoxWithImage(message, 1, imageUrl);
                    }
                    else{
                        addMessageBoxWithImage(message, 2, imageUrl);
                    }
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void imageSelect() {
        requestPermission();
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, numPics);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == numPics && resultCode == RESULT_OK && data != null && data.getData() != null){
            uri = data.getData();

            //get the image's file location
            filePath = imageHandler.getRealPathFromUri(uri, Chat.this);
            imageHandler.uploadToCloudinary(filePath);

        }
    }

    private void requestPermission(){
        if(ContextCompat.checkSelfPermission
                (Chat.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
        ){
        } else {
            ActivityCompat.requestPermissions(
                    Chat.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1
            );
        }
    }

    public void addMessageBox(String message, int type){
        TextView textView = new TextView(Chat.this);
        textView.setText(message);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 7.0f;

        if(type == 1) {
            lp2.gravity = Gravity.LEFT;
            textView.setBackgroundResource(R.drawable.bubble_in);
            textView.setTextColor(0xFFFFFFFF);
            textView.setTextSize(16);
            lp2.setMargins(0,8,80,8);
        }
        else{
            lp2.gravity = Gravity.RIGHT;
            textView.setBackgroundResource(R.drawable.bubble_out);
            textView.setTextColor(0xFF707070);
            textView.setTextSize(16);
            lp2.setMargins(80,8,0,8);
        }
        textView.setLayoutParams(lp2);
        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }

    public void addMessageBoxWithImage(String message, int type, String image){
//        TODO: set the image url to be displayed
        TextView textView = new TextView(Chat.this);
        textView.setText(message);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 7.0f;

        if(type == 1) {
            lp2.gravity = Gravity.LEFT;
            textView.setBackgroundResource(R.drawable.bubble_in);
            textView.setTextColor(0xFFFFFFFF);
            textView.setTextSize(16);
            lp2.setMargins(0,8,80,8);
        }
        else{
            lp2.gravity = Gravity.RIGHT;
            textView.setBackgroundResource(R.drawable.bubble_out);
            textView.setTextColor(0xFF707070);
            textView.setTextSize(16);
            lp2.setMargins(80,8,0,8);
        }
        textView.setLayoutParams(lp2);
        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }
}