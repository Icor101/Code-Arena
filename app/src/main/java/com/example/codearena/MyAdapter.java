package com.example.codearena;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context context;
    private List<ContestDetails> mDataSet = new ArrayList<>();
    private MyViewHolder viewHolder;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_card_view, parent, false);
        viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        ContestDetails curr = mDataSet.get(position);
        holder.contest_title.setText(curr.contest_title);
        holder.platform.setText(curr.platform);
        holder.starting_time.setText(curr.start_time);
        holder.duration.setText(curr.duration);
        ImageButton imageButton = holder.imageButton;
        imageButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setType("vnd.android.cursor.item/event");

                String properStartDateTime = "";
                long now = System.currentTimeMillis();
                int month = LocalDate.now().getMonth().ordinal(), year = LocalDate.now().getYear();
                String beginTime = holder.starting_time.getText().toString();
                String[] timeDetailsArray = beginTime.split(" ");
                String[] dateDetailsArray = timeDetailsArray[0].split("\\.");
                Log.d("timeDetailsArray:", Arrays.toString(timeDetailsArray));
                if (Integer.parseInt(dateDetailsArray[1]) < month) {
                    properStartDateTime += dateDetailsArray[0] + "." + dateDetailsArray[1] + "." + (year + 1) + " " + timeDetailsArray[1] + " " + timeDetailsArray[2];
                } else
                    properStartDateTime += dateDetailsArray[0] + "." + dateDetailsArray[1] + "." + (year) + " " + timeDetailsArray[1] + " " + timeDetailsArray[2];

                DateTimeFormatter df = DateTimeFormatter.ofPattern("dd.MM.yyyy E HH:mm");
                LocalDateTime properStartDateTimeObj = LocalDateTime.parse(properStartDateTime, df);
                ZonedDateTime zdt1 = ZonedDateTime.of(properStartDateTimeObj, ZoneId.systemDefault());

                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, zdt1.toInstant().toEpochMilli());

                String interval = holder.duration.getText().toString();
                int noOfDays, hours, minutes;
                if (interval.contains("days")) {
                    String[] arr = interval.split(" ");
                    noOfDays = Integer.parseInt(arr[0]);
                    zdt1 = zdt1.plusDays(noOfDays);
                } else {
                    String[] arr = interval.split(":");
                    hours = Integer.parseInt(arr[0]);
                    minutes = Integer.parseInt(arr[1]);
                    zdt1 = zdt1.plusHours(hours).plusMinutes(minutes);
                }

                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, zdt1.toInstant().toEpochMilli());
                intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);

                intent.putExtra(CalendarContract.Events.TITLE, holder.contest_title.getText().toString());
                intent.putExtra(CalendarContract.Events.DESCRIPTION, "This is a coding contest");
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, holder.platform.getText().toString());
                intent.putExtra(CalendarContract.Events.RRULE, "FREQ=YEARLY");

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView contest_title, starting_time, platform, duration;
        ImageButton imageButton;
        LinearLayout parent;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            contest_title = itemView.findViewById(R.id.contest_title);
            starting_time = itemView.findViewById(R.id.starting_time);
            platform = itemView.findViewById(R.id.platform);
            duration = itemView.findViewById(R.id.duration);
            imageButton = itemView.findViewById(R.id.reminderIcon);

        }
    }

    MyAdapter(Context context, List<ContestDetails> myDataSet) {
        this.context = context;
        mDataSet.addAll(myDataSet);
    }
}
