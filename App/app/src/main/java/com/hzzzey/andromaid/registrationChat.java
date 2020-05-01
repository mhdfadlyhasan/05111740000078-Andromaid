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
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class registrationChat extends AppCompatActivity implements Adapter_chat_choices.OnChoiceListener, DialogInputSpending.OnSpendListener,
        PopUpRename.PopUpRenameListener, DialogEditMonthly.DialogEditMonthlyListener,
        editLeft.DialogEditLeftListener{
    User user = User.getInstance();
    double placeholder_left,placeholder_monthly,placeholder_spend = 0.0;
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
        setContentView(R.layout.activity_registration_chat);
        mChatRecycler = findViewById(R.id.reyclerview_message_list);
        mChoicesRecycler = findViewById(R.id.choices_recycler);
        //todo fetch data, database juga belum ad
        ChatHistoryDb dbHelper = ChatHistoryDb.getInstance(this);
        dbRead = dbHelper.getReadableDatabase();
        dbWrite = dbHelper.getWritableDatabase();
        //todo pesan selamat datang
        //addMessage(false,"","Good Day " + user.get_UserName() + " what can i help today?");
        //pesan inisiasi

        if(user.get_UserName() == "Non")
        {
            greetings();
        }

        else if(user.get_MonthlyMoney() == 0.01)
        {
            addMessage(false,"",user.get_UserName()+", looks like i still need some information here");
            ask_money_info();
        }
        else if(user.get_LeftMoney() == 0.01 )
        {
            addMessage(false,"",user.get_UserName()+", I need some informations");
            ask_today_spending();
        }
        ListMessage = getListMessage();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        mChatRecycler.setLayoutManager(linearLayoutManager);
        mAdapterChat = new Adapter_Chat_Input(ListMessage);
        mChatRecycler.setAdapter(mAdapterChat);
        //todo ini untuk recycler choices
        initChat(ListChoices);
    }
    private void greetings()
    {
        addMessage(false,"","Greetings, my name is Grace, i am your personal Andromaid");
        addMessage(false,"","I will help you, to organize your spending and schedules");
        addMessage(false,"","Oh, pardon me, before we started, how can i call you?");
        addChoices(GET_NAME,"Call me...");
        ListMessage.clear();
    }
    private void ask_money_info()
    {
        addMessage(false,"","In order to help you manage your spendings, we have to make sure you're spending properly each day");
        addMessage(false,"","I can't hold or control your money, but... i can help you to monitoring your daily spendings");
        addMessage(false,"","So... can you tell me how much money you usually spend for a month? those spending like food, transportation, drink, snacks?");
        addMessage(false,"","exclude monthly fee like power, gas, water, tax, etc.");
        addChoices(GET_MONTHLY,"For a month? i spend ...");
    }
    private void how_much_should_user_spend()
    {

        int  maxDay= user.cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        addMessage(false,"","So... According to my calculation.");
        addMessage(false,""," you should spend " + placeholder_monthly/maxDay + " for a day!");
        addMessage(false,"","What do you think?");
        addChoices(ALLOCATION_AGREE,"Looks Good!, sometimes i spend lower than that");
        addChoices(ALLOCATION_DISAGREE,"No way! That's too small!");
    }

    private void ok_agreed()
    {
        addMessage(false,"","its seems that you dont have any surplus money");
        addMessage(false,"","to get some surplus money, you have to spend less than your day allocation");
        addMessage(false,"","those left money will be counted as surplus money");
        addChoices(END_BEFORE,"Okay... ");
    }
    private void ask_today_spending()
    {
        addMessage(false,"","Have you spend any money today?");
        addChoices(GET_DAY_REGIS_SPENDING,"Today, i already spent ...");
        addChoices(ASK_LEFT_AGREE,"I actually havent spend any money today ");
    }

    private void clearChoice()
    {
        ListChoices.clear();
    }

    public void onSpendClick(int result,Double ammount)
    {
        if(result == SPEND_ACCEPTED)
        {
            clearChoice();
            placeholder_spend = ammount;
            addMessage(true,"","Today, i spend " + ammount);
            ask_left_agree();
        }
    }

    private void ask_left_agree()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
        int thisdate = Integer.parseInt(dateFormat.format(user.cal.getTime()));
        int DayCount= user.cal.getActualMaximum(Calendar.DAY_OF_MONTH) - thisdate;
        double newinfo = DayCount*user.getAlocation()+ user.getAlocation()-placeholder_spend;
        placeholder_left = newinfo;
        addMessage(false,"","Last question according to my calculation");
        addMessage(false,"","you might have " + placeholder_left  + "right now.");
        addMessage(false,"","Is it correct?");
        addChoices(AGREE_LEFT_IS_RIGHT,"Yeah that's kind of correct");
        addChoices(AGREE_LEFT_IS_NOT_RIGHT,"No, i actually have ...");
        refresh_menu();
    }

    @Override
    public void LeftChangeIsYes(Double newMoney) {


        clearChoice();
        addMessage(true,"","Its Should be " + newMoney);
        if(placeholder_spend>0.0)user.spend(placeholder_spend);//???
        user.set_LeftMoney(newMoney);
        if(user.getSurplusMoney()>0) {

            addMessage(false,"","Ok " + newMoney);
            addMessage(false,"","looks like you have " + user.getSurplusMoney() + " surplus money!");

            addMessage(false,"","surplus money is money, you have spend less than your day allocation");
        }
        else if(user.getSurplusMoney()==0){

            addMessage(false,"","Hey my numbers are correct... hmph");

            addMessage(false,"","its seems that you dont have any surplus money");

            addMessage(false,"","to get some surplus money, you have to spend less than your day allocation");

            addMessage(false,"","those left money will be counted as surplus money");
        }
        else
        {

            addMessage(false,"","Ok " + newMoney);

            addMessage(false,"","Well, i suggest you spend lower these month, you're in deficit");

            addMessage(false,"","if you kept using more money that you intended, you may run out of money.");

            addMessage(false,"","just maybe... at the last week of the month");


            addMessage(false,"","also reducing your daily spending can give you some surplus money");


            addMessage(false,"","surplus money is money, you have spend less than your day allocation");
        }
        addChoices(END_BEFORE,"Okay...");

        refresh_menu();

    }
    private void dont_agree()
    {
        addMessage(false,"","Well i am sorry, this number is the highest one you should spend");
        addMessage(false,"","I suggest you to reduce your daily spending to avoid deficit every month");
        addMessage(false,"","or maybe start making money by yourself");
        addChoices(ALLOCATION_AGREE,"Okay okay, I am going to try ");
    }

    public void onChoiceClick(int position) {
        BaseChoice result = ListChoices.get(position);
        clearChoice();
        if(result.type!=GET_NAME && result.type!=GET_MONTHLY && result.type!=AGREE_LEFT_IS_NOT_RIGHT && result.type!=GET_DAY_REGIS_SPENDING)
            addMessage(true,"",result.Content);
        switch (result.type)
        {
            case GET_NAME:
                FragmentTransaction manager = getSupportFragmentManager().beginTransaction();
                PopUpRename popUpRenames = new PopUpRename();
                popUpRenames.show(manager,"ok");
                addChoices(GET_NAME,"Call me...");
                break;

            case GET_MONTHLY:
                FragmentTransaction managers = getSupportFragmentManager().beginTransaction();
                DialogEditMonthly EditMonthly = new DialogEditMonthly();
                EditMonthly.show(managers,"ok");
                addChoices(GET_MONTHLY,"For a month? i spend ...");
                break;

            case ALLOCATION_AGREE:
                addMessage(false,"","Okay, Next ...");

                user.set_MonthlyMoney(placeholder_monthly);
                ask_today_spending();
                break;
            case GET_DAY_REGIS_SPENDING:
                //dialog spending pakai yang pernah dibuat
                FragmentTransaction managerssss = getSupportFragmentManager().beginTransaction();
                DialogInputSpending dialogInputSpending = new DialogInputSpending();
                dialogInputSpending.show(managerssss,"ok");
                addChoices(GET_DAY_REGIS_SPENDING,"Today, i already spent ... ");
                addChoices(ASK_LEFT_AGREE,"I actually havent spend any money today ");
                break;
            case ASK_LEFT_AGREE:
                addMessage(false,"","Alright. ");
                ask_left_agree();
                break;

            case ALLOCATION_DISAGREE:
                dont_agree();

                break;

            case END_BEFORE:

                addMessage(false,"","you can use these surplus money as you like, maybe for eats expensive stuffs");

                addMessage(false,"","buy games, buy some book, etc ");

                addMessage(false,"","or... you could save these money and put it as your live savings! its up to you");

                addChoices(END,"Okay, i think i understand ");
                break;


            case AGREE_LEFT_IS_RIGHT:
                if(placeholder_spend>0.0)user.spend(placeholder_spend);
                user.set_LeftMoney(placeholder_left);
                ok_agreed();
                break;

            case AGREE_LEFT_IS_NOT_RIGHT:

                FragmentTransaction managerss = getSupportFragmentManager().beginTransaction();
                editLeft editLeft = new editLeft();
                editLeft.show(managerss,"ok");
                addChoices(AGREE_LEFT_IS_NOT_RIGHT,"Its should be...");
                break;

            case BACK_TO_MAIN_MENU:
                addMessage(false,"","Alright, is there anything i can help with? ");
                break;
            case USER_SPEND:
                addMessage(false,"","How much is it?");

                break;
            case CONFIRM: //todo pendaftaran awal
                break;
            case CANCEL:
                break;
            case SPEND_CONFIRMED:
                user.spend(Spend);
                Spend = 0.0;
                addMessage(false,"","I have write this spending...");
                setResult(1,new Intent());
                break;
            case END:
                End_Chat();
                break;

            case CLOSE_CHAT:
                addMessage(false,"","Have a nice day!");
                startActivity(new Intent(this,MainActivity.class));
                finish();
                break;
        }

        refresh_menu();
    }
    public void scrollbottom()
    {
        mChatRecycler.scrollToPosition(mChatRecycler.getAdapter().getItemCount()-1);
    }
    public void initChat(List ListChoices)
    {
        mChoicesRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mChoicesAdapter = new Adapter_chat_choices( ListChoices,this);
        mChoicesRecycler.setAdapter(mChoicesAdapter);
    }
    private void refresh_menu()//todo panggil refresh ketika chat baru telah diisi https://medium.com/@suragch/updating-data-in-an-android-recyclerview-842e56adbfd8
    {
        mAdapterChat.notifyDataSetChanged();
        mChoicesAdapter.notifyDataSetChanged();
        scrollbottom();
    }
    private void End_Chat()
    {

        addMessage(false,"","Alright i guess that's all, " + user.get_UserName());

        addMessage(false,"","You can call me anytime if you spend some money or if you want to ask me your spendings");

        addMessage(false,"","You also can change your call name, how much money for monthly allocation and see your spendings on the main screen");

        addChoices(CLOSE_CHAT,"Alright, thank you!");
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
            String time = "";
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
    final int GET_NAME = 10;
    final int GET_MONTHLY = 11;
    final int ALLOCATION_AGREE = 12;
    final int ALLOCATION_DISAGREE= 13;
    final int AGREE_LEFT_IS_RIGHT =  14;
    final int AGREE_LEFT_IS_NOT_RIGHT =  15;
    final int CLOSE_CHAT =  16;
    final int GET_DAY_REGIS_SPENDING = 17;
    final int ALLMONEYINFORECEIVED = 18;
    final int ASK_LEFT_AGREE = 19;
    final int END_BEFORE = 20;
    @Override
    public void changeName(String Name) {
        user.set_UserName(Name);
        clearChoice();
        addMessage(true,"","Call me " + Name);
        addMessage(false,"", user.get_UserName()+"? Alright " +user.get_UserName()+", now please give me some essensial information");
        ask_money_info();
        refresh_menu();
    }

    @Override
    public void MonthlyChangeIsYes(Double newMoney) {
        placeholder_monthly = newMoney;
        clearChoice();
        addMessage(true,"","For a Month? I Spend " + newMoney);
        how_much_should_user_spend();
        refresh_menu();
    }

}

