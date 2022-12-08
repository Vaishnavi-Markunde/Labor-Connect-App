package com.example.laborconnect.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.laborconnect.Adapters.UserRequestAdapter;
import com.example.laborconnect.R;
import com.example.laborconnect.models.Request;
import com.example.laborconnect.models.User;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PendingRequestsFragment extends Fragment {
    private static final String TAG = "UserRequestPage";
    User user;
    private FirebaseFirestore db;
    Request req;
    ArrayList<Request> requests;
    UserRequestAdapter adapter;

    ListView reqGV;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        UserHomePage userHomePage = (UserHomePage) getActivity();

        user = userHomePage.getCurrentUser();

        System.out.println("user name"+user.getName());
        return inflater.inflate(R.layout.fragment_pending,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requests = new ArrayList<>();
        reqGV = view.findViewById(R.id.pendingRequests);
        db = FirebaseFirestore.getInstance();


        db.collection("User").document(user.getDocId()).collection("Work Requests").orderBy("timestamp", Query.Direction.DESCENDING)
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
                            req.setUserReqId(id);
                            //req.setService(prof.toLowerCase(Locale.ROOT));
                            // req.setStatus("searching");
                            requests.add(req);

                        }
                        System.out.println("my req "+requests);
                        if (getActivity()!=null){
                            adapter = new UserRequestAdapter(getActivity(),requests);
                            reqGV.setAdapter(adapter);
                        }
                    }
                });




    }
}
