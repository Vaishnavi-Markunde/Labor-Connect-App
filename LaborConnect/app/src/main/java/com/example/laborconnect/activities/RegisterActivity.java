package com.example.laborconnect.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.laborconnect.R;
import com.example.laborconnect.models.User;
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

public class RegisterActivity extends AppCompatActivity {

    EditText name;
    EditText email;
    EditText phnNo;
    EditText password;
    Button backBtn, registerButton;
    RadioButton maleRadioBtn, femaleRadioBtn;
    RadioGroup roleGroup;
    RadioButton workerRadioBtn, customerRadioBtn;
    Spinner profession;
    String prof = "Customer";
    String nametext,phonetext,emailtext,passwordtext,uid;
    String gender;

    User currentUserObject;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private static final String TAG = "MyApp/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        linkViewElements();
        setRoleGroupBtnActionHandler();


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(workerRadioBtn.isChecked())
                    prof = profession.getSelectedItem().toString();
                else prof ="Customer";

                nametext = name.getText().toString();
                emailtext = email.getText().toString();
                phonetext = phnNo.getText().toString();
                passwordtext = password.getText().toString();
                gender = maleRadioBtn.isChecked()? "Male" : "Female";


                if (emailtext.isEmpty() || passwordtext.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Empty input", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(passwordtext.length()<6){
                    Toast.makeText(RegisterActivity.this, "Password length should be more than 6", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Call FireStoreAuth for authentication process

                if(prof.equals("Customer")){
                    mAuth.createUserWithEmailAndPassword(emailtext, passwordtext).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) { //If successful
                                Toast.makeText(RegisterActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                // try {
                                uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                saveUserInfo();


                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                    startActivity(intent);

//
                            } else {
                                Toast.makeText(RegisterActivity.this, "Failed!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                else{

                    Intent intent = new Intent(RegisterActivity.this, WorkerAddressActivity.class);
                    intent.putExtra("name",nametext);
                    intent.putExtra("phone",phonetext);
                    intent.putExtra("gender",gender);
                    intent.putExtra("email",emailtext);
                    intent.putExtra("prof",prof);
                    intent.putExtra("password",passwordtext);
                    startActivity(intent);

                }

            }
        });








        profession = (Spinner) findViewById(R.id.professionSpinner);

        ArrayAdapter<CharSequence> adapter_prof = ArrayAdapter.createFromResource(this,
                R.array.prof_array, android.R.layout.simple_spinner_item);
        adapter_prof.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        profession.setAdapter(adapter_prof);



    }

    private void saveUserInfo(){

        Map<String, Object> data = new HashMap<>();
        data.put("Name", nametext);
        data.put("Phone_No",phonetext);
        data.put("Gender", gender);
        data.put("Email" ,emailtext);
        data.put("Profession", prof);
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

    private void linkViewElements() {

        name = (EditText) findViewById(R.id.registerNameEditText);
        phnNo = (EditText) findViewById(R.id.registerPhoneEditText);
        email = findViewById(R.id.registerEmailEditText);
        backBtn = (Button) findViewById(R.id.registerBackBtn);
        registerButton = (Button) findViewById(R.id.registerFinalRegisterBtn);
        password = findViewById(R.id.password);
        maleRadioBtn = (RadioButton) findViewById(R.id.registerMaleRadioBtn);
        femaleRadioBtn = (RadioButton) findViewById(R.id.registerFemaleRadioBtn);
        roleGroup = (RadioGroup) findViewById(R.id.roleGroup);
        customerRadioBtn = (RadioButton) findViewById(R.id.registerCustomerRadioBtn);
        workerRadioBtn = (RadioButton) findViewById(R.id.registerWorkerRadioBtn);
        profession = findViewById(R.id.professionSpinner);

    }

    private void setRoleGroupBtnActionHandler() {


        //assert roleGroup != null;
        roleGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.registerCustomerRadioBtn:
                        profession.setVisibility(View.INVISIBLE);
                        prof ="Customer";
                        break;
                    case R.id.registerWorkerRadioBtn:
                        profession.setVisibility(View.VISIBLE);
                        break;

                }
            }
        });

    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RegisterActivity.this,StartActivity.class);
        startActivity(intent);
    }

    public void onBackPressed(View view) {
        Intent intent = new Intent(RegisterActivity.this,StartActivity.class);
        startActivity(intent);
    }
}