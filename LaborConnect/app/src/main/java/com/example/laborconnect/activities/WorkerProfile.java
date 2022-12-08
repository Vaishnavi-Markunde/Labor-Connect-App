package com.example.laborconnect.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.laborconnect.R;
import com.example.laborconnect.models.User;


public class WorkerProfile extends AppCompatActivity  {

    User currentUserObject;
    public WorkerProfile(){
        super(R.layout.activity_worker_profile);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_worker_profile);
        Intent intent = getIntent();
        currentUserObject = (User) intent.getSerializableExtra("userObj");


        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().
                    add(R.id.profile_frag, ProfileFragment.newInstance(currentUserObject),"ProfileFragment").commit();


        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(WorkerProfile.this,WorkerHomePage.class);
        intent.putExtra("userObj",currentUserObject);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.history, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.history) {
            Intent i = new Intent(WorkerProfile.this,WorkerHistory.class);
            i.putExtra("userObj", currentUserObject);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}