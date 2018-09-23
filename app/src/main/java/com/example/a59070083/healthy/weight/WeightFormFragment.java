package com.example.a59070083.healthy.weight;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a59070083.healthy.DatePickerFragment;
import com.example.a59070083.healthy.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class WeightFormFragment extends Fragment {
    FirebaseFirestore _firestore;
    FirebaseAuth _auth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight_from, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        _firestore = FirebaseFirestore.getInstance();
        _auth = FirebaseAuth.getInstance();

        initSaveButton();
        backBtn();
        dateSelection();
    }

    private void initSaveButton() {
        Button _btn = getView().findViewById(R.id.weight_from_sendBtn);
        _btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText _date = getView().findViewById(R.id.weight_from_date);
                EditText _weight = getView().findViewById(R.id.weight_from_weight);

                final String _dateStr = _date.getText().toString();
                final String _weightStr = _weight.getText().toString();

                final String _uid = _auth.getUid();

                final List<Weight> weights = new ArrayList<>();

                final List<Weight> finalWeights = weights;
                _firestore.collection("myfitness")
                        .document(_uid)
                        .collection("weight")
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for ( QueryDocumentSnapshot doc : queryDocumentSnapshots ) {
                                        finalWeights.add(doc.toObject(Weight.class));
                                }
                                String status = "";
                                List<Weight> weight_sort = sortWeightsByDate(weights);
                                if (!( weight_sort.isEmpty())) {
                                    int last_weight = weight_sort.get(0).getWeight();
                                    if ( last_weight > Integer.parseInt(_weightStr) ) {
                                        status = "DOWN";
                                    } else {
                                        status = "UP";
                                    }
                                }
                                Weight _data = new Weight(
                                        _dateStr,
                                        Integer.parseInt(_weightStr),
                                        status
                                );

                                _firestore.collection("myfitness")
                                        .document(_uid)
                                        .collection("weight")
                                        .document(_dateStr)
                                        .set(_data)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                getActivity().getSupportFragmentManager()
                                                        .beginTransaction()
                                                        .replace(R.id.main_view, new WeightFragment())
                                                        .addToBackStack(null)
                                                        .commit();
                                                Toast.makeText(getActivity(), "Success Add", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity(), "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void backBtn() {
        TextView _backBtn = getView().findViewById(R.id.weight_from_backBtn);
        _backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new WeightFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void dateSelection() {
        Button _dateBtn = getView().findViewById(R.id.weight_from_dateBtn);
        _dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });
    }

    private static List<Weight> sortWeightsByDate(List<Weight> weights) {
        Collections.sort(weights, new Comparator<Weight>() {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");;
            Date date1;
            Date date2;
            @Override
            public int compare(Weight o1, Weight o2) {
                try {
                    date1 = df.parse(o1.getDate());
                    date2 = df.parse(o2.getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return date2.compareTo(date1);
            }

        });

        return weights;
    }
}
