package dao.impl;

import dao.PassengerDAO;
import model.PassInTrip;
import model.Passenger;
import model.Trip;
import service.DatabaseConnectionService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PassengerDAOimpl implements PassengerDAO {

    /**
     * @param id
     * @return
     */
    @Override
    public Passenger getById(long id) {
        Connection connection =
                DatabaseConnectionService.DB_INSTANCE.createConnection();

        PreparedStatement preparedStatement;

        ResultSet resultSet;
        Passenger passenger = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM Passenger WHERE id = ?"
            );

            preparedStatement.setLong(1, id);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                passenger = new Passenger(
                        resultSet.getString("passenger_name"),
                        resultSet.getString("passenger_phone"),
                        resultSet.getInt("address_id")
                );
            }

        } catch (SQLException throwables) {
            System.out.println("Wrong query for Employee with id=" + id);
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                System.out.println("Connection cannot close");
            }
        }

        return passenger;
    }

    /**
     * @return
     */
    @Override
    public Set<Passenger> getAll() {
        Set<Passenger> passengers = null;
        try (Connection connection = DatabaseConnectionService
                .DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Passenger VALUES (?,?,?)");
             ResultSet resultSet =
                     preparedStatement.executeQuery(
                             "SELECT * FROM Passenger")) {
            passengers = new HashSet<>();

            Passenger passenger;
            while (resultSet.next()) {
                passenger = new Passenger(
                        resultSet.getString("passenger_name"),
                        resultSet.getString("passenger_phone"),
                        resultSet.getInt("address_id")
                );

                passengers.add(passenger);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return passengers;
    }


    /**
     * @param filename
     * @throws IOException
     */
    public static void getPassengersFromFile(File filename) throws IOException {

        Passenger passenger = new Passenger();
        PassengerDAOimpl passengerDAOimpl = new PassengerDAOimpl();
        String line;
        String words[] = null;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
        while ((line = bufferedReader.readLine()) != null) {
            if (line.contains("'")) {
                line = line.replace("'", "՛");
            }
            words = line.split(",");
            for (int i = 0; i < words.length; i++) {
                System.out.print(words[i] + " ");
                passenger.setPassengerName(words[0]);
                passenger.setPassengerPhone(words[1]);
                passenger.setAddress_id(getAddressIdForConnectionWithPassengerAddres_ID(words));
            }
            passengerDAOimpl.save(passenger);
            System.out.println();
        }
        System.out.println();

        bufferedReader.close();

        return;

    }

    /**
     * @param passenger
     */
    @Override
    public void save(Passenger passenger) {
        Connection connection =
                DatabaseConnectionService.DB_INSTANCE.createConnection();

        String query = "INSERT INTO passenger (passenger_name, passenger_phone, address_id) " +
                " VALUES (?, ?, ?)";
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            pstmt.setString(1, passenger.getPassengerName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            pstmt.setString(2, passenger.getPassengerPhone());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            pstmt.setInt(3, passenger.getAddress_id());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param id
     * @param passenger
     */
    @Override
    public void update(int id, Passenger passenger) {
        Connection connection = DatabaseConnectionService.DB_INSTANCE.createConnection();


        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Passenger " +
                    "SET passenger_name = ?, " +
                    "passenger_phone = ?, " +
                    "address_id = ? " +
                    "WHERE id=?");
            preparedStatement.setString(1, passenger.getPassengerName());
            preparedStatement.setString(2, passenger.getPassengerPhone());
            preparedStatement.setInt(3, passenger.getAddress_id());
            preparedStatement.setInt(4, 2);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param passengerId
     */
    @Override
    public void delete(long passengerId) {
        Connection connection = DatabaseConnectionService.DB_INSTANCE.createConnection();
        Passenger passenger = new Passenger();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Passenger " +
                    "WHERE id=?");

            preparedStatement.setLong(1, passengerId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param offset
     * @param perPage
     * @param sort
     * @return
     */
    @Override
    public Set<Passenger> get(int offset, int perPage, String sort) {
        Connection connection = DatabaseConnectionService.DB_INSTANCE.createConnection();

        Set<Passenger> passengers = null;
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PASSENGER order by");
            resultSet = preparedStatement.executeQuery(
                    "SELECT * FROM PASSENGER  order by passenger_name LIMIT  " + (offset * perPage) + "," + perPage);
            {

                passengers = new HashSet<>();

                Passenger passenger = null;
                while (resultSet.next()) {
                    passenger = new Passenger(
                            resultSet.getString("passenger_name"),
                            resultSet.getString("passenger_phone"),
                            resultSet.getInt("address_id")
                    );
                    passengers.add(passenger);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return passengers;
    }


    @Override
    public List<Passenger> getPassengersOfTrip(long tripNumber) {
        Connection connection = DatabaseConnectionService.DB_INSTANCE.createConnection();
        List<Passenger> passengers = null;
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM Passenger inner join  Pass_intrip on Passenger.id=Pass_in_trip.pass_id where id=?");
            preparedStatement.setLong(1, tripNumber);
            resultSet = preparedStatement.executeQuery();
            passengers = new ArrayList<>();

            Passenger passenger = null;
            while (resultSet.next()) {
                passenger = new Passenger(
                        resultSet.getString("passenger_name"),
                        resultSet.getString("passenger_phone"),
                        resultSet.getInt("address_id")
                );
                passengers.add(passenger);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return passengers;
    }

    public static int getAddressIdForConnectionWithPassengerAddres_ID(String[] word) {
        Connection connection = DatabaseConnectionService.DB_INSTANCE.createConnection();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        int addressId = 0;
        try {
            preparedStatement = connection.prepareStatement("SELECT address_id from Address WHERE country=? and city=?");
            preparedStatement.setString(1, word[2].replace("'", ""));
            preparedStatement.setString(2, word[3].replace("'", ""));
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                addressId = resultSet.getInt("address_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                ;
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return addressId;


    }

    @Override
    public void registerTrip(PassInTrip passInTrip) {

        PassInTripDAOimpl passInTripDAOimpl=new PassInTripDAOimpl();
        passInTripDAOimpl.save(passInTrip);
    }

    @Override
    public void cancelTrip(long passengerId, long tripNumber) {
        Connection connection=DatabaseConnectionService.DB_INSTANCE.createConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM pass_in_trip where pass_id=? and  trip_id=?")) {
        preparedStatement.setLong(1,passengerId);
        preparedStatement.setLong(2,tripNumber);
        preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
