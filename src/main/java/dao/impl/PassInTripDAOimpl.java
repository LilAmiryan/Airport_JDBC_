package dao.impl;

import dao.PassInTripDAO;
import model.PassInTrip;
import model.Passenger;
import service.DatabaseConnectionService;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class PassInTripDAOimpl implements PassInTripDAO {


    public void getPassInTripInformationFromFile(File file) throws IOException {
        String line = null;
        String[] words = null;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        while ((line = bufferedReader.readLine()) != null) {
            if (line.contains("'")) {
                line = line.replace("'", "՛");
            }
            words = line.split(",");
            PassInTrip passInTrip = new PassInTrip();
            for (int i = 0; i < words.length; i++) {

                System.out.println(words[i]+" ");
                passInTrip.setTrip_id(Long.parseLong(words[0]));
                passInTrip.setPass_id(Long.parseLong(words[1]));

                passInTrip.setDate(LocalDateTime.parse(words[2], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")).toLocalDate());
                passInTrip.setPlace(words[3]);

            }
            PassInTripDAOimpl passInTripDAOimpl=new PassInTripDAOimpl();
            passInTripDAOimpl.save(passInTrip);
        }

    }

    @Override
    public void save(PassInTrip passInTrip) {

        Connection connection = DatabaseConnectionService.DB_INSTANCE.createConnection();
        ResultSet resultSet = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO pass_in_trip (trip_id,pass_id, date,place)  VALUES (?,?,?,?)");
            preparedStatement.setLong(1, passInTrip.getTrip_id());
            preparedStatement.setLong(2, passInTrip.getPass_id());
            preparedStatement.setDate(3,Date.valueOf(passInTrip.getDate()));
            preparedStatement.setString(4, passInTrip.getPlace());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
