package com.example.empty_project;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

//Adapter zm

public class LogTextAdapter extends RecyclerView.Adapter<LogTextAdapter.LogViewHolder> {

    private String[] mDataset;

    // Provide a suitable constructor (depends on the kind of dataset)
    public LogTextAdapter(String[] myDataset) {
        this.mDataset = myDataset;
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class LogViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public LogViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.na);
        }
        // each data item is just a string in this case
        //public TextView textView;
        //public MyViewHolder(TextView v) {
         //   super(v);
          //  textView = v;
        //}
    }

    // Create new view (invoked by the layout manager)
    @NonNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view

        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);

        LogViewHolder vh = new LogViewHolder(v);
        return vh;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
        // - get element from your dataset at this position.
        // - replace the contents of the view with that element
        holder.textView.setText(mDataset[position]);
    }


    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}


