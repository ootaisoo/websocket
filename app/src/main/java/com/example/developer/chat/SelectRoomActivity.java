package com.example.developer.chat;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SelectRoomActivity extends AppCompatActivity implements AddRoomDialogFragment.NoticeDialogListener {

    private static final String DIALOG_ADD_ROOM = "dialog_check_phrase";

    Button button;
    RecyclerView recyclerView;
    List<String> rooms;
    String roomName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_room);

        rooms = new ArrayList<>();

        recyclerView = findViewById(R.id.rooms_recycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        button = findViewById(R.id.new_room_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = AddRoomDialogFragment.newInstance();
                dialog.show(getSupportFragmentManager(), DIALOG_ADD_ROOM);
//                Intent intent = new Intent(SelectRoomActivity.this, MainActivity.class);
                rooms.add(roomName);
            }
        });
    }

    @Override
    public void onDialogPositiveClick(@NotNull String roomName) {
        this.roomName = roomName;
    }
}
