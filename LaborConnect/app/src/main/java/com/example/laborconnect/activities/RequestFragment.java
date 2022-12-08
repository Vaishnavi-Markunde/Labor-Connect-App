package com.example.laborconnect.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.laborconnect.Adapters.RequestAdapter;
import com.example.laborconnect.Adapters.UserRequestAdapter;
import com.example.laborconnect.R;
import com.example.laborconnect.models.Request;
import com.example.laborconnect.models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Locale;

public class RequestFragment extends Fragment  {
    User user;
    private static final String TAG = "UserRequestPage";



    private DrawerLayout drawer;
    BottomNavigationView navigationView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        UserHomePage userHomePage = (UserHomePage) getActivity();

        user = userHomePage.getCurrentUser();

        System.out.println("user name"+user.getName());
        return inflater.inflate(R.layout.fragment_requests,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Toolbar toolbar = view.findViewById(R.id.toolbar);

       // setSupportActionBar(toolbar);

        drawer = view.findViewById(R.id.my_drawer_layout);
        navigationView = view.findViewById(R.id.bottomNavigationView);
        getChildFragmentManager().beginTransaction().replace(R.id.fragment_container,new CompletedRequestsFragment()).commit();


        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()) {
                    case R.id.completed:
                        getChildFragmentManager().beginTransaction().replace(R.id.fragment_container,new CompletedRequestsFragment()).commit();
                        break;
                    case R.id.pending:
                        getChildFragmentManager().beginTransaction().replace(R.id.fragment_container,new PendingRequestsFragment()).commit();
                        break;


                }

                return true;
            }

        });

        }

        //navigationView.setNavigationItemSelectedListener(this);

//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity().getApplicationContext(),drawer,toolbar,
//                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
       // requests = new ArrayList<>();
       // reqGV = view.findViewById(R.id.idUserServiceRequests);
       // db = FirebaseFirestore.getInstance();

//        db.collection("User").document(user.getDocId()).collection("Work Requests")
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot value,
//                                        @Nullable FirebaseFirestoreException e) {
//                        if (e != null) {
//                            Log.w(TAG, "Listen failed.", e);
//                            return;
//                        }
//
//                        requests = new ArrayList<>();
//
//                        for (QueryDocumentSnapshot doc : value) {
//
//                            String id = doc.getId();
//                            req = doc.toObject(Request.class);
//                            req.setId(doc.getId());
//                            //req.setService(prof.toLowerCase(Locale.ROOT));
//                            // req.setStatus("searching");
//                            requests.add(req);
//
//                        }
//                        System.out.println("my req "+requests);
//                        adapter = new UserRequestAdapter(getActivity().getApplicationContext(),requests);
//                        reqGV.setAdapter(adapter);
//                    }
//                });

//        db.collection("User").document(user.getUID()).collection("my_accepted_requests")
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot value,
//                                        @Nullable FirebaseFirestoreException e) {
//                        if (e != null) {
//                            Log.w(TAG, "Listen failed.", e);
//                            return;
//                        }
//
//                        requests = new ArrayList<>();
//
//                        for (QueryDocumentSnapshot doc : value) {
//
//                            String id = doc.getId();
//                            req = doc.toObject(Request.class);
//                            req.setId(doc.getId());
//                            //req.setService(prof.toLowerCase(Locale.ROOT));
//                            // req.setStatus("searching");
//                            requests.add(req);
//
//                        }
//                        System.out.println("my req "+requests);
//                        adapter = new UserRequestAdapter(getActivity().getApplicationContext(),requests);
//                        reqGV.setAdapter(adapter);
//                    }
//                });
//






//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        switch(item.getItemId()){
//            case R.id.completed:
//                getChildFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                        new ProfileFragment()).commit();
//                break;
//
//            case R.id.pending:
////                getChildFragmentManager().beginTransaction().replace(R.id.fragment_container,
////                        new RequeFragment()).commit();
//                break;
//        }
//
//        drawer.closeDrawer(GravityCompat.START);
//
//        return true;
//    }
}
