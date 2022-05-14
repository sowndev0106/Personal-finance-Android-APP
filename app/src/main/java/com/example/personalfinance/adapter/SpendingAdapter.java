package com.example.personalfinance.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.personalfinance.R;
import com.example.personalfinance.entity.MonthOfYear;
import com.example.personalfinance.entity.Spending;

import java.text.DecimalFormat;
import java.util.List;

public class SpendingAdapter extends BaseAdapter {
    private Context context;
    private int idLayout;
    private List<Spending> spendings;

    public SpendingAdapter(Context context, int idLayout, List<Spending> spendings) {
        this.context = context;
        this.idLayout = idLayout;
        this.spendings = spendings;
    }

    @Override
    public int getCount() {
       return spendings.size();
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

        TextView txtType = convertView.findViewById(R.id.txtItemSpending_Type);
        TextView txtNote = convertView.findViewById(R.id.txtItemSpending_Note);
        TextView txtMoney = convertView.findViewById(R.id.txtItemSpending_Money);
        ImageView img = convertView.findViewById(R.id.imageItemSpending);

        Spending spending = spendings.get(position);

        DecimalFormat formatter = new DecimalFormat("###,###,###");

        txtType.setText(spending.getType());
        txtNote.setText(spending.getNote());
        txtMoney.setText(formatter.format(Math.abs(spending.getMoney())));
        if (spending.getMoney()<0){
            txtMoney.setTextColor(Color.RED);
        }else{
            txtMoney.setTextColor(Color.WHITE);
        }
        img.setImageResource(spending.getImg());

        return convertView;
    }
}
