package com.example.laborconnect.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.FragmentManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laborconnect.Adapters.UserRequestAdapter;
import com.example.laborconnect.R;
import com.example.laborconnect.models.Request;
import com.example.laborconnect.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserHomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    final String TAG = "User Home Page";


    User currentUserObject;
    FirebaseFirestore db;
    String token;
    NotificationManager manager;
    NotificationChannel channel;
    NavigationView navigationView;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel("Notification", "Notification", NotificationManager.IMPORTANCE_DEFAULT);

            manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        Intent intent = getIntent();
        currentUserObject = (User) intent.getSerializableExtra("userObj");
        db = FirebaseFirestore.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.my_drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //   if(savedInstanceState==null){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new UserHomeFragment()).addToBackStack("my_frag").commit();


        // }
        System.out.println("stack count" + getSupportFragmentManager().getBackStackEntryCount());


        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        token = task.getResult();
                        sendRegistrationToServer(token);

                        // Log and toast
                        //String msg = getString("msg token", token);
                        Log.d(TAG, token);
                        //  Toast.makeText(WorkerHomePage.this, token, Toast.LENGTH_SHORT).show();
                    }
                });


        db.collection("User").document(currentUserObject.getUID()).collection("my_accepted_requests")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }


                    }
                });


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new UserHomeFragment()).commit();
                break;

            case R.id.navProfile:
                System.out.println("profile clicked");
                System.out.println("stack count" + getSupportFragmentManager().getBackStackEntryCount());


                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        ProfileFragment.newInstance(currentUserObject), "ProfileFragment").commit();
                break;

            case R.id.nav_requests:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new RequestFragment()).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {

        System.out.println("get back " + getSupportFragmentManager().getBackStackEntryCount());
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                    new UserHomeFragment()).commit();
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new UserHomeFragment()).commit();
        } else {
            Toast.makeText(UserHomePage.this,"Press back again to exit",Toast.LENGTH_SHORT);
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }


    }


    public void openOne(View view) {
        Button button = (Button) view;

        String service = button.getText().toString();

        Intent intent = new Intent(UserHomePage.this, WorkdetailsActivity.class);
        intent.putExtra("userObj", currentUserObject);

        intent.putExtra("service", service);
        startActivity(intent);


    }

    private void sendRegistrationToServer(String token) {
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        db.collection("User").document(currentUserObject.getDocId()).update(map);
    }

    public User getCurrentUser() {
        return currentUserObject;
    }
}