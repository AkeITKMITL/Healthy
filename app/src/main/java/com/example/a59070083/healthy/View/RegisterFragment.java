package com.example.a59070083.healthy.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a59070083.healthy.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RegisterFragment extends Fragment {
    FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fregment_register, container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRegisterBtn();
        mAuth = FirebaseAuth.getInstance();
    }

    void initRegisterBtn(){
        Button _registerBtn = getView().findViewById(R.id.register_rergister_btn);
        _registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });
    }

    void registerNewUser(){
        EditText _email = getView().findViewById(R.id.register_email);
        EditText _password = getView().findViewById(R.id.register_password);
        EditText _repassword = getView().findViewById(R.id.register_repassword);

        String _emailStr = _email.getText().toString();
        String _passwordStr = _password.getText().toString();
        String _repasswordStr = _repassword.getText().toString();

        if ( _passwordStr.length() < 6 ) {
            Log.d("Register", "Password < 6 char");
            Toast.makeText(getActivity(), "Password < 6 char", Toast.LENGTH_SHORT).show();
        } else if ( !_passwordStr.equals(_repasswordStr) ) {
            Log.d("Register", "Password not equal");
            Toast.makeText(getActivity(), "Password and Re-Password not equal", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.createUserWithEmailAndPassword(_emailStr, _passwordStr).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    sendVerifiedEmail(authResult.getUser());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("Register", "Error : " + e.getMessage());
                    Toast.makeText(getActivity(), "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void sendVerifiedEmail(FirebaseUser _user) {
        _user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("Register", "Register complete");
                Toast.makeText(getActivity(), "Register complete", Toast.LENGTH_SHORT).show();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                    fm.popBackStack();
                }

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new LoginFragment())
                        .commit();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
