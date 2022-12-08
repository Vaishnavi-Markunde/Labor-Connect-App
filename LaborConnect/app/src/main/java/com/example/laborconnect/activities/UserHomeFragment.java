package com.example.laborconnect.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.laborconnect.R;
import com.example.laborconnect.models.User;

public class UserHomeFragment extends Fragment {

    User user;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        UserHomePage userHomePage = (UserHomePage) getActivity();

        user = userHomePage.getCurrentUser();
        return inflater.inflate(R.layout.fragment_userhome,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textName = view.findViewById(R.id.texthi);
        textName.setText("Hi "+user.getName()+"!");
    }
}