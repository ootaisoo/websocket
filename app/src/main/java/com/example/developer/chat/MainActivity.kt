package com.example.developer.chat

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Button
import android.widget.EditText
import okhttp3.*
import okio.ByteString
import java.util.concurrent.TimeUnit


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
    private lateinit var roomName : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        roomName = intent.getStringExtra("roomName")
        messages = SelectRoomActivity.chats.get(roomName)!!
        Log.e(TAG, roomName);


        editText = findViewById(R.id.edit_text)
        sendButton = findViewById(R.id.send)

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
            }

            override fun onMessage(webSocket: WebSocket?, text: String) {
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

    override fun onDestroy() {
        super.onDestroy()
        SelectRoomActivity.chats.put(this@MainActivity.roomName, messages)
    }
}