package com.example.personalfinance.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.personalfinance.R;
import com.example.personalfinance.activity.UpdateSpending;
import com.example.personalfinance.entity.DateOfMonth;
import com.example.personalfinance.entity.MonthOfYear;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class DateAdapter extends BaseAdapter {
    private Context context;
    private int idLayout;
    private MonthOfYear monthOfYear;

    public DateAdapter(Context context, int idLayout, MonthOfYear monthOfYear) {
        this.context = context;
        this.idLayout = idLayout;
        this.monthOfYear = monthOfYear;
    }

    @Override
    public int getCount() {
        if (monthOfYear == null || monthOfYear.getDateOfMonths()==null || monthOfYear.getDateOfMonths().isEmpty())
            return 0;
        return monthOfYear.getDateOfMonths().size();
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

        TextView txtDate = convertView.findViewById(R.id.txtItemDate_Date);
        TextView txtDay = convertView.findViewById(R.id.txtItemDate_Day);
        TextView txtMonth = convertView.findViewById(R.id.txtItemDate_MonthYear);
        TextView txtTotal = convertView.findViewById(R.id.txtItemDate_TotalMoney);

        if (monthOfYear != null && monthOfYear.getDateOfMonths()!=null && !monthOfYear.getDateOfMonths().isEmpty()){
            DecimalFormat formatter = new DecimalFormat("###,###,###");

            DateOfMonth date = monthOfYear.getDateOfMonths().get(position);
            txtDate.setText(date.getDate()+"");
            txtDay.setText(date.getDay());
            txtMonth.setText("Tháng " + monthOfYear.getMonth() + " " + monthOfYear.getYear());
            if (date.totalMoneyInDate()>0){
                txtTotal.setText("+"+formatter.format(date.totalMoneyInDate()));
            }else{
                txtTotal.setText(formatter.format(date.totalMoneyInDate()));
            }

            if (date.getSpendings() != null && !date.getSpendings().isEmpty()){
                ListView listViewSpending = convertView.findViewById(R.id.listviewSpending);
                ViewGroup.LayoutParams params = listViewSpending.getLayoutParams();
                params.height = 196*date.getSpendings().size();
                SpendingAdapter spendingAdapter = new SpendingAdapter(parent.getContext(),R.layout.spending_item,date.getSpendings());
                listViewSpending.setAdapter(spendingAdapter);
                listViewSpending.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        System.out.println(i);

//                        Intent intent = new Intent(context.this, UpdateSpending.class);
                    }
                });
            }
        }


        return convertView;
    }
}
