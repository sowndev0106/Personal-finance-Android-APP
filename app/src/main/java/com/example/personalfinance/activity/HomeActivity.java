package com.example.personalfinance.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    private TextView txtTotalMoney;
    private TextView txtMonthInMoney;
    private TextView txtMonthOutMoney;
    private TextView txtMonthBefore;
    private TextView txtMonthAfter;
    private TextView txtMonthNow;
    private ImageView imgNext;
    private ImageView imgPrevious;
    private TextView txtMonthTotalMoney;
    private ListView listViewDate;
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private User user;
    private MonthOfYear monthOfYear;
    private DateAdapter adapter;
    private int indexMonth = -1;
    private DecimalFormat formatter;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        txtTotalMoney = findViewById(R.id.txtTotalMoney);
        txtMonthInMoney = findViewById(R.id.txtMonthInMoney);
        txtMonthOutMoney = findViewById(R.id.txtMonthOutMoney);
        txtMonthAfter = findViewById(R.id.txtMonthAfter);
        txtMonthBefore = findViewById(R.id.txtMonthBefore);
        txtMonthNow = findViewById(R.id.txtMonthNow);
        txtMonthTotalMoney = findViewById(R.id.txtMonthTotalMoney);
        listViewDate  = findViewById(R.id.listviewDate);
        imgNext = findViewById(R.id.imageNext);
        imgPrevious = findViewById(R.id.imagePrevious);
        spinner  = findViewById(R.id.spinnerLanguage);

        formatter = new DecimalFormat("###,###,###");


        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users").child("QvDrtYaWYOSiONP3u25ivw7Wp5a2");

