package com.example.personalfinance.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.personalfinance.R;
import com.example.personalfinance.adapter.DateAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ListView listViewDate  = findViewById(R.id.listviewDate);
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        DateAdapter adapter = new DateAdapter(HomeActivity.this,R.layout.date_item,list);
        listViewDate.setAdapter(adapter);
    }
}