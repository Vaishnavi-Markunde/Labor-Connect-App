package com.example.laborconnect.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;

import com.example.laborconnect.R;
import com.example.laborconnect.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Handler handler;
    FirebaseFirestore db;
    FirebaseUser currentUser;
    User currentUserObject;
    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();
        super.onStart();


        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (currentUser == null) {
                    Intent intent = new Intent(SplashActivity.this, StartActivity.class);
                    startActivity(intent);
                } else {


                    db.collection("User")
                            .whereEqualTo("Email", currentUser.getEmail())
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                                        currentUserObject = doc.toObject(User.class);
                                        currentUserObject.setDocId(doc.getId());
                                        System.out.println(currentUserObject.getProfession());
                                        if(currentUserObject.getProfession().equals("Customer")) {
                                            //implement user page
                                            // setContentView(R.layout.activity_user_home_page);

                                            Intent intent = new Intent(SplashActivity.this, UserHomePage.class);

                                            intent.putExtra("userObj", currentUserObject);
                                            startActivity(intent);
                                        }
                                        else{

                                            Intent intent = new Intent(SplashActivity.this, WorkerHomePage.class);

                                            intent.putExtra("userObj", currentUserObject);
                                            startActivity(intent);
                                        }
                                    }
                                }
                            });
                }
                //finish();
            }
        }, 1000);

    }





}