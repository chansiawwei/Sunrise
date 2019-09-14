package au.edu.swin.sdmd.suncalculatorjava;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import au.edu.swin.sdmd.suncalculatorjava.calc.AstronomicalCalendar;
import au.edu.swin.sdmd.suncalculatorjava.calc.GeoLocation;
import au.edu.swin.sdmd.suncalculatorjava.calc.SunCondition;

public class Table extends AppCompatActivity {
Intent intentHome;
Intent intentDateRange;
    Date srise;
    Date sset;
    SimpleDateFormat sdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        ArrayList<Date> alldatesList = (ArrayList<Date>) getIntent().getSerializableExtra("alldates");
        ListView listView=(ListView) findViewById(R.id.listview);
        ArrayList<SunCondition> sunConditionsList=new ArrayList<>();

        //copy from mainactivity
        ////////////////////////////////////////
//        DatePicker dp = findViewById(R.id.datePicker1);
//        Calendar cal = Calendar.getInstance();
//        int year = cal.get(Calendar.YEAR);
//        int month = cal.get(Calendar.MONTH);
//        int day = cal.get(Calendar.DAY_OF_MONTH);
//        updateTime(year, month, day);
        /////////////////////////////

        for(int i=0; i<alldatesList.size(); i++){
            updateTime(alldatesList.get(i).getYear(), alldatesList.get(i).getMonth(), alldatesList.get(i).getDay());
            sunConditionsList.add(new SunCondition(alldatesList.get(i),"Sunrise: "+ sdf.format(srise),"Sunset: "+sdf.format(sset)));
        }


        SunConditionListAdapter adapter=new SunConditionListAdapter(this,R.layout.adapter_view_layout,sunConditionsList);
        listView.setAdapter(adapter);
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

            Toast.makeText(getApplicationContext(), "Change location at homepage", Toast.LENGTH_SHORT).show();

            intentHome=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intentHome);

        }
        else if(item.getItemId()==R.id.home){
            intentHome=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intentHome);
        }
        else if(item.getItemId()==R.id.generateTable){
            Log.d("Menu selected","generate table");
            intentDateRange=new Intent(getApplicationContext(),date_picker.class);
            startActivity(intentDateRange);
        }

        else if(item.getItemId()==R.id.share){
            Log.d("Menu selected","sharing");
        }

        return super.onOptionsItemSelected(item);
    }
    private void updateTime(int year, int monthOfYear, int dayOfMonth) {
            TimeZone tz = TimeZone.getDefault();

            GeoLocation geolocation = new GeoLocation("melbourne", -37.814, 144.963, tz);
            AstronomicalCalendar ac = new AstronomicalCalendar(geolocation);
            ac.getCalendar().set(year, monthOfYear, dayOfMonth);
            srise = ac.getSunrise();
            sset  = ac.getSunset();
             sdf = new SimpleDateFormat("HH:mm");


    }
}
