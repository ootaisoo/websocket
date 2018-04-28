package com.example.developer.chat

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.KeyEvent
import android.widget.Button
import android.widget.EditText
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import java.util.concurrent.TimeUnit
import android.view.inputmethod.EditorInfo
import android.widget.TextView




class MainActivity : AppCompatActivity() {

    companion object {
        @JvmStatic
        private val TAG = MainActivity::class.java.simpleName
    }

    private lateinit var client : OkHttpClient
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var editText : EditText
    private lateinit var sendButton : Button
    private lateinit var message: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.edit_text)
        sendButton = findViewById(R.id.send)
        client = OkHttpClient.Builder()
            .readTimeout(0, TimeUnit.MILLISECONDS)
            .build();

        var request : Request = Request.Builder().url("ws://10.0.2.2:5000").build()
        var listener = MyWebSocketListener()
        val webSocket : WebSocket = client.newWebSocket(request, listener)

        sendButton.setOnClickListener { b ->
            webSocket?.send(editText.getText().toString().trim())
        }

        client.dispatcher().executorService().shutdown();
    }

//    fun sendMessage(){
//        var message : String = editText.getText().toString().trim()
//        var parser = JsonParser()
//        var json = parser.parse(message) as JSONObject


//    }
}