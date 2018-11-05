package com.example.a59070083.healthy.sleep;

import android.content.ContentValues;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Sleep {
    private String date;
    private String timeSleep;
    private String timeWake;
    private String timeDiff;
    private ContentValues _row = new ContentValues();

    public Sleep() {
    }

    public Sleep(String date, String timeSleep, String timeWake) {
        this.date = date;
        this.timeSleep = timeSleep;
        this.timeWake = timeWake;
        calTimeDiff();
    }

    public void setContent(String date, String sleep, String wake) {
        this._row.put("date", date);
        this._row.put("timeSleep", sleep);
        this._row.put("timeWake", wake);
    }

    public ContentValues getContent() {
        return _row;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeSleep() {
        return timeSleep;
    }

    public void setTimeSleep(String timeSleep) {
        this.timeSleep = timeSleep;
    }

    public String getTimeWake() {
        return timeWake;
    }

    public void setTimeWake(String timeWake) {
        this.timeWake = timeWake;
    }

    public String getTimeDiff() {
        return timeDiff;
    }

    public void setTimeDiff(String timeDiff) {
        this.timeDiff = timeDiff;
    }

    private void calTimeDiff() {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm");
        try {
            Date date1 = format.parse(this.timeSleep);
            Date date2 = format.parse(this.timeWake);
            long mills = date2.getTime() - date1.getTime();
//            Log.v("Data1", ""+date1.getTime());
//            Log.v("Data2", ""+date2.getTime());
            int hours = (int) (mills/(1000 * 60 * 60));
            int mins = (int) (mills/(1000*60)) % 60;
            if ( hours < 0 ) {
                date1 = format.parse("-24:00");
                date2 = format.parse(String.format("%02d:%02d", hours, mins));
                mills = date2.getTime() - date1.getTime();
                hours = (int) (mills/(1000 * 60 * 60));
                mins = (int) (mills/(1000*60)) % 60;
            }

            this.timeDiff = String.format("%02d:%02d", hours, mins);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
