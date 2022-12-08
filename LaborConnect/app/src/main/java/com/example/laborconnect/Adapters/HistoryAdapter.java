package com.example.laborconnect.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.laborconnect.R;
import com.example.laborconnect.models.Request;


import java.util.ArrayList;

public class HistoryAdapter extends ArrayAdapter<Request> {


    private String TAG = "HistoryAdapter";

    public HistoryAdapter(@NonNull Context context, ArrayList<Request> requestModelArrayList) {
        super(context, 0, requestModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {



        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.history_card_item, parent, false);
        }

        Request reqModel = getItem(position);


        TextView name = listitemView.findViewById(R.id.name);
        TextView title = listitemView.findViewById(R.id.title);
        TextView desc = listitemView.findViewById(R.id.desc);
        TextView add = listitemView.findViewById(R.id.add);
        TextView timespan = listitemView.findViewById(R.id.timespan);

        name.setText(reqModel.getName());
        title.setText(reqModel.getDesc_title());
        desc.setText(reqModel.getDescription());
        add.setText(reqModel.getAddress());
        timespan.setText(reqModel.getTimespan());


        return listitemView;
    }

}
