package com.example.codearena;

class ContestDetails {
    String start_time;
    String contest_title;
    String platform;
    String duration;
    String label;

    ContestDetails(String label,String start_time, String contest_title, String platform, String duration) {
        this.label = label;
        this.start_time = start_time;
        this.contest_title = contest_title;
        this.platform = platform;
        this.duration = duration;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getContest_title() {
        return contest_title;
    }

    public String getPlatform() {
        return platform;
    }

    public String getDuration() {
        return duration;
    }

    public String getLabel(){
        return label;
    }

    @Override
    public String toString() {
        return "ContestDetails{" +
                "label= '" + label +'\''+
                "start_time='" + start_time + '\'' +
                ", contest_title='" + contest_title + '\'' +
                ", platform='" + platform + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }

}
