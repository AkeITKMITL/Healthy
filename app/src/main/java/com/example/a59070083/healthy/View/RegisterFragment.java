package com.example.a59070083.healthy.View;

import android.os.Bundle;
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


public class RegisterFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fregment_register, container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRegisterBtn();
    }

    void initRegisterBtn(){
        Button _registerBtn = getView().findViewById(R.id.register_rergister_btn);
        _registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText _userId = getView().findViewById(R.id.register_userid);
//                EditText _name = getView().findViewById(R.id.register_name);
//                EditText _age = getView().findViewById(R.id.register_age);
                EditText _password = getView().findViewById(R.id.register_password);
                String userIdStr = _userId.getText().toString();
//                String nameStr = _name.getText().toString();
//                String ageStr = _age.getText().toString();
                String passwordStr = _password.getText().toString();

//                if(userIdStr.isEmpty() || nameStr.isEmpty() || ageStr.isEmpty() || passwordStr.isEmpty()){
//                    Log.d("Register","FIELD NAME IS EMPTY");
//                    Toast.makeText(getActivity() , "กรุณากรอกข้อมูลให้ครบถ้วน" , Toast.LENGTH_SHORT).show();
//                }
//                else if (userIdStr.equals("admin")){
                if (userIdStr.equals("admin")){
                    Log.d("Register","USER ALREADY EXIST");
                    Toast.makeText(getActivity(), "user นี้มีอยู่ในระบบแล้ว" , Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.d("Register","GO TO BMI");
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new BmiFragment())
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
    }
}
