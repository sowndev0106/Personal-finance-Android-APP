package com.example.personalfinance.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.personalfinance.R;
import com.example.personalfinance.adapter.TypeSpendingAdapter;
import com.example.personalfinance.entity.DateOfMonth;
import com.example.personalfinance.entity.MonthOfYear;
import com.example.personalfinance.entity.Spending;
import com.example.personalfinance.entity.TypeSpending;
import com.example.personalfinance.entity.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private Spinner spinnerTypeSpending;
    private  ArrayList<TypeSpending> typeSpendings;
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private User user;
    private Spending spendingOld;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_spending);
        TextView tvSaveBot = findViewById(R.id.tv_save_bot);
        money = findViewById(R.id.et_money);
        description = findViewById(R.id.et_description);

        addSpinner();
        findViewById(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSpending(view);
            }
        });
        findViewById(R.id.tv_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSpending(view);
            }
        });

//        database
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users").child("QvDrtYaWYOSiONP3u25ivw7Wp5a2");
        spendingOld = (Spending) getIntent().getSerializableExtra("spendingOld");
        month = (int) getIntent().getSerializableExtra("month");
        year = (int) getIntent().getSerializableExtra("year");
        day = (int) getIntent().getSerializableExtra("day");
        System.out.println(month+ " = " + year +  " - " +day);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        showData();
    }
    private void showData(){
        money.setText(Math.abs(spendingOld.getMoney())+"");
        description.setText(spendingOld.getNote());
        int index = 0;
        for(TypeSpending typeSpending:typeSpendings){
            if(typeSpending.getName().equalsIgnoreCase(spendingOld.getType())){
                break;
            }
            index++;
        }

        spinnerTypeSpending.setSelection(index);

    }
    private void deleteSpending(View view){
        if(user.getMonthOfYears() == null){
            user.setMonthOfYears(new ArrayList<>());
        }
        List<Spending> spendingNew = new ArrayList<>();

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
                spendingNew.add(spending);
            }
        }
        dateOfMonthOld.setSpendings(spendingNew);
        System.out.println(dateOfMonthOld.getSpendings());
        userRef.setValue(user);
        super.onBackPressed();
    }

    private void updateSpending(View view){
//        loop month of year
        int lengthMonth = user.getMonthOfYears().size();
        MonthOfYear monthOfYear = null;
        for (int i = 0; i <lengthMonth ; i++) {
            if( user.getMonthOfYears().get(i).getYear() == year  &&  user.getMonthOfYears().get(i).getMonth() == month ){
                monthOfYear =  user.getMonthOfYears().get(i);
            }
        }
//        loop date of month
        int lengthDate = monthOfYear.getDateOfMonths().size();
        List<Spending> spendings = null;
        for (int i = 0; i <lengthDate ; i++) {
            if( monthOfYear.getDateOfMonths().get(i).getDate() == day ){
                spendings =  monthOfYear.getDateOfMonths().get(i).getSpendings();
            }
        }

//        add speding //
        TypeSpending typeSpending =  this.typeSpendings.get(spinnerTypeSpending.getSelectedItemPosition());

        double moneyDouble = Double.parseDouble(money.getText().toString());
        if(typeSpending.getType()==0){
            // chi tiêu
            moneyDouble = -moneyDouble;
        }

        for (Spending spending: spendings){
            if(spending.getId() == spendingOld.getId()){
                spending.setImg(typeSpending.getImg());
                spending.setType(typeSpending.getName());
                spending.setNote(description.getText().toString());
                spending.setMoney(moneyDouble);
            }
        }
        super.onBackPressed();  
        userRef.setValue(user);

    }
    private void addSpinner(){
        spinnerTypeSpending =  findViewById(R.id.typeSpedding);
        typeSpendings = new ArrayList();
        typeSpendings.add(new TypeSpending( "Ăn uống",R.drawable.noto_pot_of_food, 0));
        typeSpendings.add(new TypeSpending( "Di chuyển",R.drawable.noto_pot_of_food, 0));
        typeSpendings.add(new TypeSpending( "Thuê nhà",R.drawable.noto_pot_of_food, 0));
        typeSpendings.add(new TypeSpending( "Tiền điện, nước, gas...",R.drawable.noto_pot_of_food, 0));
        typeSpendings.add(new TypeSpending( "Nạp tiền",R.drawable.noto_pot_of_food, 1));
        TypeSpendingAdapter adapter = new TypeSpendingAdapter(this, R.layout.type_speding_item, typeSpendings);
        spinnerTypeSpending.setAdapter(adapter);
    }


}