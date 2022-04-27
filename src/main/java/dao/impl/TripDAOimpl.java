package dao.impl;

import dao.TripDAO;
import model.Passenger;
import model.Trip;
import service.DatabaseConnectionService;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TripDAOimpl implements TripDAO {
    @Override
    public Trip getById(long id) {
        Connection connection =
                DatabaseConnectionService.DB_INSTANCE.createConnection();

        PreparedStatement preparedStatement;

        ResultSet resultSet;
        Trip trip = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM Trip WHERE trip_no = ?"
            );

            preparedStatement.setLong(1, id);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                trip = new Trip(
                        resultSet.getInt("trip_no"),
                        resultSet.getInt("company_id"),
                        resultSet.getString("town_from"),
                        resultSet.getString("town_to"),
                        resultSet.getTime("time_out").toLocalTime(),
                        resultSet.getTime("time_in").toLocalTime()
                );
            }

        } catch (SQLException throwables) {
            System.out.println("Wrong query for Trip with id=" + id);
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                System.out.println("Connection cannot close");
            }
        }

        return trip;
    }

    @Override
    public Set<Trip> getAll() {
        Set<Trip> trips = null;
        try (Connection connection = DatabaseConnectionService
                .DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Trip VALUES (?,?,?,?,?,?)");
             ResultSet resultSet =
                     preparedStatement.executeQuery(
                             "SELECT * FROM Trip")) {
            trips = new HashSet<>();

            Trip trip;
            while (resultSet.next()) {
                trip = new Trip(

                        resultSet.getInt("company_id"),
                        resultSet.getString("town_from"),
                        resultSet.getString("town_to"),
                        resultSet.getTime("time_out").toLocalTime(),
                        resultSet.getTime("time_in").toLocalTime()
                );

                trips.add(trip);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return trips;
    }


    @Override
    public Set<Trip> get(int offset, int perPage, String sort) {

        Connection connection = DatabaseConnectionService.DB_INSTANCE.createConnection();

        Set<Trip> trips = null;
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Trip order by");
            resultSet = preparedStatement.executeQuery(
                    "SELECT * FROM Trip  order by town_from LIMIT  " + (offset * perPage) + "," + perPage);
            {

                trips = new HashSet<>();

                Trip trip = null;
                while (resultSet.next()) {
                    trip = new Trip(
                            resultSet.getInt("trip_no"),
                            resultSet.getInt("company_id"),
                            resultSet.getString("town_from"),
                            resultSet.getString("town_to"),
                            resultSet.getTime("time_out").toLocalTime(),
                            resultSet.getTime("time_in").toLocalTime()

                    );
                    trips.add(trip);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trips;
    }


    public void getTripInformationFromFile(File file) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line = null;
        String[] words = null;
        Trip trip = new Trip();
        TripDAOimpl tripDAOimpl = new TripDAOimpl();
        while ((line = bufferedReader.readLine()) != null) {
            if (line.contains("'")) {
                line = line.replace("'", "՛");
            }
            words = line.split(",");

            for (int i = 0; i < words.length; i++) {
                System.out.println(words[i] + " ");
                trip.setTrip_no(Long.parseLong(words[0]));
                trip.setCompany_id(Integer.parseInt(words[1]));
                trip.setTown_from(words[2]);
                trip.setTown_to(words[3]);
                trip.setTime_out(LocalDateTime.parse(words[4],
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")).toLocalTime());
                trip.setTime_in(LocalDateTime.parse(words[5],
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")).toLocalTime());
            }
            tripDAOimpl.save(trip);
        }
    }


    public static int getCompanyIdForConnectionWithTrip() {
        Connection connection = DatabaseConnectionService.DB_INSTANCE.createConnection();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        int company_id = 0;
        try {
            preparedStatement = connection.prepareStatement("SELECT id from Company ");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                company_id = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return company_id;
    }

    @Override
    public void save(Trip trip) {
        Connection connection = DatabaseConnectionService.DB_INSTANCE.createConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO TRIP values (?,?,?,?,?,?)");
            preparedStatement.setLong(1, trip.getTrip_no());
            preparedStatement.setInt(2, trip.getCompany_id());
            preparedStatement.setString(3, trip.getTown_from());
            preparedStatement.setString(4, trip.getTown_to());
            preparedStatement.setTime(5, Time.valueOf(trip.getTime_out()));
            preparedStatement.setTime(6, Time.valueOf(trip.getTime_in()));
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(int id, Trip trip) {
        Connection connection = DatabaseConnectionService.DB_INSTANCE.createConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Trip " +
                    "SET company_id = ?, " +
                    "town_from = ? " +
                    "town_to = ? " +
                    "time_out = ? " +
                    "time_in = ? " +
                    "WHERE trip_no=?");
            preparedStatement.setInt(1, trip.getCompany_id());
            preparedStatement.setString(2, trip.getTown_from());
            preparedStatement.setString(3, trip.getTown_to());
            preparedStatement.setTime(4, Time.valueOf(trip.getTime_out()));
            preparedStatement.setTime(5, Time.valueOf(trip.getTime_in()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void delete(long tripId) {
        Connection connection = DatabaseConnectionService.DB_INSTANCE.createConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Trip " +
                    "WHERE id=?");

            preparedStatement.setLong(1, tripId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Trip> getTripsFrom(String city) {
        Connection connection = DatabaseConnectionService.DB_INSTANCE.createConnection();
        List<Trip> trips = null;
        ResultSet resultSet = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM Trip  where town_from=?");

            preparedStatement.setString(1, city);
            resultSet = preparedStatement.executeQuery();
            trips = new ArrayList<>();

            Trip trip = null;
            while (resultSet.next()) {
                trip = new Trip(
                        resultSet.getInt("trip_no"),
                        resultSet.getInt("company_id"),
                        resultSet.getString("town_from"),
                        resultSet.getString("town_to"),
                        resultSet.getTime("time_out").toLocalTime(),
                        resultSet.getTime("time_in").toLocalTime()
                );
                trips.add(trip);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trips;
    }


    @Override
    public List<Trip> getTripsTo(String city) {
        Connection connection = DatabaseConnectionService.DB_INSTANCE.createConnection();
        List<Trip> trips = null;
        ResultSet resultSet = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM Trip  where town_to=?");

            preparedStatement.setString(1, city);
            resultSet = preparedStatement.executeQuery();
            trips = new ArrayList<>();

            Trip trip = null;
            while (resultSet.next()) {
                trip = new Trip(
                        resultSet.getInt("trip_no"),
                        resultSet.getInt("company_id"),
                        resultSet.getString("town_from"),
                        resultSet.getString("town_to"),
                        resultSet.getTime("time_out").toLocalTime(),
                        resultSet.getTime("time_in").toLocalTime()
                );
                trips.add(trip);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trips;
    }
}

