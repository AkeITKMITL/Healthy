package com.example.a59070083.healthy.sleep;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a59070083.healthy.R;

public class SleepFromFragment extends Fragment{
    private SQLiteDatabase myDB;
    private Bundle _bundle;
    private EditText _date;
    private EditText _timeSleep;
    private EditText _timeWake;
    private String _dateStr;
    private String _timeSleepStr;
    private String _timeWakeStr;
    private int _bundleInt;
    private String _dateSql;
    private String _timeSleepSql;
    private String _timeWakeSql;
    private Sleep _itemSleep = new Sleep();
    private ContentValues _row;

    public SleepFromFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fregment_sleep_from, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        myDB = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);
        myDB.execSQL("CREATE TABLE IF NOT EXISTS user (_id INTEGER PRIMARY KEY AUTOINCREMENT, date VARCHAR(20), timeSleep VARCHAR(20), timeWake VARCHAR(20), timeDiff VARCHAR(20))");

        _backBtn();
        _saveBtn();

        _bundle = getArguments();

        _date = getView().findViewById(R.id.sleep_date);
        _timeSleep = getView().findViewById(R.id.sleep_time_sleep);
        _timeWake = getView().findViewById(R.id.sleep_time_wake);

        int count = 0;

        if(_bundle != null){
            _bundleInt = _bundle.getInt("_id"); //get Bundle to Int

            Cursor myCursor = myDB.rawQuery("SELECT * FROM user", null);
            while (myCursor.moveToNext()){
                if(count == _bundleInt){
                    _bundleInt = myCursor.getInt(0); //เช็คเป็น _id เพราะจะเอาไปอัพเดตตาราง
                    _dateSql = myCursor.getString(1);
                    _timeSleepSql = myCursor.getString(2);
                    _timeWakeSql = myCursor.getString(3);

                    _date.setText(_dateSql);
                    _timeSleep.setText(_timeSleepSql);
                    _timeWake.setText(_timeWakeSql);

                    Log.d("SLEEP_FORM", "Count = "+count+" _bundleInt = "+_bundleInt+" _id = "+_bundleInt);
                } else {
                    count += 1;
                }
            }
        }
    }

    private void _backBtn(){
        Button _backBtn = getView().findViewById(R.id.back_sleep_from_btn);
        _backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new SleepFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void _saveBtn() {
        Button _saveBtn = getView().findViewById(R.id.save_sleep_from_btn);
        _saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _date = getView().findViewById(R.id.sleep_date);
                _timeSleep = getView().findViewById(R.id.sleep_time_sleep);
                _timeWake = getView().findViewById(R.id.sleep_time_wake);

                _dateStr = _date.getText().toString();
                _timeSleepStr = _timeSleep.getText().toString();
                _timeWakeStr = _timeWake.getText().toString();

                if(_bundle != null){ //เช็คว่าข้อมูลไหนมีการอัพเดตมั่ง
                    Log.d("SLEEP_FORM", "update = "+_dateStr+_timeSleepStr+_timeWakeStr);

                    _itemSleep.setContent(_dateStr, _timeSleepStr, _timeWakeStr);
                    _row = _itemSleep.getContent();

                    myDB.update("user", _row, "_id="+_bundleInt, null);

                    Log.d("SLEEP_FORM", "UPDATE ALREADY");
                    Toast.makeText(getActivity(), "UPDATE COMPLETE", Toast.LENGTH_SHORT).show();
                } else { //Bundle = null แปลว่าจะเพิ่มข้อมูล
                    _itemSleep.setContent(_dateStr, _timeSleepStr, _timeWakeStr);

                    _row = _itemSleep.getContent();

                    myDB.insert("user", null, _row);

                    Log.d("SLEEP_FORM", "INSERT ALREADY");
                    Toast.makeText(getActivity(), "SAVE", Toast.LENGTH_SHORT).show();
                }

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new SleepFragment())
                        .addToBackStack(null)
                        .commit();

                Log.d("SLEEP_FORM", "GOTO SLEEP");
            }
        });
    }
}
