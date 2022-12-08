package com.example.laborconnect.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.laborconnect.R;
import com.example.laborconnect.models.User;

public class WorkdetailsActivity extends AppCompatActivity {

    EditText desc,title ;
    String service;
    User currentUserObject;
    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workdetails);

        desc = findViewById(R.id.editDesc);
        title = findViewById(R.id.editTitle);
        next = findViewById(R.id.btnNext);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        service = extras.getString("service");
        currentUserObject = (User) extras.getSerializable("userObj");


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(desc.getText().toString().isEmpty()||title.getText().toString().isEmpty()){
                    System.out.println("yes empty");
                    Toast.makeText(WorkdetailsActivity.this,"Please enter complete work details",Toast.LENGTH_SHORT).show();
                    return;
                }

                System.out.println("title in workdetail "+title.getText().toString());
                Intent intent = new Intent(WorkdetailsActivity.this,AddressActivity.class);

                intent.putExtra("service",service);
                intent.putExtra("userObj",currentUserObject);
                intent.putExtra("desc",desc.getText().toString());
                intent.putExtra("title",title.getText().toString());
                startActivity(intent);
            }
        });

    }
}