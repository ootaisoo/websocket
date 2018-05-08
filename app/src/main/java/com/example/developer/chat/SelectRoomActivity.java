package com.example.developer.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectRoomActivity extends AppCompatActivity implements AddRoomDialogFragment.NoticeDialogListener, RoomsAdapter.OnRoomSelectedListener {

    private static final String TAG = RoomsAdapter.class.getSimpleName();

    private static final String DIALOG_ADD_ROOM = "dialog_check_phrase";

    Button button;
    RecyclerView recyclerView;
    List<String> rooms;
    RoomsAdapter roomsAdapter;
    static Map<String, List<String>> chats = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_room);

        rooms = new ArrayList<>();

        roomsAdapter = new RoomsAdapter(rooms, this, this);

        recyclerView = findViewById(R.id.rooms_recycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(roomsAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        button = findViewById(R.id.new_room_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = AddRoomDialogFragment.newInstance();
                dialog.show(getSupportFragmentManager(), DIALOG_ADD_ROOM);
            }
        });
    }

    @Override
    public void onDialogPositiveClick(@NotNull String roomName) {
        roomsAdapter.add(roomName);
        chats.put(roomName, new ArrayList<String>());
    }

    @Override
    public void onRoomSelected(String roomName) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("roomName", roomName);
        startActivity(intent);
    }
}


