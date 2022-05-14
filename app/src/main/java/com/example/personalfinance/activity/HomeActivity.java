package com.example.personalfinance.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.personalfinance.R;
import com.example.personalfinance.adapter.DateAdapter;
import com.example.personalfinance.entity.DateOfMonth;
import com.example.personalfinance.entity.MonthOfYear;
import com.example.personalfinance.entity.Spending;
import com.example.personalfinance.entity.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private TextView txtTotalMoney;
    private TextView txtMonthInMoney;
    private TextView txtMonthOutMoney;
    private TextView txtMonthBefore;
    private TextView txtMonthAfter;
    private TextView txtMonthTotalMoney;
    private ListView listViewDate;
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private User user;
    private MonthOfYear monthOfYear;
    private  DateAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txtTotalMoney = findViewById(R.id.txtTotalMoney);
        txtMonthInMoney = findViewById(R.id.txtMonthInMoney);
        txtMonthOutMoney = findViewById(R.id.txtMonthOutMoney);
        txtMonthAfter = findViewById(R.id.txtMonthAfter);
        txtMonthBefore = findViewById(R.id.txtMonthBefore);
        txtMonthTotalMoney = findViewById(R.id.txtMonthTotalMoney);
        listViewDate  = findViewById(R.id.listviewDate);

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users").child("QvDrtYaWYOSiONP3u25ivw7Wp5a2");

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loadDataFromFirebase(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadDataFromFirebase(DataSnapshot snapshot){
       user = snapshot.getValue(User.class);
       monthOfYear = user.getMonthOfYears().get(user.getMonthOfYears().size()-1);
       adapter = new DateAdapter(HomeActivity.this,R.layout.date_item,monthOfYear);
       listViewDate.setAdapter(adapter);

       double total = 0;
       DecimalFormat formatter = new DecimalFormat("###,###,###");


       txtTotalMoney.setText(formatter.format(user.totalMoney()) + " đ");
       txtMonthInMoney.setText("+" +formatter.format(user.totalInMoney()) + " đ");
       txtMonthOutMoney.setText(formatter.format(user.totalOutMoney()) + " đ");
       double totalMonth = monthOfYear.totalInMoneyInMonth() + monthOfYear.totalOutMoneyInMonth();
       if (totalMonth>=0){
           txtMonthTotalMoney.setText("+" + formatter.format(totalMonth) + " đ");
       }else{
           txtMonthTotalMoney.setText(formatter.format(totalMonth) + " đ");
       }

    }

}