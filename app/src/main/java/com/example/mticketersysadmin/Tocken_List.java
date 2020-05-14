package com.example.mticketersysadmin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mticketersysadmin.adapter.RecyclerViewAdapterTocken;
import com.example.mticketersysadmin.data.DataBaseHandler;
import com.example.mticketersysadmin.model.TockenData;

import java.util.ArrayList;
import java.util.List;

public class Tocken_List extends AppCompatActivity {

    private RecyclerViewAdapterTocken recyclerViewAdapterTocken;
    private DataBaseHandler dataBaseHandler;
    private List<TockenData> tockenDataList;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tocken__list);

        recyclerView = findViewById(R.id.recycler_view_tocken);

        dataBaseHandler = new DataBaseHandler(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tockenDataList = new ArrayList<>();
        tockenDataList = dataBaseHandler.getAllTockens();

        recyclerViewAdapterTocken = new RecyclerViewAdapterTocken(this,tockenDataList);
        recyclerView.setAdapter(recyclerViewAdapterTocken);
        recyclerViewAdapterTocken.notifyDataSetChanged();
    }
}
