package dao.impl;

import dao.CompanyDAO;
import model.Company;
import model.PassInTrip;
import model.Passenger;
import service.DatabaseConnectionService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import static java.time.format.DateTimeFormatter.*;

public class CompanyDAOimpl implements CompanyDAO {

    @Override
    public Company getById(long id) {
        Connection connection =
                DatabaseConnectionService.DB_INSTANCE.createConnection();

        PreparedStatement preparedStatement;

        ResultSet resultSet;
        Company company = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM Company WHERE id = ?"
            );

            preparedStatement.setLong(1, id);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                company = new Company(
                        resultSet.getString("company_name"),
                        resultSet.getString("founding_date"),
                        resultSet.getInt("id")
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

        return company;
    }


    @Override
    public Set<Company> getAll() {

        Set<Company> companies = null;
        try (Connection connection = DatabaseConnectionService
                .DB_INSTANCE.createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Company VALUES (?,?,?)");
             ResultSet resultSet =
                     preparedStatement.executeQuery(
                             "SELECT * FROM Company")) {
            companies = new HashSet<>();

            Company company;
            while (resultSet.next()) {
                company = new Company(
                        resultSet.getString("company_name"),
                        resultSet.getString("founding_date"),
                        resultSet.getInt("id")
                );

                companies.add(company);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return companies;
    }

    @Override
    public Set<Company> get(int offset, int perPage, String sort) {

        Connection connection = DatabaseConnectionService.DB_INSTANCE.createConnection();

        Set<Company> companies = null;
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Company order by");
            resultSet = preparedStatement.executeQuery(
                    "SELECT * FROM Company  order by company_name LIMIT  " + (offset * perPage) + "," + perPage);
            {

                companies = new HashSet<>();

                Company company = null;
                while (resultSet.next()) {
                    company = new Company(
                            resultSet.getString("company_name"),
                            resultSet.getString("founding_date"),
                            resultSet.getInt("id")
                    );
                    companies.add(company);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companies;
    }

    @Override
    public void save(Company company) {
        Connection connection = DatabaseConnectionService.DB_INSTANCE.createConnection();


        String query = "INSERT INTO Company (company_name, founding_date)VALUES (?, ?)";
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, company.getCompanyName());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("m/D/yyyy");
            String date = company.getFoundingDate();
            LocalDate localDate = LocalDate.parse(date, formatter);
            pstmt.setDate(2, Date.valueOf(localDate));
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getCompaniesFieldsFromFile(File filename) throws IOException {

        String line;
        String words[] = null;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
        while ((line = bufferedReader.readLine()) != null) {
            if (line.contains("'")) {
                line = line.replace("'", "՛");
            }
            words = line.split(",");
            System.out.println();
            Company company = new Company();
            for (int i = 0; i < words.length; i++) {
                System.out.println(words[i] + " ");
                company.setCompanyName(words[0]);
                company.setFoundingDate(words[1]);
            }
            CompanyDAOimpl companyDAOimpl = new CompanyDAOimpl();
            companyDAOimpl.save(company);
        }
        return;

    }
    @Override
    public void update(int id,Company company) {
        Connection connection = DatabaseConnectionService.DB_INSTANCE.createConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Company " +
                    "SET company_name = ?, " +
                    "founding_date = ?, " +
                    "WHERE id=?");
            preparedStatement.setString(1, company.getCompanyName());
            preparedStatement.setString(2, company.getFoundingDate());
            preparedStatement.setInt(3, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void delete(long companyId) {
        Connection connection = DatabaseConnectionService.DB_INSTANCE.createConnection();
        Passenger passenger = new Passenger();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Company " +
                    "WHERE id=?");

            preparedStatement.setLong(1, companyId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
