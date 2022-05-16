package com.example.personalfinance.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.personalfinance.R;
import com.example.personalfinance.adapter.TypeSpendingAdapter;
import com.example.personalfinance.database.Database;
import com.example.personalfinance.entity.DateOfMonth;
import com.example.personalfinance.entity.MonthOfYear;
import com.example.personalfinance.entity.Spending;
import com.example.personalfinance.entity.TypeSpending;
import com.example.personalfinance.entity.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public  class UpdateSpending extends AppCompatActivity {

    private int year, month, day;
    private EditText money;
    private EditText description;
    private Spinner spinnerTypeSpending;
    private  ArrayList<TypeSpending> typeSpendings;
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private User user;
    private Spending spendingOld;
    private TextView tv_date;
    private FirebaseAuth firebaseAuth;

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
                showCofirm();
            }
        });
        findViewById(R.id.tv_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSpending(view);
            }
        });

//        database
        database = Database.getIntance();

        firebaseAuth = FirebaseAuth.getInstance();
        userRef = database.getReference("users").child(firebaseAuth.getUid());
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
        //        cancel
        findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });


        // set date in lable
        LocalDate date = LocalDate.of(year, month, day);
        DayOfWeek dayow = date.getDayOfWeek();
        tv_date = findViewById(R.id.tv_date);
        tv_date.setText(dayOfWeek(dayow.getValue())+" ngày "+day+" tháng "+ month +" năm "+year);
    }
    private void cancel(){
        super.onBackPressed();
    }
    private void showData(){
        money.setText(String.format("%.0f", Math.abs(spendingOld.getMoney())));
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

    public void showCofirm() {
        new AlertDialog.Builder(this)
                .setMessage("Xác nhận xóa chi tiêu")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteSpending();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
    private void deleteSpending(){
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
        if(money.getText().toString().trim().equals("")){
            Toast toast = Toast.makeText(UpdateSpending.this, "Vui lòng nhập số tiền", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
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
                spending.setImg(typeSpending.getImgName());
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
        typeSpendings.add(new TypeSpending( "Di chuyển", R.drawable.emojione_v1_motorcycle, 0));
        typeSpendings.add(new TypeSpending( "Thuê nhà", R.drawable.flat_color_icons_home, 0));
        typeSpendings.add(new TypeSpending( "Tiền điện, nước, gas...", R.drawable.icon_park_database_power, 0));
        typeSpendings.add(new TypeSpending( "Đồ dùng, thiết bị", R.drawable.icon_park_weixin_market, 0));
        typeSpendings.add(new TypeSpending( "Vui chơi", R.drawable.noto_man_playing_water_polo, 0));
        typeSpendings.add(new TypeSpending( "Chi tiêu khác", R.drawable.icon_park_more_two, 0));
        typeSpendings.add(new TypeSpending( "Tiền vào", R.drawable.emojione_atm_sign, 1));
        TypeSpendingAdapter adapter = new TypeSpendingAdapter(this, R.layout.type_speding_item, typeSpendings);
        spinnerTypeSpending.setAdapter(adapter);
    }
    public String dayOfWeek(int i){
        switch ( i ) {
            case  1: return "Thứ hai";
            case  2: return "Thứ ba";
            case  3: return "Thứ tư";
            case  4: return "Thứ năm";
            case  5: return "Thứ sáu";
            case  6: return "Thứ bảy";
            case  7: return "Chủ nhật";
            default: return "Chủ nhật";
        }
    }

}