package au.edu.swin.sdmd.suncalculatorjava;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import au.edu.swin.sdmd.suncalculatorjava.calc.SunCondition;

public class SunConditionListAdapter extends ArrayAdapter<SunCondition> {

    private Context mcontext;
    int mResouce;
    public SunConditionListAdapter(Context context, int resource, ArrayList<SunCondition> objects){
    super(context,resource,objects);
    mcontext=context;
    mResouce=resource;
    }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        Date date=getItem(position).getDate();
        String sunrise=getItem(position).getSunrise();
        String sunset=getItem(position).getSunset();

        SunCondition suncondition=new SunCondition(date,sunrise,sunset);
        LayoutInflater inflater =LayoutInflater.from(mcontext);
        convertView=inflater.inflate(mResouce,parent,false);

        TextView tvdate=(TextView)convertView.findViewById(R.id.textView1);
        TextView tvsunrise=(TextView)convertView.findViewById(R.id.textView2);
        TextView tvsunset=(TextView)convertView.findViewById(R.id.textView3);
        tvdate.setText(date.toString());
        tvsunrise.setText(sunrise);
        tvsunset.setText(sunset);

        return  convertView;
    }
}
