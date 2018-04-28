package com.example.developer.chat

import android.graphics.Color
import android.support.v7.view.menu.MenuBuilder
import android.support.v7.view.menu.MenuPopupHelper
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class MessageAdapter(private var messages: List<String>) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.message_holder, parent, false)
        return MessageViewHolder(view)
    }

    override fun getItemCount(): Int = messages.count()

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    fun getItem(position: Int) = messages[position]

    inner class MessageViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private var textView : TextView = itemView.findViewById(R.id.message)
        fun bind(message: String) {
            textView.setText(message)
        }
    }
}