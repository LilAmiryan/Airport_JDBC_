package model;

import java.sql.Date;
import java.time.LocalDate;

public class PassInTrip {
    private long trip_id;
    private long pass_id;
    private LocalDate date;
    private String place;

    public PassInTrip() {
    }

    public PassInTrip(long trip_id, long pass_id, LocalDate date, String place) {
        this.trip_id = trip_id;
        this.pass_id = pass_id;
        this.date = date;
        this.place = place;
    }

    public long getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(long trip_id) {
        this.trip_id = trip_id;
    }

    public long getPass_id() {
        return pass_id;
    }

    public void setPass_id(long pass_id) {
        this.pass_id = pass_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "PassInTrip{" +
                "trip_id=" + trip_id +
                ", pass_id=" + pass_id +
                ", date=" + date +
                ", place=" + place +
                '}';
    }
}
