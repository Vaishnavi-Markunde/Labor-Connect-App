package com.example.laborconnect.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laborconnect.Adapters.RequestAdapter;
import com.example.laborconnect.R;
import com.example.laborconnect.models.Request;
import com.example.laborconnect.models.User;
import com.example.laborconnect.notifications.FcmNotificationSender;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class WorkerHomePage extends AppCompatActivity {
    private static final String TAG = "WorkerPage";
    private FirebaseFirestore db;
    User currentUserObject;
    Request req;
    ArrayList<Request> requests;
    RequestAdapter adapter;
    String token;
    String prof;
    ListView reqGV;
    TextView workerLocn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_home_page);



        requests = new ArrayList<>();
        reqGV = findViewById(R.id.idWorkRequests);
        workerLocn = findViewById(R.id.workerLocn);



//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
//            channel = new NotificationChannel("Notification", "Notification", NotificationManager.IMPORTANCE_DEFAULT);
//
//            manager=getSystemService(NotificationManager.class);
//            manager.createNotificationChannel(channel);
//        }


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





        Intent intent = getIntent();
        currentUserObject = (User) intent.getSerializableExtra("userObj");
        prof= currentUserObject.getProfession().toLowerCase(Locale.ROOT);
        db = FirebaseFirestore.getInstance();
        CollectionReference ref = db.collection("requests_"+prof);


        String locn = "Your Location : "+currentUserObject.getState()+" , "+currentUserObject.getCity();
        workerLocn.setText(locn);

        System.out.println("curr state "+currentUserObject.getState()+" "+currentUserObject.getCity());




        db.collection("requests_"+prof).whereEqualTo("state",currentUserObject.getState())
                .whereEqualTo("city",currentUserObject.getCity()).orderBy("timestamp", Query.Direction.DESCENDING)
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
                            req.setWorker_docid(currentUserObject.getDocId());
                            req.setService(prof.toLowerCase(Locale.ROOT));
                            // req.setStatus("searching");
                            requests.add(req);

                        }
                        System.out.println("my req "+requests);
                        adapter = new RequestAdapter(WorkerHomePage.this,requests,currentUserObject);
                        //adapter.;

                        reqGV.setAdapter(adapter);
                    }
                });




    }

    private void sendRegistrationToServer(String token) {
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        db.collection("User").document(currentUserObject.getDocId()).update(map);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.profile) {
            Intent i = new Intent(WorkerHomePage.this,WorkerProfile.class);
            i.putExtra("userObj", currentUserObject);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(WorkerHomePage.this,"Press back again to exit",Toast.LENGTH_SHORT);
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

    }
}