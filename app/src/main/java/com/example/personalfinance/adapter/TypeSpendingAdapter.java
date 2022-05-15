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
import com.example.personalfinance.entity.Spending;
import com.example.personalfinance.entity.TypeSpending;

import java.text.DecimalFormat;
import java.util.List;

public class TypeSpendingAdapter extends BaseAdapter {
    private Context context;
    private int idLayout;
    private List<TypeSpending> typeSpendings;

    public TypeSpendingAdapter(Context context, int idLayout, List<TypeSpending> typeSpendings) {
        this.context = context;
        this.idLayout = idLayout;
        this.typeSpendings = typeSpendings;
    }

    @Override
    public int getCount() {
       return typeSpendings.size();
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
        TextView txtName = convertView.findViewById(R.id.tv_name);
        ImageView img = convertView.findViewById(R.id.imageTypeSpending);

        TypeSpending typeSpending = typeSpendings.get(position);

        txtName.setText(typeSpending.getName());
        img.setImageResource(typeSpending.getImg());

        return convertView;
    }
}
