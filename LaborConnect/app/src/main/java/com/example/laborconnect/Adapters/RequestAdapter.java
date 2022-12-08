package com.example.laborconnect.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.laborconnect.R;
import com.example.laborconnect.activities.CompletedRequestsFragment;
import com.example.laborconnect.activities.MainActivity;
import com.example.laborconnect.activities.ServiceDetails;
import com.example.laborconnect.activities.UserHomePage;
import com.example.laborconnect.activities.WorkerHomePage;
import com.example.laborconnect.models.Request;
import com.example.laborconnect.models.User;
import com.example.laborconnect.notifications.FcmNotificationSender;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.LogRecord;

public class RequestAdapter extends ArrayAdapter<Request> {
    private Context context;
    User currentUserObject;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();



    public RequestAdapter(@NonNull Context context, ArrayList<Request> requestModelArrayList, User currentUserObject) {
        super(context, 0, requestModelArrayList);
        this.context = context;
        this.currentUserObject = currentUserObject;
       // super(context, 0, requestModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.card_item, parent, false);
        }

        Request reqModel = getItem(position);
        TextView name = listitemView.findViewById(R.id.name);
        TextView title = listitemView.findViewById(R.id.reqTitle);

        Button view = listitemView.findViewById(R.id.btnView);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ServiceDetails.class);
                intent.putExtra("reqModel", reqModel);
                intent.putExtra("userObj",currentUserObject);

                // intent.putExtra("worker_docid",reqModel.getWorker_docid());
                context.startActivity(intent);
            }
        });

        name.setText("Work Request by "+reqModel.getName());
        title.setText(reqModel.getDesc_title());




        //Intent intent = new Intent(WorkerHomePage.this,);



       // courseTV.setText(c());
       // courseIV.setImageResource(courseModel.getImgid());
        return listitemView;
    }






}