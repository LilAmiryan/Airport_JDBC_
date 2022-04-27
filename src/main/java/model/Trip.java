package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Trip {
    private long trip_no;
    private int company_id;
    private String town_from;
    private String town_to;
    private LocalTime time_out;
    private LocalTime time_in;

    public Trip() {
    }

    public Trip(long trip_no, int company_id, String town_from, String town_to, LocalTime time_out, LocalTime time_in) {
        this.trip_no=trip_no;
        this.company_id = company_id;
        this.town_from = town_from;
        this.town_to = town_to;
        this.time_out = time_out;
        this.time_in = time_in;
    }
    public Trip(int company_id, String town_from, String town_to, LocalTime time_out, LocalTime time_in) {
        this.company_id = company_id;
        this.town_from = town_from;
        this.town_to = town_to;
        this.time_out = time_out;
        this.time_in = time_in;
    }

    public void setTrip_no(long trip_no) {
        this.trip_no = trip_no;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public String getTown_from() {
        return town_from;
    }

    public void setTown_from(String town_from) {
        this.town_from = town_from;
    }

    public String getTown_to() {
        return town_to;
    }

    public void setTown_to(String town_to) {
        this.town_to = town_to;
    }

    public LocalTime getTime_out() {
        return time_out;
    }

    public void setTime_out(LocalTime time_out) {
        this.time_out = time_out;
    }

    public LocalTime getTime_in() {
        return time_in;
    }

    public void setTime_in(LocalTime time_in) {
        this.time_in = time_in;
    }

    public long getTrip_no() {
        return trip_no;
    }

    public void setTrip_no(int trip_no) {
        this.trip_no = trip_no;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "trip_no=" + trip_no +
                ", company_id=" + company_id +
                ", town_from='" + town_from + '\'' +
                ", town_to='" + town_to + '\'' +
                ", time_out='" + time_out + '\'' +
                ", time_in='" + time_in + '\'' +
                '}';
    }
}
