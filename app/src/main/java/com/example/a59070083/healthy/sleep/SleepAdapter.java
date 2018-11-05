package com.example.a59070083.healthy.sleep;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.a59070083.healthy.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SleepAdapter extends ArrayAdapter<Sleep> {
    private List<Sleep> sleeps;
    private Context context;

    public SleepAdapter(Context context, int resource, List<Sleep> object) {
        super(context, resource, object);
        this.sleeps = object;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View sleepItem = LayoutInflater.from(context).inflate(
                R.layout.fregment_sleep_item,
                parent,
                false);
        TextView date = sleepItem.findViewById(R.id.sleep_item_date);
        TextView sleeptime = sleepItem.findViewById(R.id.sleep_item_sleep);
        TextView sumtime = sleepItem.findViewById(R.id.sleep_item_sum_time);

        date.setText(sleeps.get(position).getDate());
        sleeptime.setText(sleeps.get(position).getTimeSleep() + " - " + sleeps.get(position).getTimeWake());
        sumtime.setText(sleeps.get(position).getTimeDiff());

        return sleepItem;
    }
}
