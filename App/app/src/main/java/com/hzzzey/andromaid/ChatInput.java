package com.hzzzey.andromaid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ChatInput extends AppCompatActivity {

    private RecyclerView mChatRecycler;
    private RecyclerView.Adapter mAdapterChat;
    public List<BaseMessage> ListMessage = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_input);
        mChatRecycler = findViewById(R.id.reyclerview_message_list);
        //todo fetch data
        ListMessage.add(new BaseMessage(true,"now","Ok "));
        ListMessage.add(new BaseMessage(false,"now","Diterima"));
        ListMessage.add(new BaseMessage(true,"now","Terima Kasih"));


        //todo back end
        mChatRecycler.setLayoutManager(new LinearLayoutManager(this));
        mAdapterChat = new Adapter_Chat_Input( ListMessage);
        mChatRecycler.setAdapter(mAdapterChat);

    }
}
