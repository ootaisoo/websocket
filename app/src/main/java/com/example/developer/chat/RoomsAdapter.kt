package com.example.developer.chat

import android.content.Context
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class RoomsAdapter(var rooms : MutableList<String>, var listener : OnRoomSelectedListener, var context : Context) : RecyclerView.Adapter<RoomsAdapter.RoomsViewHolder>() {

    companion object {
        @JvmStatic
        private val TAG = RoomsAdapter::class.java.simpleName
    }

    interface OnRoomSelectedListener {
        fun onRoomSelected(roomName : String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.room_holder, parent, false)
        return RoomsViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoomsViewHolder, position: Int) {
        var roomname = rooms.get(position)
        holder.roomTextView.setText(roomname)
    }

    fun add(item: String) {
        rooms.add(item)
        notifyItemInserted(rooms.indexOf(item))
    }

    fun delete(item: String) {
        rooms.remove(item)
        notifyItemRemoved(rooms.indexOf(item))
    }

    override fun getItemCount(): Int {
        return rooms.size
    }

    inner class RoomsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var roomTextView: TextView

        init {
            roomTextView = itemView.findViewById(R.id.room_name)
            itemView.setOnClickListener {
                listener?.onRoomSelected(roomTextView.text.toString())
            }
            itemView.setOnLongClickListener {
                val builder = AlertDialog.Builder(context)
                builder.setItems(
                    R.array.chat_dialog_menu_items
                ) { dialog, which -> delete(roomTextView.text.toString()) }
                builder.create().show()
                true
            }
        }
    }
}