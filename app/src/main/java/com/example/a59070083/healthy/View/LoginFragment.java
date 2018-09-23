package com.example.a59070083.healthy.View;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.a59070083.healthy.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fregment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        checkLoginUser();
        initLoginBtn();
        initRegisterBtn();
    }

        void initLoginBtn() {
        final Button _loginBtn = getView().findViewById(R.id.login_login_btn);
        _loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    void initRegisterBtn() {
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

    void loginUser() {
        final Button _loginBtn = getView().findViewById(R.id.login_login_btn);
        EditText _userId = getView().findViewById(R.id.login_user);
        EditText _password = getView().findViewById(R.id.login_password);
        String _userIdStr = _userId.getText().toString();
        String _passwordStr = _password.getText().toString();

        _loginBtn.setEnabled(false);
        mAuth.signInWithEmailAndPassword(_userIdStr, _passwordStr).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                if ( authResult.getUser().isEmailVerified() ) {
                    Log.d("Login", "Login success");
                    Toast.makeText(getActivity(), "Login success", Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new MenuFragment())
                            .commit();
                } else {
                    _loginBtn.setEnabled(true);
                    Toast.makeText(getActivity(), "Please comfirm your email", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                _loginBtn.setEnabled(true);
                Log.d("Login", "Error : " + e.getMessage());
                Toast.makeText(getActivity(), "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void checkLoginUser() {
        FirebaseUser _user = mAuth.getCurrentUser();
        if ( _user != null ) {
            Log.d("test", _user.getUid());
            Log.d("test", mAuth.getCurrentUser().getEmail());
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_view, new MenuFragment())
                    .commit();
        }
    }
}

