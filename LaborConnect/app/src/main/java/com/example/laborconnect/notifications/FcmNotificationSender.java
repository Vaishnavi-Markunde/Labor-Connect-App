package com.example.laborconnect.notifications;

import android.app.Activity;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.laborconnect.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FcmNotificationSender {

    String userFcmToken;
    String title;
    String body;
    Context mContext;
    Activity mActivity;

    private RequestQueue requestQueue;
    private final String postUrl = "https://fcm.googleapis.com/fcm/send";

    //Enter your fcm serverKey here
    private final String fcmServerKey ="key=";

    public FcmNotificationSender(String userFcmToken, String title, String body, Context mContext, Activity mActivity) {
        this.userFcmToken = userFcmToken;
        this.title = title;
        this.body = body;
        this.mContext = mContext;
        this.mActivity = mActivity;
    }

    public void SendNotification(){
        requestQueue = Volley.newRequestQueue(mActivity);
        JSONObject mainObj = new JSONObject();
        try{
            mainObj.put("to",userFcmToken);
            JSONObject notiObject = new JSONObject();
            notiObject.put("title",title);
            notiObject.put("body",body);
            notiObject.put("icon", R.drawable.notification_icon);

            mainObj.put("notification",notiObject);


            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, postUrl, mainObj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                public Map<String,String> getHeaders()  {
                    Map<String,String> params = new HashMap<>();
                params.put("Authorization",fcmServerKey);
                  return params;
                }
            };


requestQueue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


