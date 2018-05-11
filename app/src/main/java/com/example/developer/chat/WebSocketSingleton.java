package com.example.developer.chat;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

public class WebSocketSingleton {

    private static final String TAG = WebSocketSingleton.class.getSimpleName();

    private static WebSocketSingleton instance;

    private MyWebSocketListener listener;
    private WebSocket webSocket;
    private OkHttpClient client;

    public MyWebSocketListener getListener() {
        return listener;
    }

    public WebSocket getWebSocket() {
        return webSocket;
    }

    public OkHttpClient getClient() {
        return client;
    }

    private WebSocketSingleton(){

        Log.e(TAG, "WebSocketSingleton()");
        client = new OkHttpClient.Builder()
                .readTimeout(0, TimeUnit.MILLISECONDS)
                .build();

        Request request = new Request.Builder().url("ws://10.0.2.2:5000").build();

        listener = new MyWebSocketListener();

        webSocket = client.newWebSocket(request, listener);
    }

    public static WebSocketSingleton getInstance() {

        if (instance == null){

            instance = new WebSocketSingleton();
        }
        return instance;
    }

}
