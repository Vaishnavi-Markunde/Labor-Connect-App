package com.example.laborconnect.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.laborconnect.R;
import com.example.laborconnect.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";


    private User user;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_profile,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        Spinner state,city;
        TextView textName = view.findViewById(R.id.name);
        textName.setText(user.getName());
        TextView textEmail = view.findViewById(R.id.email);
        textEmail.setText(user.getEmail());
        TextView textPhn = view.findViewById(R.id.phn);
        textPhn.setText(user.getPhone_No());
        TextView gender = view.findViewById(R.id.gender);
        gender.setText(user.getGender());
        Button logout = view.findViewById(R.id.logout);


        state = view.findViewById(R.id.state_spinner);
        city = view.findViewById(R.id.city_spinner);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent = new Intent(view.getContext(),StartActivity.class);
                startActivity(intent);
            }
        });

        ArrayAdapter<CharSequence> adapter_state = ArrayAdapter.createFromResource(view.getContext(),
                R.array.state_array, android.R.layout.simple_spinner_item);
        adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(adapter_state);

        if(user.getState()!=null) {
            int p = adapter_state.getPosition(user.getState());
            state.setSelection(p);

        }

        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    ArrayAdapter<CharSequence> adapter_city = ArrayAdapter.createFromResource(parent.getContext(),
                            R.array.select_state_first, android.R.layout.simple_spinner_item);
                    // adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    city.setAdapter(adapter_city);
                }

                if(position==1) {
                    ArrayAdapter<CharSequence> adapter_city = ArrayAdapter.createFromResource(parent.getContext(),
                            R.array.city_mah, android.R.layout.simple_spinner_item);

                    // adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    city.setAdapter(adapter_city);
                    if(user.getCity()!=null) {
                        int p = adapter_city.getPosition(user.getCity());
                        city.setSelection(p);

                    }
                }
                else if(position==2) {
                    ArrayAdapter<CharSequence> adapter_city = ArrayAdapter.createFromResource(parent.getContext(),
                            R.array.city_kar, android.R.layout.simple_spinner_item);

                    //   adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    city.setAdapter(adapter_city);

                    if(user.getCity()!=null) {
                        int p = adapter_city.getPosition(user.getCity());
                        city.setSelection(p);

                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        TextView locntext = view.findViewById(R.id.locationtext);
        TextView add = view.findViewById(R.id.address);
        TextView proftext = view.findViewById(R.id.proftext);
        TextView prof = view.findViewById(R.id.prof);
        View v1 = view.findViewById(R.id.locationview);
        View v2 = view.findViewById(R.id.profview);
        LinearLayout addLayout = view.findViewById(R.id.addLinearLayout);
        LinearLayout spinnerLayout = view.findViewById(R.id.spinnerlayout);
        ImageButton editAdd = view.findViewById(R.id.editAdd);
        Button done = view.findViewById(R.id.btnDone);

        if(!user.getProfession().equals("Customer")){
            locntext.setVisibility(View.VISIBLE);
            proftext.setVisibility(View.VISIBLE);
            v1.setVisibility(View.VISIBLE);
            v2.setVisibility(View.VISIBLE);
            addLayout.setVisibility(View.VISIBLE);
            add.setText(user.getCity()+" , "+user.getState());
            add.setVisibility(View.VISIBLE);
            prof.setText(user.getProfession());
            prof.setVisibility(View.VISIBLE);

        }

        editAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLayout.setVisibility(View.GONE);
                spinnerLayout.setVisibility(View.VISIBLE);

            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLayout.setVisibility(View.VISIBLE);
                spinnerLayout.setVisibility(View.GONE);
                String st = state.getSelectedItem().toString();
                String ct = city.getSelectedItem().toString();
                user.setState(st);
                user.setCity(ct);

                Map<String,Object> map = new HashMap<>();

                map.put("city",ct);
                map.put("state",st);
                add.setText(user.getCity()+" , "+user.getState());

                db.collection("User").document(user.getDocId()).update(map);

            }
        });





    }

    public static ProfileFragment newInstance(User user) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = (User) getArguments().get(ARG_PARAM1);


        }
    }


}
