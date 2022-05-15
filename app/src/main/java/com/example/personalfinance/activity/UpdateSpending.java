package com.example.personalfinance.activity;

import androidx.appcompat.app.AppCompatActivity;
import com.example.personalfinance.R;
import com.example.personalfinance.adapter.TypeSpendingAdapter;
import com.example.personalfinance.entity.DateOfMonth;
import com.example.personalfinance.entity.MonthOfYear;
import com.example.personalfinance.entity.Spending;
import com.example.personalfinance.entity.TypeSpending;
import com.example.personalfinance.entity.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public  class UpdateSpending extends AppCompatActivity {


    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private int year, month, day;
    private EditText money;
    private EditText description;
    private Spinner SpinnerTypeSpending;
    private  ArrayList<TypeSpending> typeSpending;
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private User user;
    private Spending spendingOld;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_spending);
        getTodaysDate();
        TextView tvSaveBot = findViewById(R.id.tv_save_bot);
        money = findViewById(R.id.et_money);
        description = findViewById(R.id.et_description);

        addSpinner();
        tvSaveBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSpending( view);
            }
        });
        findViewById(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSpending(view);
            }
        });

//        database

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users").child("QvDrtYaWYOSiONP3u25ivw7Wp5a2");
        user = (User) getIntent().getSerializableExtra("user");
        spendingOld = (Spending) getIntent().getSerializableExtra("spendingOld");


    }
    private void deleteSpending(View view){
        if(user.getMonthOfYears() == null){
            user.setMonthOfYears(new ArrayList<>());
        }
        List<Spending> spendingsNew = new ArrayList<>();

        int lengthMonth = user.getMonthOfYears().size();
        MonthOfYear monthOfYear = null;

        for (int i = 0; i <lengthMonth ; i++) {
            if( user.getMonthOfYears().get(i).getYear() == year  &&  user.getMonthOfYears().get(i).getMonth() == month ){
                monthOfYear =  user.getMonthOfYears().get(i);
                break;
            }
        }
        DateOfMonth dateOfMonthOld = null;
        for (DateOfMonth dateOfMonth: monthOfYear.getDateOfMonths()){
            if(dateOfMonth.getDate() == day){
                dateOfMonthOld = dateOfMonth;
            }
        }
        for (Spending spending: dateOfMonthOld.getSpendings()){
            // remove element
            if(spending.getId() != spendingOld.getId()){
                spendingsNew.add(spending);
            }
        }
        dateOfMonthOld.setSpendings(spendingsNew);
        userRef.setValue(user);
        super.onBackPressed();
    }

    private void updateSpending(View view){
//        loop month of year
        if(user.getMonthOfYears() == null){
            user.setMonthOfYears(new ArrayList<>());
        }

        int lengthMonth = user.getMonthOfYears().size();
        MonthOfYear monthOfYear = null;
        for (int i = 0; i <lengthMonth ; i++) {
            if( user.getMonthOfYears().get(i).getYear() == year  &&  user.getMonthOfYears().get(i).getMonth() == month ){
                monthOfYear =  user.getMonthOfYears().get(i);
            }
        }
//       Create new Month of year if not exits
        if(monthOfYear == null){
            monthOfYear =  new MonthOfYear(year,month,new ArrayList<>());
            user.getMonthOfYears().add(monthOfYear);
        }
//        loop date of month
        int lengthDate = monthOfYear.getDateOfMonths().size();
        List<Spending> spendings = null;
        for (int i = 0; i <lengthDate ; i++) {
            if( monthOfYear.getDateOfMonths().get(i).getDate() == day ){
                spendings =  monthOfYear.getDateOfMonths().get(i).getSpendings();
            }
        }
        if(spendings == null){
            spendings = new ArrayList<>();
            monthOfYear.getDateOfMonths().add(new DateOfMonth(day, "Chủ nhật", spendings));
        }

//        add speding //
        TypeSpending typeSpending =  this.typeSpending.get(SpinnerTypeSpending.getSelectedItemPosition());

        double moneyDouble = Double.parseDouble(money.getText().toString());
        if(typeSpending.getType()==0){
            // chi tiêu
            moneyDouble = -moneyDouble;
        }
        spendings.add(new Spending(new Date().getTime(),typeSpending.getName(), typeSpending.getImg(),description.getText().toString(), moneyDouble));
        System.out.println(monthOfYear);
        userRef.setValue(user);

    }
    private void addSpinner(){
        SpinnerTypeSpending =  findViewById(R.id.typeSpedding);
        typeSpending = new ArrayList();
        typeSpending.add(new TypeSpending( "Ăn uống",R.drawable.noto_pot_of_food, 0));
        typeSpending.add(new TypeSpending( "Di chuyển",R.drawable.noto_pot_of_food, 0));
        typeSpending.add(new TypeSpending( "Thuê nhà",R.drawable.noto_pot_of_food, 0));
        typeSpending.add(new TypeSpending( "Tiền điện, nước, gas...",R.drawable.noto_pot_of_food, 0));
        typeSpending.add(new TypeSpending( "Nạp tiền",R.drawable.noto_pot_of_food, 1));
        TypeSpendingAdapter adapter = new TypeSpendingAdapter(this, R.layout.type_speding_item, typeSpending);
        SpinnerTypeSpending.setAdapter(adapter);
    }
    private void getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        this.month = month;
        this.year = year;
        this.day = day;
    }

}