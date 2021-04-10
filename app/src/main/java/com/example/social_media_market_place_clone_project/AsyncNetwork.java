package com.example.social_media_market_place_clone_project;

import android.os.AsyncTask;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/* This class handles all requests to the database.
It works by creating an AsyncTask in and running a synchronous request within that asynchronous task,
the request data is then stored in the "Result" string so it can be accessed.

It accepts a String with the link (url) to the php file.
Note that this link should already contain all the query parameters needed (use HttpUrl.Builder).

To initialize this class use:
    AsyncNetwork example = new AsyncTask();
To launch the asynchronous task you then have to use:
    example.execute(url);
where url is the String with the link.

Before you try to access the response data it is advisable to create a short delay for the Asynchronous task to finish,
this is done by using:
    example.get(5000,TimeUnit.MILLISECONDS);
you may be asked to put that in a try-catch loop. The code above delays the Main UI by 5 seconds, you may shorten it but
5 second is safe. This delay can be hidden by displaying a progress bar/circle for 5 second on the main UI.

To access the response data, you must access the "Result" string in the class:
    example.Result;
if you try to access response data before asynchronous task is finished, the "Result" string will have the value "Waiting".

 */

public class AsyncNetwork extends AsyncTask<String, Integer, String> {
    String Result= "Waiting";

    @Override
    protected String doInBackground(String... link) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(link[0])
                .build();

        Call call = client.newCall(request);
        Response response = null;
        try {
            response = call.execute(); // Synchronous request done here.
        } catch (IOException e) {
            e.printStackTrace();
        }

        String ResponseData = null;
        try {
            ResponseData = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Result = ResponseData;
        return ResponseData;
    }

}
