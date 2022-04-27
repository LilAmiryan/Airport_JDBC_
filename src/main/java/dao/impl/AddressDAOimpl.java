package dao.impl;

import dao.AddressDAO;
import model.Address;
import model.Passenger;
import service.DatabaseConnectionService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddressDAOimpl implements AddressDAO {
    public void save(Address address) {
        Connection connection =
                DatabaseConnectionService.DB_INSTANCE.createConnection();
        String query = "INSERT INTO Address (country, city)" +
                " VALUES (?,?)";
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            pstmt.setString(1, address.getCountry());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            pstmt.setString(2, address.getCity());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getAddressesFromFile(File filename) throws IOException {

        Address address=new Address();
        AddressDAOimpl addressDAOimpl=new AddressDAOimpl();
        File file = new File("passengers.txt");
        String line;
        String words[]=null;
        BufferedReader bufferedReader=new BufferedReader(new FileReader(filename));
        while ((line=bufferedReader.readLine())!=null) {
            words = line.split(",");
            for (int i = 0; i < words.length; i++) {
                //System.out.print(words[i]+" ");
                address.setCountry(words[2]);
                address.setCity(words[3]);
            }
            addressDAOimpl.save(address);
            System.out.println();

        }
        return;
    }
}
