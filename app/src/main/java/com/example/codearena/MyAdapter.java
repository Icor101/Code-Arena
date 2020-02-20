package com.example.codearena;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<ContestDetails> mDataSet;
    private MyViewHolder viewHolder;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_card_view, parent, false);
        viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ContestDetails curr = mDataSet.get(position);
        holder.contest_title.setText(curr.contest_title);
        holder.platform.setText(curr.platform);
        holder.starting_time.setText(curr.start_time);
        holder.duration.setText(curr.duration);
        //holder.parent.setBackgroundColor(Color.rgb(0,0,10));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView contest_title, starting_time, platform, duration;
        LinearLayout parent;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            contest_title = itemView.findViewById(R.id.contest_title);
            starting_time = itemView.findViewById(R.id.starting_time);
            platform = itemView.findViewById(R.id.platform);
            duration = itemView.findViewById(R.id.duration);

        }
    }

    MyAdapter(List<ContestDetails> myDataSet) {
        mDataSet = myDataSet;
    }
}
