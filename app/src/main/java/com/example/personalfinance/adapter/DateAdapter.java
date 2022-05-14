package com.example.personalfinance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.personalfinance.R;

import java.util.ArrayList;
import java.util.List;

public class DateAdapter extends BaseAdapter {
    private Context context;
    private int idLayout;
    private List<String> list;

    public DateAdapter(Context context, int idLayout, List<String> list) {
        this.context = context;
        this.idLayout = idLayout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(idLayout,parent,false);
        }
        ListView listViewSpending = convertView.findViewById(R.id.listviewSpending);
        List<String> list1 = new ArrayList<>();
        list1.add("1");
        list1.add("2");
        list1.add("3");
        list1.add("1");
        list1.add("2");
        list1.add("3");
        list1.add("1");
        list1.add("2");
        list1.add("3");
        ViewGroup.LayoutParams params = listViewSpending.getLayoutParams();
        params.height = 225*list1.size();
        SpendingAdapter spendingAdapter = new SpendingAdapter(parent.getContext(),R.layout.spending_item,list1);
        listViewSpending.setAdapter(spendingAdapter);
        return convertView;
    }
}
