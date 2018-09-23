package com.example.a59070083.healthy.weight;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a59070083.healthy.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
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

//import com.example.a59070090.healthy.R;

/**
 * Created by LAB203_04 on 27/8/2561.
 */

public class WeightFragment extends Fragment {
    List<Weight> weights;
    private FirebaseAuth _auth;
    private FirebaseFirestore _firestore;

    public WeightFragment() {
        _auth = FirebaseAuth.getInstance();
        _firestore = FirebaseFirestore.getInstance();
        weights = new ArrayList<>();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addBtn();
        getDataFormFirebase();

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

    void addBtn() {
        Button _addBtn = getView().findViewById(R.id.add_weight_btn);
        _addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new WeightFormFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    void getDataFormFirebase() {
        _auth = FirebaseAuth.getInstance();
        String _uid = _auth.getUid();
        _firestore = FirebaseFirestore.getInstance();

        ListView weightList = getView().findViewById(R.id.weight_list);
        final WeightAdapter weightAdapter = new WeightAdapter(
                getActivity(),
                android.R.layout.list_content,
                weights
        );
        weightList.setAdapter(weightAdapter);
        weights.clear();

        _firestore.collection("myfitness")
                .document(_uid)
                .collection("weight")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for ( QueryDocumentSnapshot doc : queryDocumentSnapshots ) {
                            weights.add(doc.toObject(Weight.class));
                        }
                        weights = sortWeightsByDate(weights);
                        weightAdapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
