package com.example.developer.chat

import com.google.gson.Gson
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class MyWebSocketListener : WebSocketListener() {

    companion object {
        @JvmStatic
        private val TAG = MyWebSocketListener::class.java.simpleName
    }

    interface OnMessageReceivedListener {
        fun onMessageReceived()
    }

    var onMessageReceivedlistener: OnMessageReceivedListener? = null

    override fun onOpen(webSocket: WebSocket?, response: Response?) {
        super.onOpen(webSocket, response)
    }

    override fun onMessage(webSocket: WebSocket?, text: String) {
        val gson = Gson()
        val message = gson.fromJson(text, Message::class.java)

        SelectRoomActivity.chats.get(message.roomName)?.add(message.message)
        onMessageReceivedlistener?.onMessageReceived()
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
}