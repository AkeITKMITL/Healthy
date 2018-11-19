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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.a59070083.healthy.Post.PostFragment;
import com.example.a59070083.healthy.R;
import com.example.a59070083.healthy.sleep.SleepFragment;
import com.example.a59070083.healthy.weight.WeightFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class MenuFragment extends Fragment {
    ArrayList<String> menu;
    FirebaseAuth _auth;

    public MenuFragment() {
        menu = new ArrayList<>();
        menu.add("BMI");
        menu.add("Weight");
        menu.add("Setup");
        menu.add("Sleep");
        menu.add("Post");
        menu.add("Logout");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final ArrayAdapter<String> menuAdapter = new ArrayAdapter<>( //สร้าง adapter
                getActivity(),
                android.R.layout.simple_list_item_1,
                menu
        );

        ListView menuList = getView().findViewById(R.id.menu_list);
        menuList.setAdapter(menuAdapter);

        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> adapterView , View view, int i, long l){
              Log.d("MENU","Click on menu = "+ menu.get(i));
//                menuAdapter.notifyDataSetChanged();

                if (menu.get(i).equals("BMI")) {
                    Log.d("USER","GOTO BMI");
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new BmiFragment())
                            .addToBackStack(null)
                            .commit();
                }
                else if (menu.get(i).equals("Weight")) {
                    Log.d("USER","GOTO WEIGHT");
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new WeightFragment())
                            .addToBackStack(null)
                            .commit();
                }
                else if (menu.get(i).equals("Sleep")) {
                    Log.d("USER", "GOTO SLEEP");
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new SleepFragment())
                            .addToBackStack(null)
                            .commit();
                }
                else if (menu.get(i).equals("Post")) {
                    Log.d("USER", "GOTO POST");
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new PostFragment())
                            .addToBackStack(null)
                            .commit();
                }
                else if (menu.get(i).equals("Logout")) {
                    _auth = FirebaseAuth.getInstance();
                    _auth.signOut();
                    Log.d("USER","Logout");
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new LoginFragment())
                            .commit();
                }

          }
        });

    }


}
