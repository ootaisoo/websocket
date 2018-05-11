package com.example.developer.chat

import android.content.Context
import android.util.Log

import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket

class WebSocketSingleton private constructor(context: Context) {

    companion object {

        private val TAG = WebSocketSingleton::class.java.simpleName

        private var instance : WebSocketSingleton? = null

        fun  getInstance(context: Context): WebSocketSingleton {
            if (instance == null)  // NOT thread safe!
                instance = WebSocketSingleton(context)

            return instance!!
        }
    }

    var listener: MyWebSocketListener? = null
    val webSocket: WebSocket
    val client: OkHttpClient

    init {

        Log.e(TAG, "WebSocketSingleton()")
        client = OkHttpClient.Builder()
            .readTimeout(0, TimeUnit.MILLISECONDS)
            .build()

        val request = Request.Builder().url("ws://10.0.2.2:5000").build()

        listener = MyWebSocketListener()

        webSocket = client.newWebSocket(request, listener!!)
    }
}
