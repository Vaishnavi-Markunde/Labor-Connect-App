package com.example.laborconnect.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.laborconnect.Adapters.HistoryAdapter;
import com.example.laborconnect.Adapters.RequestAdapter;
import com.example.laborconnect.Adapters.UserRequestAdapter;
import com.example.laborconnect.R;
import com.example.laborconnect.models.Request;
import com.example.laborconnect.models.User;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class WorkerHistory extends AppCompatActivity {
    private static final String TAG = "WorkerHistory";
    private FirebaseFirestore db;
    User currentUserObject;
    Request req;
    ArrayList<Request> requests;
    HistoryAdapter adapter;
    ListView reqGV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_history);
        db = FirebaseFirestore.getInstance();
        requests = new ArrayList<>();
        reqGV = findViewById(R.id.idWorkHistory);
        Intent intent = getIntent();
        currentUserObject = (User) intent.getSerializableExtra("userObj");


        db.collection("User").document(currentUserObject.getDocId()).collection("Your Accepted Services").orderBy("acceptedTimestamp", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        requests = new ArrayList<>();

                        for (QueryDocumentSnapshot doc : value) {

                            String id = doc.getId();
                            req = doc.toObject(Request.class);
                            req.setId(doc.getId());

                            requests.add(req);

                        }
                        System.out.println("my confirmed req "+requests);

                            adapter = new HistoryAdapter(WorkerHistory.this,requests);
                            reqGV.setAdapter(adapter);


                    }
                });

        //    db.collection("User").document(currentUserObject.getDocId()).collection("Your Accepted Services")



    }
}