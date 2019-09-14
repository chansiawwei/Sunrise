package au.edu.swin.sdmd.suncalculatorjava;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.squareup.timessquare.CalendarPickerView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class date_picker extends AppCompatActivity {
    CalendarPickerView datePicker;
    Button button;
    Intent intentHome;
    Intent intentTable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);

        Date today = new Date();
        Calendar nextYear=Calendar.getInstance();
        nextYear.add(Calendar.YEAR,1);
        button=(Button)findViewById(R.id.button2);
        datePicker=findViewById(R.id.calendar);
        datePicker.init(today,nextYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .withSelectedDate(today);
        //Log.d("DATE@!!",datePicker.getSelectedDates().toString());
        datePicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
               // String seletedDate= DateFormat.getDateInstance(DateFormat.FULL).format(date);

                Calendar calSelected=Calendar.getInstance();
                calSelected.setTime(date);
                String selectedDate = "" + calSelected.get(Calendar.DAY_OF_MONTH)
                        + " " + (calSelected.get(Calendar.MONTH) + 1)
                        + " " + calSelected.get(Calendar.YEAR);

                Toast.makeText(getApplicationContext(), selectedDate, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.example_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.changeLocation) {
            //logout user

            Toast.makeText(getApplicationContext(), "GO BACK TO HOMEPAGE FIRST", Toast.LENGTH_SHORT).show();


        }
        else if(item.getItemId()==R.id.home){
            intentHome=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intentHome);
        }

        else if(item.getItemId()==R.id.generateTable){
            Toast.makeText(getApplicationContext(), "You are already on it.", Toast.LENGTH_SHORT).show();

        }

        else if(item.getItemId()==R.id.share){
            Log.d("Menu selected","sharing");
        }

        return super.onOptionsItemSelected(item);
    }

    public void generateTable(View view){
       // Log.d("DATE@!!",datePicker.getSelectedDates().toString());
       List<Date> a= datePicker.getSelectedDates();
        ArrayList<String> arrayListOfString = new ArrayList(a);

        intentTable=new Intent(getApplicationContext(),Table.class);
        intentTable.putExtra("alldates",arrayListOfString);
        startActivity(intentTable);

    }
}
