package au.edu.swin.sdmd.suncalculatorjava;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import au.edu.swin.sdmd.suncalculatorjava.calc.AstronomicalCalendar;
import au.edu.swin.sdmd.suncalculatorjava.calc.GeoLocation;

public class MainActivity extends AppCompatActivity {
    EditText input;
    Intent intentDateRange;
    Intent intentHome;
    AlertDialog ad;
    AlertDialog ad2;
    String cityName="";
    Double lat=0.0;
    Double longi=0.0;
    Date srise;
    Date sset;
    SimpleDateFormat sdf;
    TextView locationTV;
    int year;
    int month;
    int day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationTV =(TextView)findViewById(R.id.locationTV);

        initializeUI();
        //alert dialog to change location starts here
        final AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setTitle("Change Location");
        builder.setMessage("Enter major Australia Location to show sun rise/set time");

        input= new EditText(this);
        builder.setView(input);

        //Set positive Button
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                 cityName=input.getText().toString();
                if (cityName != null) {
                    switch (cityName) {
                        case "sydney":
                            lat = -33.868;
                            longi = 151.207;

                            initializeUI();
                            break;
                        case "melbourne":
                            lat = -37.814;
                            longi = 144.963;
                            locationTV.setText(cityName);
                            initializeUI();
                            break;
                        case "brisbane":
                            lat = -27.468;
                            longi = 153.028;
                            initializeUI();
                            break;
                        case "perth":
                            lat = -31.952;
                            longi = 115.861;
                            initializeUI();
                            break;

                        case "adelaide":
                            lat = -34.929;
                            longi = 138.599;
                            initializeUI();
                            break;

                        case "gold coast":
                            lat = -28.00;
                            longi = 115.861;
                            initializeUI();
                            break;

                        case "canberra":
                            lat = -35.283;
                            longi = 149.128;
                            initializeUI();
                            break;

                        case "newcastle":
                            lat = -32.93;
                            longi = 151.78;
                            initializeUI();
                            break;

                        case "wollongong":
                            lat = -34.424;
                            longi = 150.893;
                            initializeUI();
                            break;

                        case "logan":
                            lat = -27.639;
                            longi = 153.109;
                            initializeUI();
                            break;

                        case "geelong":
                            lat = -38.147;
                            longi = 144.361;
                            initializeUI();
                            break;

                        case "hobart":
                            lat = -42.879;
                            longi = 147.329;
                            initializeUI();
                            break;

                        default:
                            Toast.makeText(getApplicationContext(), "This city is unavailable", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        //Set Negative Button
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

         ad=builder.create();


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

            ad.show();


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
            if(Double.compare(lat,0.0)==0 && Double.compare(longi,0.0)==0){
                Toast.makeText(getApplicationContext(), "Please set the city name first", Toast.LENGTH_SHORT).show();

            }else {
                updateTime(year, month, day);
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "City Name: " + cityName +
                        " Sunrise: " + sdf.format(srise) +
                        " Sunset: " + sdf.format(sset));
//            sendIntent.putExtra(Intent.EXTRA_TEXT, "Sunrise:" +sdf.format(srise));
//            sendIntent.putExtra(Intent.EXTRA_TEXT, "Sunset:" +sdf.format(sset));
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void initializeUI() {
        DatePicker dp = findViewById(R.id.datePicker1);
        Calendar cal = Calendar.getInstance();
         year   = cal.get(Calendar.YEAR);
         month = cal.get(Calendar.MONTH);
         day   = cal.get(Calendar.DAY_OF_MONTH);
        dp.init(year,month,day,dateChangeHandler); // setup initial values and reg. handler
        updateTime(year, month, day);
    }

    private void updateTime(int year, int monthOfYear, int dayOfMonth) {

        if(Double.compare(lat,0.0)==0 && Double.compare(longi,0.0)==0){
            locationTV.setText("City Is Yet to be set...");
        }
        else{
            TimeZone tz = TimeZone.getDefault();
            locationTV.setText(cityName);
            GeoLocation geolocation = new GeoLocation(cityName, lat, longi, tz);
            AstronomicalCalendar ac = new AstronomicalCalendar(geolocation);
            ac.getCalendar().set(year, monthOfYear, dayOfMonth);
             srise = ac.getSunrise();
             sset  = ac.getSunset();

             sdf = new SimpleDateFormat("HH:mm");

            TextView sunriseTV = findViewById(R.id.sunriseTimeTV);
            TextView sunsetTV = findViewById(R.id.sunsetTimeTV);
            Log.d("SUNRISE Unformatted", srise+"");

            sunriseTV.setText(sdf.format(srise));
            sunsetTV.setText(sdf.format(sset));
        }

    }

    DatePicker.OnDateChangedListener dateChangeHandler = new DatePicker.OnDateChangedListener()
    {
        public void onDateChanged(DatePicker dp, int year, int monthOfYear, int dayOfMonth)
        {
            updateTime(year, monthOfYear, dayOfMonth);
        }
    };


}
