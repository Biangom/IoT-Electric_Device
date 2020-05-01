package com.example.iot_electronic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class LogDataAdapter extends RecyclerView.Adapter<LogDataAdapter.LogViewHolder> {

    private String[] mDataset;
    private ArrayList<Data> listData = new ArrayList<>();

    // Provide a suitable constructor (depends on the kind of dataset)
    public LogDataAdapter(String[] myDataset) {
        this.mDataset = myDataset;
    }

    public LogDataAdapter(ArrayList<Data> list) {
        this.listData = list;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class LogViewHolder extends RecyclerView.ViewHolder {
        public TextView nameText;
        public TextView valueText;
        public TextView dateText;
        public TextView timeText;


        public LogViewHolder(View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.nameText);
            valueText = itemView.findViewById(R.id.valueText);
            dateText = itemView.findViewById(R.id.dateText);
            timeText = itemView.findViewById(R.id.timeText);
        }
        // each data item is just a string in this case
        //public TextView textView;
        //public MyViewHolder(TextView v) {
        //   super(v);
        //  textView = v;
        //}

        void onBind(Data data) {
            nameText.setText(data.name);
            valueText.setText(data.value);
            dateText.setText(data.time.format(DateTimeFormatter.ofPattern("MM-dd")));
            timeText.setText(data.time.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        }
    }

    // Create new view (invoked by the layout manager)
    @NonNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);

        return new LogViewHolder(view);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
        // - get element from your dataset at this position.
        // - replace the contents of the view with that element
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}