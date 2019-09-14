package au.edu.swin.sdmd.suncalculatorjava.calc;

import java.util.Date;

public class SunCondition {
    private Date date;
    private  String sunrise;
    private  String sunset;


    public SunCondition(Date date,String sunrise, String sunset){
        this.date=date;
        this.sunrise=sunrise;
        this.sunset=sunset;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }
}
