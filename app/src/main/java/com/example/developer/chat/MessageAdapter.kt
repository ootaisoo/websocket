package com.example.developer.chat

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class MessageAdapter(private var messages: MutableList<String>) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    companion object {
        @JvmStatic
        private val TAG = MessageAdapter::class.java.simpleName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.message_holder, parent, false)
        return MessageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        var message = messages.get(position)
        holder.textView.setText(message)
    }

    fun getItem(position: Int) = messages[position]

    inner class MessageViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var textView : TextView = itemView.findViewById(R.id.message)
    }
}