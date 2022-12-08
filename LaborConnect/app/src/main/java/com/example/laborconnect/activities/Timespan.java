package com.example.laborconnect.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;
import com.example.laborconnect.R;
import com.example.laborconnect.models.User;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Timespan extends AppCompatActivity {
    String service, add1, add2, pin, st, ct, timespan,desc;
    User currentUserObject;
    Button confirmBtn;
    String desc_title;



    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timespan);
       // confirmBtn = findViewById(R.id.confirmBtn);

        Intent intent = getIntent();
        extras = intent.getExtras();
        desc_title = extras.getString("title");
        service = extras.getString("service");
        add1 = extras.getString("add1");
        add2 = extras.getString("add2");
        st = extras.getString("state");
        ct = extras.getString("city");
        pin = extras.getString("pin");
        desc = extras.getString("desc");
        currentUserObject = (User) extras.getSerializable("userObj");
        System.out.println("title in timespan activity "+desc_title);

//        confirmBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TOPIC = "/topics/userABC"; //topic must match with what the receiver subscribed to
//                NOTIFICATION_TITLE = "You have got a new service request";
//                NOTIFICATION_MESSAGE = "Details";
//
//
//                JSONObject notification = new JSONObject();
//                JSONObject notifcationBody = new JSONObject();
//                try {
//                    notifcationBody.put("title", NOTIFICATION_TITLE);
//                    notifcationBody.put("message", NOTIFICATION_MESSAGE);
//
//                    notification.put("to", TOPIC);
//                    notification.put("data", notifcationBody);
//                } catch (JSONException e) {
//                    Log.e(TAG, "onCreate: " + e.getMessage() );
//                }
//                sendNotification(notification);
//            }
//        });

    }


    public void openOne(View view) {
        Button button = (Button) view;

        timespan = button.getText().toString();
        Intent intent = new Intent(Timespan.this, ConfirmationActivity.class);
        intent.putExtra("prev",extras);
        intent.putExtra("timespan", timespan);
        intent.putExtra("add1",add1);
        intent.putExtra("add2",add2);
        intent.putExtra("pin",pin);
        intent.putExtra("state",st);
        intent.putExtra("city",ct);
        intent.putExtra("service",service);
        intent.putExtra("userObj",currentUserObject);
        intent.putExtra("desc",desc);
        intent.putExtra("title",desc_title);
        startActivity(intent);

    }
}

//    private void sendNotification(JSONObject notification) {
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.i(TAG, "onResponse: " + response.toString());
////                        edtTitle.setText("");
////                        edtMessage.setText("");
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(Timespan.this, "Request error", Toast.LENGTH_LONG).show();
//                        Log.i(TAG, "onErrorResponse: Didn't work");
//                    }
//                }){
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("Authorization", serverKey);
//                params.put("Content-Type", contentType);
//                return params;
//            }
//        };
//        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
//    }
