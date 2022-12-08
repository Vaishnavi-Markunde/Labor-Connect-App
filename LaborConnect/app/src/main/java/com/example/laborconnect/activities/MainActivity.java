package com.example.laborconnect.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.example.laborconnect.R;
import com.example.laborconnect.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;


public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseUser currentUser;
    User currentUserObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();


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

                                Intent intent = new Intent(MainActivity.this, UserHomePage.class);

                                intent.putExtra("userObj", currentUserObject);
                                startActivity(intent);
                            }
                            else{

                                Intent intent = new Intent(MainActivity.this, WorkerHomePage.class);

                                intent.putExtra("userObj", currentUserObject);
                                startActivity(intent);
                            }
//                            setNavHeaderEmailAndUsername(); //Set nav header username and email
//                            setAllChildFragmentsViewModelData();
//                            navigationDrawerSetup();
                        }
                    }
                });


//        if(currentUserObject.getProfession().equals("Customer")){
//            //implement user page
//           // setContentView(R.layout.activity_user_home_page);
//            Intent intent = new Intent(MainActivity.this,UserHomePage.class);
//           // intent.putExtra("userObj",  currentUserObject);
//            startActivity(intent);
//
//
//
//
//
//
//
//        }
//        else {
//            //implement worker page
//            setContentView(R.layout.activity_main);
//        }







    }
}