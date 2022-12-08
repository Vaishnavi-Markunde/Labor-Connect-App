package com.example.laborconnect.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.laborconnect.R;
import com.example.laborconnect.activities.RequestFragment;
import com.example.laborconnect.models.Request;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class UserRequestAdapter extends ArrayAdapter<Request> {


    private String TAG = "UserRequestAdapter";

    public UserRequestAdapter(@NonNull Context context, ArrayList<Request> requestModelArrayList) {
        super(context, 0, requestModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {

            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.user_card_item, parent, false);
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Request reqModel = getItem(position);

        String service = reqModel.getService();
        TextView title = listitemView.findViewById(R.id.title);
        TextView desc = listitemView.findViewById(R.id.desc);
        TextView add = listitemView.findViewById(R.id.add);
        TextView timespan = listitemView.findViewById(R.id.timespan);
        TableRow accepted = listitemView.findViewById(R.id.accepted);
        TextView acceptedBy = listitemView.findViewById(R.id.acceptedBy);
        TableRow contact = listitemView.findViewById(R.id.contact);
        TextView workerContact = listitemView.findViewById(R.id.workerContact);
        Button delete = listitemView.findViewById(R.id.delete);




        title.setText(reqModel.getDesc_title());
        desc.setText(reqModel.getDescription());
        add.setText(reqModel.getAddress());
        timespan.setText(reqModel.getTimespan());
        if(reqModel.getWorker_name()!=null){
             accepted.setVisibility(View.VISIBLE);
             acceptedBy.setText(reqModel.getWorker_name());

        }

        if(reqModel.getWorker_phnNo()!=null){
            contact.setVisibility(View.VISIBLE);
            workerContact.setText(reqModel.getWorker_phnNo());

        }

        if(reqModel.getStatus().equals("Searching")){
            delete.setVisibility(View.VISIBLE);
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(listitemView.getContext());
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alert.setTitle("Delete entry");
                alert.setMessage("Are you sure you want to delete?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {



                    public void onClick(DialogInterface dialog, int which) {
                        db.collection("User").document(reqModel.getUser_docid()).collection("Work Requests").document(reqModel.getUserReqId()).delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {

                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        db.collection("requests_" + service.toLowerCase(Locale.ROOT)).whereEqualTo("UserReqId",reqModel.getUserReqId()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                            @Override
                                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                System.out.println(queryDocumentSnapshots+" query");
                                                for(QueryDocumentSnapshot doc : queryDocumentSnapshots){
                                                    System.out.println("doc inside delete "+doc.getId());
                                                    db.collection("requests_" + service.toLowerCase(Locale.ROOT)).document(doc.getId()).delete();
                                                }
                                            }
                                        });
//                                        Log.d(TAG, "DocumentSnapshot work req successfully deleted! " + id);
                                    }


                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error deleting document", e);
                                    }
                                });
                    }
                });
                alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // close dialog
                        dialog.cancel();
                    }
                });
                alert.show();
            }
        });
       // status.setText(reqModel.getStatus());

        return listitemView;


    }
}