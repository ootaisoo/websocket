package com.example.developer.chat

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.EditText
import com.google.gson.Gson
import okhttp3.WebSocket

class MainActivity : AppCompatActivity(), MyWebSocketListener.OnMessageReceivedListener {

    companion object {
        @JvmStatic
        private val TAG = MainActivity::class.java.simpleName
    }

    private lateinit var messageAdapter: MessageAdapter
    private lateinit var editText : EditText
    private lateinit var sendButton : Button
    private lateinit var messages: MutableList<String>
    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayoutManager : LinearLayoutManager
    private lateinit var roomName : String
    private lateinit var webSocket: WebSocket
    private lateinit var message : String

    val singleton = WebSocketSingleton.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        singleton.listener?.onMessageReceivedlistener = this
        webSocket = singleton.webSocket

        roomName = intent.getStringExtra("roomName")
        messages = SelectRoomActivity.chats.get(roomName)!!

        editText = findViewById(R.id.edit_text)
        sendButton = findViewById(R.id.send)

        messageAdapter = MessageAdapter(messages)

        recyclerView = findViewById(R.id.output)
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.adapter = messageAdapter

        sendButton.setOnClickListener { b ->
            val gson = Gson()
            message = editText.text.toString().trim()
            webSocket.send(gson.toJson(Message(roomName, message)))
        }
    }

    override fun onMessageReceived() {
        messageAdapter.notifyItemInserted(messages.indexOf(message))
    }
}