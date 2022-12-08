package com.example.laborconnect.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laborconnect.R;
import com.example.laborconnect.models.Request;
import com.example.laborconnect.models.User;
import com.example.laborconnect.notifications.FcmNotificationSender;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.firestore.v1.DocumentTransform;
import com.google.type.DateTime;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ConfirmationActivity extends AppCompatActivity {

    User currentUserObject;
    private FirebaseFirestore db;
    TextView add,serv,cont,ts;
    Handler handler;
    String token;
    String userReqId;
    Request request;
    String desc_title,timespan,service,address,desc,city,state,uid;
    private static final String TAG = "Confirm/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        //db
        db = FirebaseFirestore.getInstance();

        add = findViewById(R.id.textAdd);
        serv = findViewById(R.id.textSer);
        cont = findViewById(R.id.textPhn);
        ts = findViewById(R.id.textTS);

        Intent intent = getIntent();

        Bundle extras = intent.getExtras();

        System.out.println(extras.getString("timespan"));
        System.out.println(extras.get("add1"));
        currentUserObject = (User) extras.getSerializable("userObj");

        timespan = extras.getString("timespan");
        service = extras.getString("service");
        address = extras.getString("add1")+" , "+extras.getString("add2")+" , \n"
                +extras.getString("city")+" "+extras.getString("pin");
        desc_title = extras.getString("title");
        desc = extras.getString("desc");
        city = extras.getString("city");
        state = extras.getString("state");
        uid = currentUserObject.getUID();


        add.setText(address);
        ts.setText(timespan);
        cont.setText(currentUserObject.getPhone_No());
        serv.setText(service);





        Map<String, Object> data = new HashMap<>();
        data.put("service", service);
        data.put("phone_No",currentUserObject.getPhone_No());
        data.put("address",address);
        data.put("timespan" ,timespan);
        data.put("description", desc);
        data.put("city",city);
        data.put("state",state);
        data.put("uid",uid);
        data.put("status","Searching");
        data.put("timestamp", Timestamp.now());
        data.put("user_docid",currentUserObject.getDocId());
        data.put("addline1",extras.getString("add1"));
        data.put("addline2",extras.getString("add2"));
        data.put("pincode",extras.getString("pin"));
        data.put("name",currentUserObject.getName());
        data.put("desc_title",desc_title);

        System.out.println("desc title"+desc_title);



        request = new Request();

        db.collection("User").document(currentUserObject.getDocId()).collection("Work Requests").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {


                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        userReqId = documentReference.getId();

                        request.setUserReqId(userReqId);
                        data.put("UserReqId",userReqId);


                        db.collection("requests_"+service.toLowerCase(Locale.ROOT)).add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error adding document", e);
                                    }
                                });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });





        db.collection("User").whereEqualTo("Profession",service).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                System.out.println("name "+document.get("name")+" sending ");
                                sendNotification(document.getId());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    private void sendNotification(String id) {

        db.collection("User").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                token = (String) documentSnapshot.get("token");
                System.out.println("token : "+token);

            }
        });

        handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                FcmNotificationSender sender = new FcmNotificationSender(
                        token,"You have got a new request! ",desc_title,getApplicationContext(),
                        ConfirmationActivity.this
                );

                sender.SendNotification();
                System.out.println(" sender "+sender);

            }
        }, 1000);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(ConfirmationActivity.this,"Press back again",Toast.LENGTH_SHORT);
        Intent intent = new Intent(ConfirmationActivity.this,UserHomePage.class);
        intent.putExtra("userObj",currentUserObject);
        startActivity(intent);
    }



    public void onHomeClick(View view) {

        Intent intent = new Intent(ConfirmationActivity.this,UserHomePage.class);
        intent.putExtra("userObj",currentUserObject);
        startActivity(intent);
    }
}




