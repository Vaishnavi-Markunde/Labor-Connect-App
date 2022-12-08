package com.example.laborconnect.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.laborconnect.R;
import com.example.laborconnect.models.User;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddressActivity extends AppCompatActivity {
  Spinner state,city;
  EditText addline1,addline2,pincode;
  Button next;
  User currentUserObject;
  String service,desc,title;
  FirebaseFirestore db ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_address);
       state = findViewById(R.id.state_spinner);
       city = findViewById(R.id.city_spinner);
       addline1 = findViewById(R.id.addline1);
       addline2 = findViewById(R.id.addline2);
       pincode = findViewById(R.id.addpin);
       next = findViewById(R.id.nextBtn);

       db = FirebaseFirestore.getInstance();


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        service = extras.getString("service");
        currentUserObject = (User) extras.getSerializable("userObj");
        desc = extras.getString("desc");
        title = extras.getString("title");
        System.out.println(service+" "+currentUserObject);
        System.out.println("title in address activity "+title);


        if(currentUserObject.getAdd1()!=null) addline1.setText(currentUserObject.getAdd1().toString());
        if(currentUserObject.getAdd2()!=null) addline2.setText(currentUserObject.getAdd2().toString());
        if(currentUserObject.getPincode()!=null) pincode.setText(currentUserObject.getPincode().toString());




       next.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               String add1 = addline1.getText().toString();
               String add2 = addline2.getText().toString();
               String pin = pincode.getText().toString();

               if(add1.isEmpty()||add2.isEmpty()||pin.isEmpty()){
                   Toast.makeText(AddressActivity.this,"Please enter complete address",Toast.LENGTH_SHORT).show();
                   return;
               }

               String st = state.getSelectedItem().toString();
               String ct = city.getSelectedItem().toString();

               Map<String,Object> add = new HashMap<>();
               add.put("add1",add1);
               add.put("add2",add2);
               add.put("pincode",pin);
               add.put("city",ct);
               add.put("state",st);


               db.collection("User").document(currentUserObject.getDocId()).update(add);



               Intent intent = new Intent(AddressActivity.this,Timespan.class);
               intent.putExtra("add1",add1);
               intent.putExtra("add2",add2);
               intent.putExtra("pin",pin);
               intent.putExtra("state",st);
               intent.putExtra("city",ct);
               intent.putExtra("service",service);
               intent.putExtra("desc",desc);
               intent.putExtra("title",title);
               intent.putExtra("userObj",currentUserObject);
               startActivity(intent);







           }
       });



        ArrayAdapter<CharSequence> adapter_state = ArrayAdapter.createFromResource(this,
                R.array.state_array, android.R.layout.simple_spinner_item);
        adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       state.setAdapter(adapter_state);

       if(currentUserObject.getState()!=null) {
           int p = adapter_state.getPosition(currentUserObject.getState());
           state.setSelection(p);

       }

        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              if(position==0){
                  ArrayAdapter<CharSequence> adapter_city = ArrayAdapter.createFromResource(parent.getContext(),
                          R.array.select_state_first, android.R.layout.simple_spinner_item);

                 city.setAdapter(adapter_city);
              }

               if(position==1) {
                   ArrayAdapter<CharSequence> adapter_city = ArrayAdapter.createFromResource(parent.getContext(),
                           R.array.city_mah, android.R.layout.simple_spinner_item);

                   city.setAdapter(adapter_city);
                   if(currentUserObject.getCity()!=null) {
                       int p = adapter_city.getPosition(currentUserObject.getCity());
                       city.setSelection(p);

                   }
               }
               else if(position==2) {
                   ArrayAdapter<CharSequence> adapter_city = ArrayAdapter.createFromResource(parent.getContext(),
                           R.array.city_kar, android.R.layout.simple_spinner_item);

                   city.setAdapter(adapter_city);

                   if(currentUserObject.getCity()!=null) {
                       int p = adapter_city.getPosition(currentUserObject.getCity());
                       city.setSelection(p);

                   }
               }

                }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
       });








    }
}