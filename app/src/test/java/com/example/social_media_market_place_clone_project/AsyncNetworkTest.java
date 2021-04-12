package com.example.social_media_market_place_clone_project;

import android.content.Intent;
import android.widget.Toast;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static org.junit.Assert.assertEquals;

public class AsyncNetworkTest {

    @Test
    public void use_class_to_make_request() throws JSONException, IOException, InterruptedException {
        AsyncNetwork request = new AsyncNetwork();
        String link="";

        MockWebServer server = new MockWebServer();

        // Schedule some responses.
        server.enqueue(new MockResponse().setBody("success"));

        // Start the server.
        server.start();

        // Ask the server for its URL. You'll need this to make HTTP requests.
        HttpUrl baseUrl = server.url(link);

        System.out.println(baseUrl.toString());
        HttpUrl.Builder urlBuilder = HttpUrl.parse(baseUrl.toString()).newBuilder();
        String url = urlBuilder.build().toString();
        request.execute(url);

//        while(request.Result.equals("Waiting")){
////            do nothing, request in progress
//            System.out.println("waiting");
//        }

        // Request is finished
        RecordedRequest request1 = server.takeRequest();
        System.out.println(request1.getPath());
        assertEquals(request.Result , "success");
    }

}