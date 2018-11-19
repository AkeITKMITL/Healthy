package com.example.a59070083.healthy.sleep;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.a59070083.healthy.R;
import com.example.a59070083.healthy.View.MenuFragment;

import java.util.ArrayList;
import java.util.List;

public class SleepFragment extends Fragment {
    private List<Sleep> sleeps;
    private SQLiteDatabase myDB;

    public SleepFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sleep, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myDB = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);
        myDB.execSQL("CREATE TABLE IF NOT EXISTS user (_id INTEGER PRIMARY KEY AUTOINCREMENT, date VARCHAR(20), timeSleep VARCHAR(20), timeWake VARCHAR(20), timeDiff VARCHAR(20))");
        _addSleepBtn();
        _backSleepBtn();
        getDataFormSQL();
    }

    private void _addSleepBtn(){
        Button _addSleep = getView().findViewById(R.id.add_sleep_btn);
        _addSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new SleepFromFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void _backSleepBtn(){
        Button _backBtn = getView().findViewById(R.id.back_sleep_btn);
        _backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new MenuFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    void getDataFormSQL() {
//        sleeps.clear();
        sleeps = new ArrayList<>();
        ListView sleepList = getView().findViewById(R.id.sleep_list);
        final SleepAdapter sleepAdapter = new SleepAdapter(
                getActivity(),
                R.layout.fragment_sleep_item,
                sleeps
        );
        sleepList.setAdapter(sleepAdapter);

        Cursor myCursor = myDB.rawQuery("SELECT * FROM user", null);

        while(myCursor.moveToNext()){
            String _date = myCursor.getString(1);
            String _timeSleep = myCursor.getString(2);
            String _timeWake = myCursor.getString(3);

            Log.d("SLEEP", "_id : "+myCursor.getInt(0)+" sleep : "+_timeSleep+" wake : "+_timeWake+" date : "+_date);

            sleeps.add(new Sleep(_date, _timeSleep, _timeWake));
        }

        sleepList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("SLEEP", "Position" + position);
                Bundle bundle = new Bundle();

                bundle.putInt("_id", position);

                SleepFromFragment sleepFromFragment = new SleepFromFragment();

                sleepFromFragment.setArguments(bundle);

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, sleepFromFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