//
//        List<Spending> spendings = new ArrayList<>();
//        spendings.add(new Spending(new Date().getTime(),"Ăn uống", R.drawable.noto_pot_of_food,"Hủ tiếu",-20000));
//        spendings.add(new Spending(new Date().getTime(),"Ăn uống", R.drawable.noto_pot_of_food,"Hủ tiếu",-20000));
//        DateOfMonth date10_5 = new DateOfMonth(10,"Thứ ba",spendings);
//
//        spendings = new ArrayList<>();
//        spendings.add(new Spending(new Date().getTime(),"Ăn uống", R.drawable.noto_pot_of_food,"Hủ tiếu",-20000));
//        spendings.add(new Spending(new Date().getTime(),"Ăn uống", R.drawable.noto_pot_of_food,"Hủ tiếu",-20000));
//        DateOfMonth date11_5 = new DateOfMonth(11,"Thứ tư",spendings);
//
//        spendings = new ArrayList<>();
//        spendings.add(new Spending(new Date().getTime(),"Ăn uống", R.drawable.noto_pot_of_food,"Hủ tiếu",-20000));
//        spendings.add(new Spending(new Date().getTime(),"Ăn uống", R.drawable.noto_pot_of_food,"Hủ tiếu",-20000));
//        DateOfMonth date12_5 = new DateOfMonth(12,"Thứ năm",spendings);
//
//        spendings = new ArrayList<>();
//        spendings.add(new Spending(new Date().getTime(),"Thu nhập", R.drawable.flat_color_icons_money_transfer,"Lãnh lương",200000));
//        spendings.add(new Spending(new Date().getTime(),"Thu nhập", R.drawable.flat_color_icons_money_transfer,"Lãnh lương",200000));
//        DateOfMonth date13_5 = new DateOfMonth(13,"Thứ sáu",spendings);
//
//        spendings = new ArrayList<>();
//        spendings.add(new Spending(new Date().getTime(),"Ăn uống", R.drawable.noto_pot_of_food,"Hủ tiếu",-20000));
//        spendings.add(new Spending(new Date().getTime(),"Ăn uống", R.drawable.noto_pot_of_food,"Hủ tiếu",-20000));
//        DateOfMonth date14_5 = new DateOfMonth(14,"Thứ bảy",spendings);
//
//        List<DateOfMonth> months5s = new ArrayList<>();
//        months5s.add(date10_5);
//        months5s.add(date11_5);
//        months5s.add(date12_5);
//        months5s.add(date13_5);
//        months5s.add(date14_5);
//
//        /////
//        spendings = new ArrayList<>();
//        spendings.add(new Spending(new Date().getTime(),"Ăn uống", R.drawable.noto_pot_of_food,"Hủ tiếu",-20000));
//        spendings.add(new Spending(new Date().getTime(),"Ăn uống", R.drawable.noto_pot_of_food,"Hủ tiếu",-20000));
//        date10_5 = new DateOfMonth(10,"Thứ ba",spendings);
//
//        spendings = new ArrayList<>();
//        spendings.add(new Spending(new Date().getTime(),"Ăn uống", R.drawable.noto_pot_of_food,"Hủ tiếu",-20000));
//        spendings.add(new Spending(new Date().getTime(),"Ăn uống", R.drawable.noto_pot_of_food,"Hủ tiếu",-20000));
//        date11_5 = new DateOfMonth(11,"Thứ tư",spendings);
//
//        spendings = new ArrayList<>();
//        spendings.add(new Spending(new Date().getTime(),"Ăn uống", R.drawable.noto_pot_of_food,"Hủ tiếu",-20000));
//        spendings.add(new Spending(new Date().getTime(),"Ăn uống", R.drawable.noto_pot_of_food,"Hủ tiếu",-20000));
//        date12_5 = new DateOfMonth(12,"Thứ năm",spendings);
//
//        spendings = new ArrayList<>();
//        spendings.add(new Spending(new Date().getTime(),"Thu nhập", R.drawable.flat_color_icons_money_transfer,"Lãnh lương",200000));
//        spendings.add(new Spending(new Date().getTime(),"Thu nhập", R.drawable.flat_color_icons_money_transfer,"Lãnh lương",200000));
//        date13_5 = new DateOfMonth(13,"Thứ sáu",spendings);
//
//        spendings = new ArrayList<>();
//        spendings.add(new Spending(new Date().getTime(),"Ăn uống", R.drawable.noto_pot_of_food,"Hủ tiếu",-20000));
//        spendings.add(new Spending(new Date().getTime(),"Ăn uống", R.drawable.noto_pot_of_food,"Hủ tiếu",-20000));
//        date14_5 = new DateOfMonth(14,"Thứ bảy",spendings);
//
//        List<DateOfMonth> month4s = new ArrayList<>();
//        month4s.add(date10_5);
//        month4s.add(date11_5);
//        month4s.add(date12_5);
//        month4s.add(date13_5);
//        month4s.add(date14_5);
//
//        /////
//        spendings = new ArrayList<>();
//        spendings.add(new Spending(new Date().getTime(),"Ăn uống", R.drawable.noto_pot_of_food,"Hủ tiếu",-20000));
//        spendings.add(new Spending(new Date().getTime(),"Ăn uống", R.drawable.noto_pot_of_food,"Hủ tiếu",-20000));
//        date10_5 = new DateOfMonth(10,"Thứ ba",spendings);
//
//        spendings = new ArrayList<>();
//        spendings.add(new Spending(new Date().getTime(),"Ăn uống", R.drawable.noto_pot_of_food,"Hủ tiếu",-20000));
//        spendings.add(new Spending(new Date().getTime(),"Ăn uống", R.drawable.noto_pot_of_food,"Hủ tiếu",-20000));
//        date11_5 = new DateOfMonth(11,"Thứ tư",spendings);
//
//        spendings = new ArrayList<>();
//        spendings.add(new Spending(new Date().getTime(),"Ăn uống", R.drawable.noto_pot_of_food,"Hủ tiếu",-20000));
//        spendings.add(new Spending(new Date().getTime(),"Ăn uống", R.drawable.noto_pot_of_food,"Hủ tiếu",-20000));
//        date12_5 = new DateOfMonth(12,"Thứ năm",spendings);
//
//        spendings = new ArrayList<>();
//        spendings.add(new Spending(new Date().getTime(),"Thu nhập", R.drawable.flat_color_icons_money_transfer,"Lãnh lương",200000));
//        spendings.add(new Spending(new Date().getTime(),"Thu nhập", R.drawable.flat_color_icons_money_transfer,"Lãnh lương",200000));
//        date13_5 = new DateOfMonth(13,"Thứ sáu",spendings);
//
//        spendings = new ArrayList<>();
//        spendings.add(new Spending(new Date().getTime(),"Ăn uống", R.drawable.noto_pot_of_food,"Hủ tiếu",-20000));
//        spendings.add(new Spending(new Date().getTime(),"Ăn uống", R.drawable.noto_pot_of_food,"Hủ tiếu",-20000));
//        date14_5 = new DateOfMonth(14,"Thứ bảy",spendings);
//
//        List<DateOfMonth> month3s = new ArrayList<>();
//        month3s.add(date10_5);
//        month3s.add(date11_5);
//        month3s.add(date12_5);
//        month3s.add(date13_5);
//        month3s.add(date14_5);
//
//        MonthOfYear month3_2022 = new MonthOfYear(2022,3,months5s);
//        MonthOfYear month4_2022 = new MonthOfYear(2022,4,months5s);
//        MonthOfYear month5_2022 = new MonthOfYear(2022,5,months5s);
//
//        User user1 = new User("Trung Ngoc", Arrays.asList(month3_2022,month4_2022,month5_2022));
//
//        userRef.setValue(user1);


        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loadDataFromFirebase(snapshot);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        imgPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexMonth --;
                loadDataInMonth();
                if (indexMonth == 0){
                    imgPrevious.setEnabled(false);
                }
                imgNext.setEnabled(true);
            }
        });

        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexMonth ++;
                loadDataInMonth();
                if (indexMonth == user.getMonthOfYears().size()-1){
                    imgNext.setEnabled(false);
                }
                imgPrevious.setEnabled(true);
            }
        });


    }

    private void loadDataFromFirebase(DataSnapshot snapshot){
       user = snapshot.getValue(User.class);
       indexMonth  = user.getMonthOfYears().size()-1;

       imgNext.setEnabled(false);


       if (indexMonth>=0){
           double total = 0;

           txtTotalMoney.setText(formatter.format(user.totalMoney()) + " đ");

           loadDataInMonth();
       }

    }

    private void loadDataInMonth() {

        if (indexMonth>0){
            imgPrevious.setEnabled(true);
            txtMonthBefore.setText(user.getMonthOfYears().get(indexMonth-1).getMonth()+ "/" + user.getMonthOfYears().get(indexMonth-1).getYear());
        }
        else {
            txtMonthBefore.setText("");
            imgPrevious.setEnabled(false);
        }

        if (indexMonth<user.getMonthOfYears().size()-1){
            txtMonthAfter.setText(user.getMonthOfYears().get(indexMonth+1).getMonth()+ "/" + user.getMonthOfYears().get(indexMonth+1).getYear());
        }else{
            imgNext.setEnabled(false);
            txtMonthAfter.setText("");
        }

        monthOfYear = user.getMonthOfYears().get(indexMonth);

        txtMonthNow.setText(monthOfYear.getMonth()+ "/" + monthOfYear.getYear());
        txtMonthInMoney.setText("+" +formatter.format(monthOfYear.totalInMoneyInMonth()) + " đ");
        txtMonthOutMoney.setText(formatter.format(monthOfYear.totalOutMoneyInMonth()) + " đ");
        double totalMonth = monthOfYear.totalInMoneyInMonth() + monthOfYear.totalOutMoneyInMonth();
        if (totalMonth>=0){
            txtMonthTotalMoney.setText("+" + formatter.format(totalMonth) + " đ");
        }else{
            txtMonthTotalMoney.setText(formatter.format(totalMonth) + " đ");
        }
        adapter = new DateAdapter(HomeActivity.this,R.layout.date_item,monthOfYear);
        listViewDate.setAdapter(adapter);
    }


}