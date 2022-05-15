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
import com.google.firebase.auth.FirebaseAuth;
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
import android.widget.Toast;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddSpending extends AppCompatActivity {


    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private int year, month, day;
    private EditText money;
    private EditText description;
    private Spinner SpinnerTypeSpedding;
    private  ArrayList<TypeSpending> typeSpendings;
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private User user;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_spending);
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());

        TextView tvSaveBot = findViewById(R.id.tv_save_bot);
        money = findViewById(R.id.et_money);
        description = findViewById(R.id.et_description);

        addSpinner();
        tvSaveBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSpending( view);
            }
        });
        findViewById(R.id.tv_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSpending( view);
            }
        });

//        database

        database = FirebaseDatabase.getInstance();

        firebaseAuth = FirebaseAuth.getInstance();
        userRef = database.getReference("users").child(firebaseAuth.getUid());
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        cancel
        findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });

    }
    private void cancel(){
        super.onBackPressed();
    }
    private void saveSpending(View view){
        if(money.getText().toString().trim().equals("")){
            Toast toast = Toast.makeText(AddSpending.this, "Vui lòng nhập số tiền", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
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
        System.out.println(monthOfYear);
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
            LocalDate date = LocalDate.of(year, month, day);
            DayOfWeek dayow = date.getDayOfWeek();
            monthOfYear.getDateOfMonths().add(new DateOfMonth(day, dayOfWeek(dayow.getValue()), spendings));
        }

//        add speding //      default nạp tiền
        TypeSpending typeSpending =  typeSpendings.get(SpinnerTypeSpedding.getSelectedItemPosition());

        double moneyDouble = Double.parseDouble(money.getText().toString());
        if(typeSpending.getType()==0){
            // chi tiêu
            moneyDouble = -moneyDouble;
        }
        spendings.add(new Spending(new Date().getTime(),typeSpending.getName(), typeSpending.getImg(),description.getText().toString(), moneyDouble));
        super.onBackPressed();
        userRef.setValue(user);

    }
    private void addSpinner(){
        SpinnerTypeSpedding =  findViewById(R.id.typeSpedding);
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
        SpinnerTypeSpedding.setAdapter(adapter);
    }
    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        this.month = month;
        this.year = year;
        this.day = day;
        return makeDateString(day, month, year);

    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year1, int month1, int day1)
            {
                month1 = month1 + 1;
                String date = makeDateString(day1, month1, year1);
                dateButton.setText(date);
                year  = year1;
                month = month1;
                day = day1;
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());


    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
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
    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }
}