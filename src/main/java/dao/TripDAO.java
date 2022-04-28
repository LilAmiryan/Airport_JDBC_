package dao;

import model.Trip;

import java.util.List;
import java.util.Set;

public interface TripDAO {
    Trip getById(long id);

    Set<Trip> getAll();

    Set<Trip> get(int offset, int perPage, String sort);

    void save(Trip trip);

    void update(int id,Trip trip);

    void delete(long tripId);

    List<Trip> getTripsFrom(String city);

    List<Trip> getTripsTo(String city);

}
