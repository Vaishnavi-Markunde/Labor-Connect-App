package com.example.laborconnect.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.laborconnect.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class WorkerAddressActivity extends AppCompatActivity {
    Spinner state,city;
    String st,ct;
    Button done;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    String nametext,phonetext,emailtext,passwordtext,uid;
    String gender,prof;
    private static final String TAG = "WorkerAddress/";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_address);
        state = findViewById(R.id.state_spinner);
        city = findViewById(R.id.city_spinner);
        done = findViewById(R.id.btnDone);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        nametext = extras.getString("name");
        phonetext = extras.getString("phone");
        gender = extras.getString("gender");
        emailtext = extras.getString("email");
        prof = extras.getString("prof");
        passwordtext = extras.getString("password");


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                st = state.getSelectedItem().toString();
                ct = city.getSelectedItem().toString();
                System.out.println("email and pass : "+emailtext+" "+passwordtext);
                if (emailtext.isEmpty() || passwordtext.isEmpty()) {
                    Toast.makeText(WorkerAddressActivity.this, "Empty input", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(passwordtext.length()<6){
                    Toast.makeText(WorkerAddressActivity.this, "Password length should be more than 6", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(emailtext, passwordtext).addOnCompleteListener(WorkerAddressActivity.this, new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) { //If successful
                            Toast.makeText(WorkerAddressActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            // try {
                            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                            saveUserInfo();
                            Intent intent = new Intent(WorkerAddressActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(WorkerAddressActivity.this, "Failed!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        ArrayAdapter<CharSequence> adapter_state = ArrayAdapter.createFromResource(this,
                R.array.state_array, android.R.layout.simple_spinner_item);
        adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(adapter_state);


        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                st = String.valueOf(parent.getItemAtPosition(position));


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

                }
                else if(position==2) {
                    ArrayAdapter<CharSequence> adapter_city = ArrayAdapter.createFromResource(parent.getContext(),
                            R.array.city_kar, android.R.layout.simple_spinner_item);

                    //   adapter_city.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    city.setAdapter(adapter_city);


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void saveUserInfo(){

        Map<String, Object> data = new HashMap<>();
        data.put("Name", nametext);
        data.put("Phone_No",phonetext);
        data.put("Gender", gender);
        data.put("Email" ,emailtext);
        data.put("Profession", prof);
        data.put("state",st);
        data.put("city",ct);
        data.put("UID",uid);


        db.collection("User").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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
}