package com.hzzzey.andromaid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ChatInput extends AppCompatActivity implements Adapter_chat_choices.OnChoiceListener, DialogInputSpending.OnSpendListener {

    final Handler handler = new Handler();
    User user = User.getInstance();
    private RecyclerView mChatRecycler;
    private RecyclerView mChoicesRecycler;
    private RecyclerView.Adapter mAdapterChat,mChoicesAdapter;
    public List<BaseMessage> ListMessage = new ArrayList<>();
    public List<BaseChoice> ListChoices = new ArrayList<>();
    public Double Spend = 0.0;
    SQLiteDatabase dbRead,dbWrite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_input);
        mChatRecycler = findViewById(R.id.reyclerview_message_list);
        mChoicesRecycler = findViewById(R.id.choices_recycler);
        //todo fetch data, database juga belum ada

        ChatHistoryDb dbHelper = ChatHistoryDb.getInstance(this);
        dbRead = dbHelper.getReadableDatabase();
        dbWrite = dbHelper.getWritableDatabase();

        addMessage(false,"now","Good Day " + user.get_UserName() + " what can i help today?");
        ListMessage.clear();
        //pesan inisiasi

        ListMessage = getListMessage();
        //todo back end
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        mChatRecycler.setLayoutManager(linearLayoutManager);
        mAdapterChat = new Adapter_Chat_Input(ListMessage);
        mChatRecycler.setAdapter(mAdapterChat);
        main_menu();
        //todo ini untuk recycler choices
        initChat(ListChoices);
    }

    private void clearChoice()
    {
        ListChoices.clear();
    }
    public void onSpendClick(int result,Double ammount)
    {
        clearChoice();
        switch (result)
        {
            case SPEND_ACCEPTED:
                addMessage(true,"now","its " + ammount);
                addMessage(false,"now","Ok, i am going to add " + ammount);
                addMessage(false,"now","Everything's correct? ");
                Spend = ammount;
                confirmSpending();
                break;
            case SPEND_DECLINE:
                main_menu();
                addMessage(true,"now","Nevermind ");
                addMessage(false,"now","Alright, is there anything i can help with? ");
                break;
        }
        mChatRecycler.scrollToPosition(mChatRecycler.getAdapter().getItemCount()-1);
        refresh_menu();
    }
    public void onChoiceClick(int position) {
        Log.d("Chat",position +"");//nentukan choices disini
        BaseChoice result = ListChoices.get(position);
        clearChoice();
        if(result.type!=USER_SPEND_RETRY)addMessage(true,"now",result.Content);
        switch (result.type)
        {
            case BACK_TO_MAIN_MENU:
                addMessage(false,"now","Alright, is there anything i can help with? ");
                main_menu_after();
                break;
            case USER_SPEND:
                addMessage(false,"now","How much is it?");
                user_spending_menu();
                break;
            case USER_SPEND_RETRY:
                user_spending_menu();
                break;
            case SHOW_TODAY_SPENDING:
                show_today_spending();
                main_menu_after();
                break;
            case CONFIRM: //todo pendaftaran awal
                user_spending_menu();
                break;
            case CANCEL:
                main_menu_after();
                break;
            case SPEND_CONFIRMED:
                user.spend(Spend);
                Spend = 0.0;
                addMessage(false,"now","I have write this spending...");
                setResult(1,new Intent());
                main_menu_after();
                break;
            case END:
                End_Chat();
                break;
        }
        refresh_menu();
        mChatRecycler.scrollToPosition(mChatRecycler.getAdapter().getItemCount()-1);
    }
    public void initChat(List ListChoices)
    {
        mChoicesRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mChoicesAdapter = new Adapter_chat_choices( ListChoices,this);
        mChoicesRecycler.setAdapter(mChoicesAdapter);
    }
    private void main_menu()
    {
        addChoices(USER_SPEND,"I spend some money");
        addChoices(SHOW_TODAY_SPENDING,"How much did i spent today?");
        addChoices(END,"Nothing... Just checking in");
    }
    private void main_menu_after()
    {
        addMessage(false,"now","Anything else? ");
        addChoices(USER_SPEND,"I spend some money");
        addChoices(SHOW_TODAY_SPENDING,"How much did i spent today?");
        addChoices(END,"That's all, thank you...");
    }
    private void refresh_menu()//todo panggil refresh ketika chat baru telah diisi https://medium.com/@suragch/updating-data-in-an-android-recyclerview-842e56adbfd8
    {
        mAdapterChat.notifyDataSetChanged();
        mChoicesAdapter.notifyDataSetChanged();
    }
    private void user_spending_menu()//todo tambahkan dialog input data
    {
        FragmentTransaction manager = getSupportFragmentManager().beginTransaction();
        DialogInputSpending dialogInputSpending = new DialogInputSpending();
        dialogInputSpending.show(manager,"ok");
        addChoices(CANCEL,"Cancel that");
        addChoices(USER_SPEND_RETRY,"I Spend ");
    }

    private void show_today_spending()
    {
        List<todayspending> spending = user.getTodaySpending();
        String spending_info;
        if(spending.size()>0) spending_info = "your spending info\n";
        else spending_info = "Looks like you haven't spent any money today!\n";
        for(int i = 0;i<spending.size();i++)
        {
            spending_info+= spending.get(i).time + ' ' + spending.get(i).spending+"\n";
        }
        addMessage(false,"now",spending_info);
    }

    private void End_Chat()
    {
        addMessage(false,"now","Alright " + user.get_UserName() +" call me when you need me");
        finish();
    }

    private void addMessage(Boolean isSent, String time, String content)
    {
        ListMessage.add(new BaseMessage(isSent,time,content));
        ContentValues values = new ContentValues();
        values.put(ChatHistoryDb.ChatEntry.COLUMN_NAME_IS_SEND_BY_USER, isSent);
        values.put(ChatHistoryDb.ChatEntry.COLUMN_NAME_CONTENT, content);
        dbWrite.insert(ChatHistoryDb.ChatEntry.TABLE_NAME, null, values);
    }

    private void addChoices(int code, String content)
    {
        ListChoices.add(new BaseChoice(code,content,0));
    }

    private void confirmSpending()
    {
        Log.d("spend","terpanggil kok");
        addChoices(BACK_TO_MAIN_MENU,"Cancle that");
        addChoices(USER_SPEND_RETRY,"Wait, wrong ammount");
        addChoices(SPEND_CONFIRMED,"Looks Good!");
    }

    private List<BaseMessage> getListMessage()
    {
        List<BaseMessage>Chat =  new ArrayList<>();
        Cursor cursors = dbRead.rawQuery("Select * from (select _ID, strftime('%H.%M',datetime(time_sent,'localtime')) as time," +
                ChatHistoryDb.ChatEntry.COLUMN_NAME_CONTENT +",  " +
                ChatHistoryDb.ChatEntry.COLUMN_NAME_IS_SEND_BY_USER +
                " from "+ ChatHistoryDb.ChatEntry.TABLE_NAME +  " ORDER BY _ID  DESC LIMIT 100) ORDER BY _ID ASC"
                ,null);

        while(cursors.moveToNext()) {
            String content = cursors.getString(cursors.getColumnIndexOrThrow(ChatHistoryDb.ChatEntry.COLUMN_NAME_CONTENT ));
            String time = cursors.getString(cursors.getColumnIndexOrThrow("time"));
            Integer sender = cursors.getInt(cursors.getColumnIndexOrThrow(ChatHistoryDb.ChatEntry.COLUMN_NAME_IS_SEND_BY_USER ));
            Boolean IS_SENT_BY_USER = false;
            if(sender==1) IS_SENT_BY_USER = true;
            Chat.add(new BaseMessage(IS_SENT_BY_USER,time,content));
        }
        cursors.close();
        return Chat;
    }
    //choice codes
    final int BACK_TO_MAIN_MENU = 0;
    final int USER_SPEND = 1;
    final int SHOW_TODAY_SPENDING = 2;
    final int CONFIRM = 3;
    final int CANCEL = 4;
    final int END = 5;
    final int USER_SPEND_RETRY = 6;
    final int SPEND_ACCEPTED = 7;//reminder, di dialog input ada deklarasi sendiri
    final int SPEND_DECLINE = 8;
    final int SPEND_CONFIRMED = 9;
}

