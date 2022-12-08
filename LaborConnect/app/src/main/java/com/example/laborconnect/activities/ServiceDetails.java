package com.example.laborconnect.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laborconnect.R;
import com.example.laborconnect.models.Request;
import com.example.laborconnect.models.User;
import com.example.laborconnect.notifications.FcmNotificationSender;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ServiceDetails extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    Handler handler;
    Request reqModel;
    String token;
    User currentUserObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_details);

        TextView name = findViewById(R.id.name);
        TextView title = findViewById(R.id.title);
        TextView desc =findViewById(R.id.desc);
        TextView phn = findViewById(R.id.phn);
        TextView add = findViewById(R.id.add);
        TextView ts = findViewById(R.id.timespan);
        Button btnAccept = findViewById(R.id.btnReqAccept);

        Intent intent = getIntent();
        reqModel = (Request) intent.getSerializableExtra("reqModel");
        currentUserObject = (User) intent.getSerializableExtra("userObj");
        name.setText(reqModel.getName());
        title.setText(reqModel.getDesc_title());
        desc.setText(reqModel.getDescription());
        add.setText(reqModel.getAddress());
        phn.setText(reqModel.getPhone_No());
        ts.setText(reqModel.getTimespan());









        btnAccept.setOnClickListener(new View.OnClickListener() {
            private static final String TAG = "RequestAdapter" ;

            @Override
            public void onClick(View v) {

                String id = reqModel.getId();
                String service = reqModel.getService();
                String uid = reqModel.getUid();
                sendNotification(reqModel.getUser_docid(), auth.getCurrentUser().getUid());

                db.collection("requests_" + service).document(id)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {

                            @Override
                            public void onSuccess(Void aVoid) {
                                saveToAcceptedRequests(reqModel);

                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error deleting document", e);
                            }
                        });



                db.collection("User").document(reqModel.getUser_docid()).collection("Work Requests").document(reqModel.getUserReqId()).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {

                            @Override
                            public void onSuccess(Void aVoid) {
                                saveToUserAcceptedRequests(reqModel);
                                saveToWorkerAcceptedRequests(reqModel);
                                Log.d(TAG, "DocumentSnapshot work req successfully deleted! " + id);
                            }


                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error deleting document", e);
                            }
                        });


                Toast.makeText(ServiceDetails.this, "Request Accepted", Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(ServiceDetails.this, WorkerHomePage.class);
                intent.putExtra("userObj", currentUserObject);
                startActivity(intent);


            }




        });

    }

    public void saveToAcceptedRequests(Request reqModel){
        String wid = auth.getCurrentUser().getUid();
        reqModel.setWid(wid);
        reqModel.setWorker_docid(currentUserObject.getDocId());
        reqModel.setWorker_name(currentUserObject.getName());
        reqModel.setWorker_phnNo(currentUserObject.getPhone_No());
        //eqModel.setAcceptedTimestamp(Timestamp.now());
        reqModel.setStatus("found");
        db.collection("accepted_requests").add(reqModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                db.collection("accepted_requests").document(documentReference.getId()).update("acceptedTimestamp", FieldValue.serverTimestamp());
            }
        });

    }

    private void saveToUserAcceptedRequests(Request reqModel) {
        db.collection("User").document(reqModel.getUser_docid()).collection("my_accepted_requests")
                .add(reqModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        db.collection("User").document(reqModel.getUser_docid()).collection("my_accepted_requests").document(documentReference.getId()).update("acceptedTimestamp", FieldValue.serverTimestamp());
                    }
                });

    }

    private void saveToWorkerAcceptedRequests(Request reqModel) {


        db.collection("User").document(currentUserObject.getDocId()).collection("Your Accepted Services")
                .add(reqModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                db.collection("User").document(currentUserObject.getDocId())
                        .collection("Your Accepted Services")
                        .document(documentReference.getId())
                        .update("acceptedTimestamp", FieldValue.serverTimestamp());
            }
        });
    }

        private void sendNotification(String user_doc_id, String uid1) {


      db.collection("User").document(user_doc_id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                token = (String) documentSnapshot.get("token");


            }
        });

        handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                FcmNotificationSender sender = new FcmNotificationSender(
                        token,"Accepted","Your request for "+reqModel.getService()+" was accepted by "+currentUserObject.getName(),getApplicationContext(),
                        ServiceDetails.this
                        );

                sender.SendNotification();
            }
        }, 1000);


        }



}