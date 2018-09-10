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
import android.widget.TextView;
import android.widget.Toast;

import com.example.a59070083.healthy.R;

public class LoginFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fregment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

            initLoginBtn();
            initRegisterBtn();
    }

        void initLoginBtn(){
        Button _loginBtn = (Button) getView().findViewById(R.id.login_login_btn); //cast ให้ตรงกัน
        _loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText _userId = (EditText) getView().findViewById(R.id.login_user);
                EditText _password = (EditText) getView().findViewById(R.id.login_password);
                String _userIdStr = _userId.getText().toString();
                String _passwordStr = _password.getText().toString();

                if (_userIdStr.isEmpty()|| _passwordStr.isEmpty()){ //is null -> show thost
                    Log.d("USER","USER OR PASSWORD IS EMPTY");
                    Toast.makeText(
                            getActivity(), "กรุณาระบุ user or password" , Toast.LENGTH_SHORT).show();
                }
                else if (_userIdStr.equals("admin") && _passwordStr.equals("admin")){ //correct
                    Log.d("USER","GOTO BMI");
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new MenuFragment()).addToBackStack(null).commit();
                }
                else{ //incorrect
                    Log.d("USER","INVALID USERNAME OR PASSWORD");
                    Toast.makeText(getActivity(), "user or password ไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
        void initRegisterBtn(){
            TextView _registerBtn = getView().findViewById(R.id.register_textview);
            _registerBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("USER", "GOTO REGIS");
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new RegisterFragment())
                            .addToBackStack(null)
                            .commit();
                }
            });
        }

}

