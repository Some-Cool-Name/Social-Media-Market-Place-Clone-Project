package com.example.social_media_market_place_clone_project;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.example.social_media_market_place_clone_project.SignUp2;

import java.util.HashMap;
import java.util.Map;

public class ImageHandler {

    public String imageUrl = null;
    Map config = new HashMap();
    private Context context ;
    public ImageHandler(Context context){
        this.context=context;
    }

    //    this method uploads image to cloud and returns it's url
    //    also outputs a toast message to indicate status of upload
    //    if you want to do something after successful image upload, do it on onSuccess override
    public int uploadToCloudinary(String filePath) {
        try{
            Log.d("A", "sign up uploadToCloudinary- ");

            MediaManager.get().upload(filePath).callback(new UploadCallback() {
                @Override
                public void onStart(String requestId) {
//                start
                    //System.out.println("starting");
                }

                @Override
                public void onProgress(String requestId, long bytes, long totalBytes) {
//                uploading
                    //System.out.println("in progress...");
                }

                @Override
                public void onSuccess(String requestId, Map resultData) {
                    String url = resultData.get("url").toString();
                    System.out.println(url);
                    imageUrl = url;
                }

                @Override
                public void onError(String requestId, ErrorInfo error) {
//               error.getDescription()
                    //System.out.println(error.getDescription());
                    imageUrl = "error";
                }

                @Override
                public void onReschedule(String requestId, ErrorInfo error) {
                    //System.out.println(error.getDescription());
                }
            }).dispatch();

            return 1;
        }
        catch (Exception e){
            e.printStackTrace();
            config.put("cloud_name", "dkctv74ue");
            config.put("api_key", "421896893816688");
            config.put("api_secret", "pumaVpw63KKXxpRi-ZfLzwo7eck");
            MediaManager.init(context, config);
            uploadToCloudinary(filePath);
        }

        return 0;
    }

    //    gets the file path of an image from the URI
    public String getRealPathFromUri(Uri imageUri, Activity activity){
        Cursor cursor = null;

        try {
            cursor = activity.getContentResolver().query(imageUri, null, null, null, null);
            if(cursor==null) {
                return imageUri.getPath();
            }else{
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                return cursor.getString(idx);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return "0";

    }

}
