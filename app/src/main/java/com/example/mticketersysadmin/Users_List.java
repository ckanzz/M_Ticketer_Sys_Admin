package com.example.mticketersysadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mticketersysadmin.adapter.RecyclerViewAdapterUser;
import com.example.mticketersysadmin.data.DataBaseHandler;
import com.example.mticketersysadmin.model.UserData;

import java.util.ArrayList;
import java.util.List;

public class Users_List extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DataBaseHandler dataBaseHandler;
    private List<UserData> userDataList;
    private RecyclerViewAdapterUser recyclerViewAdapterUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users__list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycler_view_user);
        dataBaseHandler = new DataBaseHandler(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userDataList = new ArrayList<>();
        userDataList = dataBaseHandler.getAllUsers();

        recyclerViewAdapterUser = new RecyclerViewAdapterUser(this,userDataList);
        recyclerView.setAdapter(recyclerViewAdapterUser);
        recyclerViewAdapterUser.notifyDataSetChanged();
    }
}
