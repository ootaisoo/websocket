package com.example.developer.chat

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Button
import com.google.gson.Gson
import okhttp3.WebSocket

import java.util.ArrayList
import java.util.HashMap
import java.util.WeakHashMap

class SelectRoomActivity : AppCompatActivity(), AddRoomDialogFragment.NoticeDialogListener,
    RoomsAdapter.OnRoomSelectedListener, MyWebSocketListener.OnMessageReceivedListener {

    companion object {

        private val TAG = SelectRoomActivity::class.java.simpleName

        private val DIALOG_ADD_ROOM = "dialog_check_phrase"
        internal var chats: MutableMap<String, MutableList<String>> = HashMap()
    }

    private lateinit var webSocket: WebSocket
    private lateinit var button: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var rooms: MutableList<String>
    private lateinit var messagesToStore: MutableList<String>
    private lateinit var roomsAdapter: RoomsAdapter

    val singleton = WebSocketSingleton.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_room)

        webSocket = singleton.webSocket

        rooms = ArrayList()

        roomsAdapter = RoomsAdapter(rooms, this, this)

        recyclerView = findViewById(R.id.rooms_recycler)
        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = roomsAdapter

        val dividerItemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)

        button = findViewById(R.id.new_room_button)
        button.setOnClickListener {
            val dialog = AddRoomDialogFragment.newInstance()
            dialog.show(supportFragmentManager, DIALOG_ADD_ROOM)
        }
    }

    override fun onDialogPositiveClick(roomName: String) {
        roomsAdapter.add(roomName)
        chats[roomName] = ArrayList()
    }

    override fun onRoomSelected(roomName: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("roomName", roomName)
        startActivity(intent)
    }

    override fun onMessageRecived(text: String) {
        Log.e(TAG, "12345")
        val gson = Gson()
        val message = gson.fromJson(text, Message::class.java)

        messagesToStore = SelectRoomActivity.chats.get(message.roomName)!!
        messagesToStore.add(message.message)
        SelectRoomActivity.chats.put(message.roomName, messagesToStore)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "onDestroy()")
    }
}


