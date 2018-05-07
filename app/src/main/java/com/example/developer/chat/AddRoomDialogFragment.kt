package com.example.developer.chat

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import kotlinx.android.synthetic.main.dialog_room_name.view.*


class AddRoomDialogFragment : DialogFragment() {

    companion object {
        @JvmStatic
        private val TAG = AddRoomDialogFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(): AddRoomDialogFragment {
            val dialog = AddRoomDialogFragment()
            return dialog
        }
    }

    private lateinit var mView: View
    var listener: NoticeDialogListener? = null



    interface NoticeDialogListener {
        fun onDialogPositiveClick(roomName: String)
    }

    override fun onAttach(activity: Context) {
        super.onAttach(activity)

        try {
            listener = activity as NoticeDialogListener?
        } catch (e: ClassCastException) {
            throw ClassCastException(activity!!.toString() + " must implement NoticeDialogListener")
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity?.layoutInflater
        mView = inflater?.inflate(R.layout.dialog_room_name, null)!!

        onViewCreated(mView, savedInstanceState)

        val dialog = builder.setView(mView)
            .setTitle("Enter Room Name")
            .setPositiveButton("OK", null)
            .create()

        dialog.setOnShowListener {
            val button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            button.setOnClickListener {
                listener?.onDialogPositiveClick(mView.room_name_dialog.text.toString());
                dialog.dismiss() }
        }

        return dialog
    }
}