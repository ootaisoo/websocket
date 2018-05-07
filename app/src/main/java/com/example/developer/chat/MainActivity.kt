package com.example.developer.chat

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.KeyEvent
import android.widget.Button
import android.widget.EditText
import java.util.concurrent.TimeUnit
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import okhttp3.*
import okio.ByteString


class MainActivity : AppCompatActivity() {

    companion object {
        @JvmStatic
        private val TAG = MainActivity::class.java.simpleName
    }

    private lateinit var client : OkHttpClient
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var editText : EditText
    private lateinit var sendButton : Button
    private lateinit var messages: MutableList<String>
    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayoutManager : LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.edit_text)
        sendButton = findViewById(R.id.send)

        messages = ArrayList()
        messageAdapter = MessageAdapter(messages)

        recyclerView = findViewById(R.id.output)
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.adapter = messageAdapter

        client = OkHttpClient.Builder()
            .readTimeout(0, TimeUnit.MILLISECONDS)
            .build();

        var request : Request = Request.Builder().url("ws://10.0.2.2:5000").build()

        val webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket?, response: Response?) {
                Log.e(TAG, "CONNECTION")
            }

            override fun onMessage(webSocket: WebSocket?, text: String) {
                Log.e(TAG, "onMessage()")
                messageAdapter.add(text)
            }

            override fun onMessage(webSocket: WebSocket?, bytes: ByteString?) {
                super.onMessage(webSocket, bytes)
            }

            override fun onClosing(webSocket: WebSocket?, code: Int, reason: String?) {
                webSocket?.close(1000, null)
            }

            override fun onClosed(webSocket: WebSocket?, code: Int, reason: String?) {
                super.onClosed(webSocket, code, reason)
            }

            override fun onFailure(webSocket: WebSocket?, t: Throwable?, response: Response?) {
                t?.printStackTrace();
            }
        })

        sendButton.setOnClickListener { b ->
            webSocket?.send(editText.getText().toString().trim())

        }

        client.dispatcher().executorService().shutdown();
    }
}