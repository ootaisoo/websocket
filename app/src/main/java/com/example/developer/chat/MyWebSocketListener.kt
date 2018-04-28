package com.example.developer.chat

import android.util.Log
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class MyWebSocketListener(var messages : MutableList<String>) : WebSocketListener() {

    companion object {
        @JvmStatic
        private val TAG = MyWebSocketListener::class.java.simpleName
    }

//    var messages : MutableList<String> = ArrayList()


    override fun onOpen(webSocket: WebSocket?, response: Response?) {
        Log.e(TAG, "CONNECTION")
    }

    override fun onFailure(webSocket: WebSocket?, t: Throwable?, response: Response?) {
        t?.printStackTrace();
    }

    override fun onClosing(webSocket: WebSocket?, code: Int, reason: String?) {
        webSocket?.close(1000, null)
    }

    override fun onMessage(webSocket: WebSocket?, text: String) {
        messages.add(text)
    }

    override fun onMessage(webSocket: WebSocket?, bytes: ByteString?) {

    }
}