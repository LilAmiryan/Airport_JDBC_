import dao.impl.*;
import model.Company;
import model.PassInTrip;
import model.Passenger;
import model.Trip;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class App {
    public static void main(String[] args) throws ParseException {
        File passenger = new File("passengers.txt");
        File passInTripFile = new File("pass_in_trip.txt");
        File tripFile = new File("trip.txt");
        File companyFile = new File("companies.txt");

        //-------Company---------

        CompanyDAOimpl companyDAOimpl=new CompanyDAOimpl();
        Date date = java.sql.Date.valueOf("2019-01-26");
       /* Company company=new Company();
        try {
            companyDAOimpl.getCompaniesFieldsFromFile(companyFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        //System.out.println(companyDAOimpl.getAll());
        //System.out.println(companyDAOimpl.getById(12));


       /* try {
            passengerDAOimpl.getPassengersFromFile(passenger);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        //-----------Passenger----------
        PassengerDAOimpl passengerDAOimpl = new PassengerDAOimpl();
        PassInTrip passInTrip=new PassInTrip();
        passengerDAOimpl.registerTrip(passInTrip);
       /* try {
            passengerDAOimpl.getPassengersFromFile(passenger);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //System.out.println(passengerDAOimpl.get(2,50,"passenger_name").toString());
        //passengerDAOimpl.delete(303);
        //  passengerDAOimpl.update(9, new Passenger("Lilit", "123456789", 5));
        // System.out.println( passengerDAOimpl.getAll().toString());
        //System.out.println(passengerDAOimpl.getById(400).toString());

        // System.out.println(passengerDAOimpl.getPassengersOfTrip(2).toString());

        //--------------Address--------------
        AddressDAOimpl addressDAOimpl = new AddressDAOimpl();
/*
        try {
            addressDAOimpl.getAddressesFromFile(passenger);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        //------------Pass_In_Trip---------
       /* PassInTripDAOimpl passInTripDAOimpl = new PassInTripDAOimpl();
        try {
            passInTripDAOimpl.getPassInTripInformationFromFile(passInTripFile);
        } catch (IOException e) {
            e.printStackTrace();
        }*/


        //---------Trip---------
        TripDAOimpl tripDAOimpl=new TripDAOimpl();
       // System.out.println(tripDAOimpl.getTripsFrom("Paris"));
       // System.out.println(tripDAOimpl.getAll());
        //System.out.println(tripDAOimpl.getById(1100));
        /*try {
            tripDAOimpl.getTripInformationFromFile(tripFile);
        } catch (IOException e) {
            e.printStackTrace();
        }*/



    }
}
