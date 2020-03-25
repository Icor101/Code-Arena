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
import android.widget.Toast;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    public static List<ContestDetails> mDataSet = new ArrayList<>();
    private MyViewHolder viewHolder;
    static boolean past=false,live=false,future=false;


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_card_view, parent, false);
        viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final ContestDetails curr = mDataSet.get(position);
        Log.d("Set", FilterFragment.set.toString());
        String startDateTime = curr.start_time;
        String[] timeDetailsArr = startDateTime.split(" ");
        String time = timeDetailsArr[timeDetailsArr.length - 1];
        LocalTime startTime = LocalTime.parse(time);
        startTime = startTime.plusHours(5).plusMinutes(30);
        String modifiedTime = timeDetailsArr[0] + " " + timeDetailsArr[1] + " " + startTime.toString();

        if (FilterFragment.set.contains(curr.platform.split("\\.")[0]) || FilterFragment.set.size() == 0 || FilterFragment.set.contains("other")) {
            holder.contest_title.setText(curr.contest_title);
            holder.platform.setText(curr.platform);
            holder.starting_time.setText(modifiedTime);
            holder.duration.setText(curr.duration);
            ImageButton imageButton = holder.imageButton;
            if (curr.label.equals("past")) {
                past=true;
                holder.reminderTv.setText("Contest over");
                imageButton.setImageResource(R.drawable.ic_contest_over);
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast toast = Toast.makeText(context, "Contest already over", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
            } else {
                if(curr.label.equals("live"))
                    live=true;
                else if(curr.label.equals("future"))
                    future=true;
                imageButton.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_INSERT);
                        intent.setType("vnd.android.cursor.item/event");

                        String properStartDateTime = "";
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
                        int noOfDays, hours, minutes, noOfYears;
                        if (interval.contains("days")) {
                            String[] arr = interval.split(" ");
                            noOfDays = Integer.parseInt(arr[0]);
                            zdt1 = zdt1.plusDays(noOfDays);
                        } else if (interval.contains("years")) {
                            String[] arr = interval.split(" ");
                            noOfYears = Integer.parseInt(arr[0]);
                            zdt1 = zdt1.plusYears(noOfYears);
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
                ImageButton shareBtn = holder.shareButton;
                shareBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        String shareBody = context.getText(R.string.contest_title) + " " + curr.contest_title + "\n" + context.getText(R.string.contest_platform) + " " + curr.platform + "\n" + context.getText(R.string.contest_start_time) + " " + curr.start_time + "\n" + context.getText(R.string.contest_duration) + " " + curr.duration;
                        String shareSub = "Sharing contest details";
                        intent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                        intent.putExtra(Intent.EXTRA_TEXT, shareBody);
                        context.startActivity(Intent.createChooser(intent, "Share via"));
                    }
                });
            }
        } else {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0,0));
        }

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView contest_title, starting_time, platform, duration, reminderTv;
        ImageButton imageButton, shareButton;
        LinearLayout parent;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            contest_title = itemView.findViewById(R.id.contest_title);
            starting_time = itemView.findViewById(R.id.starting_time);
            platform = itemView.findViewById(R.id.platform);
            duration = itemView.findViewById(R.id.duration);
            imageButton = itemView.findViewById(R.id.reminderIcon);
            reminderTv = itemView.findViewById(R.id.reminderTextView);
            shareButton = itemView.findViewById(R.id.shareButton);
        }
    }

    MyAdapter(Context context, List<ContestDetails> myDataSet) {
        this.context = context;
        mDataSet.addAll(myDataSet);
    }

    public MyAdapter() {
    }
}
